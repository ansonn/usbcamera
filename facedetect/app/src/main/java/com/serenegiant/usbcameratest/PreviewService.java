package com.serenegiant.usbcameratest;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.media.FaceDetector;
import android.os.Binder;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.megvii.facepp.sdk.Facepp;
import com.megvii.facepp.sdk.Facepp.Face;
import com.megvii.facepp.sdk.Facepp.FaceppConfig;
import com.sendong.facesystem.Person;
import com.sendong.facesystem.PersonInfo;
import com.sendong.facesystem.Result;
import com.sendong.facesystem.util.AuthResult;
import com.sendong.facesystem.util.AuthorizationUtils;
import com.sendong.facesystem.util.CommonOperate;
import com.sendong.facesystem.util.ConUtil;
import com.sendong.facesystem.util.CountTime;
import com.sendong.facesystem.util.ImageUtil;
import com.serenegiant.widget.Preview;

public class PreviewService extends Service implements Callback {
	private Preview preview;

	// private View view = null;
	// private WindowManager mWindowManager = null;
	// private WindowManager.LayoutParams wmParams = null;
	// private final int SW_HIDE = 0;
	// private final int SW_SHOW = 1;
	private static Camera mCamera;
	int mNumberOfCameras;
	int mCurrentCamera; // Camera ID currently chosen
	private Handler mHandler;
	// for thread pool
	private ImageView mImageView;
	private static final int CORE_POOL_SIZE = 1; // initial/minimum threads
	private static final int MAX_POOL_SIZE = 4; // maximum threads
	private static final int KEEP_ALIVE_TIME = 10; // time periods while keep
													// the idle thread
	protected static final ThreadPoolExecutor EXECUTER = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>());
	private DateFormat df;
	private String imei;
	public static byte[] cameraData;
	OnImageChangeListener changeLister;
	public static String ACTION_IMAGE_CHANGE = "ACTION_IMAGE_CHANGE";
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams wmParams;
	View view;
	private int degree = 0;
	private Facepp facepp;
	private boolean faceAppInitOk = false;
	private CommonOperate commonOperate;

	@Override
	public boolean bindService(Intent service, ServiceConnection conn, int flags) {
		// TODO Auto-generated method stub
		return super.bindService(service, conn, flags);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mHandler = new Handler();
		x.Ext.init(this.getApplication());
		x.Ext.setDebug(false);
		Log.i("test", "service onCreate");
		showCarRecorderStart();

		createWindowView();

		// IntentFilter mFilterRecord = new IntentFilter();
		// mFilterRecord.addAction("action.preview_window_off");
		// mFilterRecord.addAction("action.preview_window_on");
		// registerReceiver(mReceiverRecord, mFilterRecord);

		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		countTime = new CountTime();
		personInfo = new PersonInfo();
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ImageUtil.initSavePath();

		EXECUTER.execute(mThread);

		commonOperate = new CommonOperate("4U0cuE35ad0nO2MBloOsgtS3ZLzmlAxy",
				"JhESL9bQ-snkA2TJs7Nd5mDCHY9vTCQZ");

	}

	private void initFaceDetect() {
		if (preview == null) {
			return;
		}

		int height = preview.getPreViewSize().height;
		int width = preview.getPreViewSize().width;

		int left = 0;
		int top = 0;
		int right = width;
		int bottom = height;
		facepp = new Facepp();
		String errorCode = facepp.init(this,
				ConUtil.getFileContent(this, R.raw.megviifacepp_0_2_0_model));
		Log.w("ceshi", "errorCode====" + errorCode);
		FaceppConfig faceppConfig = facepp.getFaceppConfig();
		faceppConfig.rotation = degree;
		faceppConfig.interval = 25;
		faceppConfig.minFaceSize = 200;
		faceppConfig.roi_left = left;
		faceppConfig.roi_top = top;
		faceppConfig.roi_right = right;
		faceppConfig.roi_bottom = bottom;
		faceppConfig.detectionMode = FaceppConfig.DETECTION_MODE_NORMAL;
		facepp.setFaceppConfig(faceppConfig);
		faceAppInitOk = true;
	}

	private void createWindowView() {
		if (mCamera == null) {
			mCamera = Camera.open();
			Camera.Parameters params = mCamera.getParameters();

			// params.setPreviewSize(1280, 720);
			mCamera.setDisplayOrientation(degree);
			mCamera.setParameters(params);

			mCamera.setPreviewCallback(new PreviewCallback() {

				@Override
				public void onPreviewFrame(byte[] data, Camera camera) {
					cameraData = data;

				}
			});

			x.task().postDelayed(new Runnable() {

				@Override
				public void run() {
					new AuthorizationUtils().netWorkAuthoriza(
							PreviewService.this, new AuthResult() {

								@Override
								public void authState(boolean isSuccess) {
									if (isSuccess) {
										initFaceDetect();
									}

								}
							});

				}
			}, 5000);

		}

		if (!com.sendong.facesystem.util.Config.hasGUI) {
			mWindowManager = (WindowManager) getApplicationContext()
					.getSystemService(Context.WINDOW_SERVICE);
			wmParams = new WindowManager.LayoutParams();

			wmParams.type = 2002;
			wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
					| LayoutParams.FLAG_NOT_FOCUSABLE;
			wmParams.screenOrientation = 0;
			wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
			wmParams.width = 1;
			wmParams.height = 1;

			view = LayoutInflater.from(this).inflate(R.layout.preview_dialog,
					null);

			mImageView = (ImageView) view.findViewById(R.id.camera_image);
			preview = (Preview) view.findViewById(R.id.surfaceView);

			if (mCamera != null) {
				preview.setCamera(mCamera);
			}
			mWindowManager.addView(view, wmParams);
			// if (mCamera == null) {
			// mCamera = Camera.open();
			// }
			// if (mCamera != null) {
			// // preview.setCamera(mCamera);
			// //
			//
			// mCamera.setPreviewCallback(new PreviewCallback() {
			//
			// @Override
			// public void onPreviewFrame(byte[] data, Camera camera) {
			// cameraData = data;
			//
			// }
			// });
			// mCamera.setDisplayOrientation(90);
			//
			// // Camera.Parameters parameters = mCamera.getParameters();
			// //
			// // mCamera.setParameters(parameters);
			// }
		}
	}

	private void showUI(boolean isShow) {
		com.sendong.facesystem.util.Config.hasGUI = isShow;
		if (isShow) {
			wmParams.width = wmParams.FILL_PARENT;
			wmParams.height = wmParams.FILL_PARENT;
			mWindowManager.updateViewLayout(view, wmParams);
		} else {
			wmParams.width = 2;
			wmParams.height = 2;
			mWindowManager.updateViewLayout(view, wmParams);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return new CameraBinder();
	}

	class CameraBinder extends Binder {
		public Camera getCamera(Preview preview, Handler handler) {
			PreviewService.this.preview = preview;
			mHandler = handler;
			return mCamera;
		}

		public void setFaces(int w, int h, Bitmap faceBitmap, String imagePath) {
			PreviewService.this.setFaces(w, h, faceBitmap, imagePath);
		}

		public byte[] getFaceData() {
			return cameraData;
		}

		public void setOnImageChangeListener(OnImageChangeListener changeLister) {
			PreviewService.this.changeLister = changeLister;
		}

	}

	@Override
	public void onDestroy() {

		// mWindowManager.removeView(view);
		// stopForeground(true);
		super.onDestroy();
	}

	public void showCarRecorderStart() {
		System.currentTimeMillis();
		Notification localNotification = new Notification();
		localNotification.flags = (0x2 | localNotification.flags);
		Context localContext = getApplicationContext();
		Intent localIntent = new Intent();
		localNotification.setLatestEventInfo(localContext, "", "",
				PendingIntent.getActivity(getApplicationContext(), 0,
						localIntent, 0));
		// startForeground(1, localNotification);
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}

	Thread mThread = new Thread() {
		public void run() {
			while (!this.isInterrupted()) {
				try {
					// this.sleep(5 * 1000);
					onImageChange(cameraData);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// mHandler.sendEmptyMessage(0x000000);
				// ImageUtil.saveJpeg(convertViewToBitmap(mUVCCameraView,
				// 600, 480));
				// Toast.makeText(MainActivity.this, "拿一张", 1).show();
			}

		}
	};

	FaceDetector faceDetector = null;
	FaceDetector.Face[] face;
	final int N_MAX = 10;
	CountTime countTime = null;
	PersonInfo personInfo = null;
	final static int MSG_SHOW_TIME = 4;

	List<Long> startTimeList = new ArrayList<Long>();

	public void setFaces(int w, int h, Bitmap faceBitmap, final String imagePath) {
		// faceBitmap=SexRecogUtil.adjustPhotoRotation(faceBitmap, 180);
		// faceDetector = new FaceDetector(faceBitmap.getWidth(),
		// faceBitmap.getHeight(), N_MAX);

		FaceppDetect faceppDetect = new FaceppDetect();
		faceppDetect.setDetectCallback(new DetectCallback() {

			public void detectResult(JSONObject rst) {
				if (rst != null) {
					JSONArray faces;
					try {
						Log.i("test", "start detect json=" + rst);
						faces = rst.getJSONArray("faces");

						analyseFaceDetectJson(faces, imagePath);
						Log.i("test", "faces.length()=" + faces.length());
						Log.i("test", "result=" + rst);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("result=" + rst);

			}
		});

		startTimeList.add(System.currentTimeMillis());
		Log.i("test", "start detect");
		faceppDetect.detect(faceBitmap);

	}

	public Result analysisJSON(String result) {
		try {

			if (result == null) {
				return null;
			}
			JSONObject jo;
			Iterator<?> it;
			Result r = new Result();

			jo = new JSONObject(result);
			for (it = jo.keys(); it.hasNext();) {
				String key = (String) it.next();
				String value = (String) jo.getString(key);
				if ("status".equals(key)) {
					r.setStatus(value);
				} else if ("code".equals(key)) {
					r.setCode(value);
				} else if ("content".equals(key)) {
					r.setContent(value);
				}
			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void showtToast(final String msg) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (com.sendong.facesystem.util.Config.hasGUI) {
					Toast.makeText(getApplicationContext(), msg, 0).show();
				}

			}
		});
	}

	boolean isfirst = true;
	int faceCount = 0;

	private void analyseFaceDetectJson(JSONArray faces, String imagePath) {

		// JSONArray faces;

		try {
			// faces = jsonObject.getJSONArray("face");

			int nFace = faces.length();
			showtToast("face:" + nFace + " analyse=" + personInfo.isAnalysing());
			// Toast.makeText(this, "face:" + nFace, 0).show();
			if (nFace > 0) {
				System.out.println();
			}

			if (!personInfo.isAnalysing() || (nFace > faceCount)) {
				if (nFace > 0) {
					if (nFace > faceCount) {
						personInfo.getPersons().clear();
					}
					faceCount = nFace;

					// countTime.setStart(System.currentTimeMillis());
					personInfo.setImagePath(imagePath);
					personInfo.setStartTime(System.currentTimeMillis());
					Person person;
					for (int i = 0; i < nFace; i++) {
						JSONObject json_person = faces.getJSONObject(i);
						JSONObject json_attribute = json_person
								.getJSONObject("attributes");
						String sex = json_attribute.getJSONObject("gender")
								.getString("value");
						String age = json_attribute.getJSONObject("age")
								.getString("value");
						String race = json_attribute.getJSONObject("race")
								.getString("value");
						person = new Person();
						person.setmSex("Female".equals(sex) ? 2 : 1);
						person.setAge(Integer.parseInt(age));
						person.setRace(race);
						personInfo.addPerson(person);
						showtToast("sex:" + sex + " age:" + age);
					}

					// personInfo.setSex(2);
					personInfo.setAnalysing(true);
				}

			} else {
				if (nFace == 0) {
					// countTime.setEnd(System.currentTimeMillis());
					// personInfo.setStartTime(startTimeList.get(0));
					// startTimeList.remove(0);
					personInfo.setEndTime(System.currentTimeMillis());
					// float time = countTime.getCountTime();
					// float time = personInfo.getLastTime();
					// countTime.clearCountTime();
					if (personInfo.getPersons().size() > 0) {
						for (Person person : personInfo.getPersons()) {
							uploadPersonInfo(personInfo, person);
						}
					}

					personInfo.clearAllInfo();
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void uploadPersonInfo(final PersonInfo personInfo, Person person) {

		RequestParams requestParams = new RequestParams(
				"http://market.zyxfeng.com/api.php");

		requestParams.addParameter("time", System.currentTimeMillis());
		requestParams.addParameter("g", "ApiMarket");
		requestParams.addParameter("m", "Face");
		requestParams.addParameter("c", "addFaceLog");
		requestParams.addParameter("device_id", imei);
		requestParams.addBodyParameter("addAttachment",
				new File(personInfo.getImagePath()));
		requestParams.addParameter("sex", person.getmSex());
		requestParams.addParameter("race", person.getRace());
		requestParams.addParameter("age", person.getAge());
		requestParams.addParameter("start_time",
				df.format(new Date(personInfo.getStartTime())));
		requestParams.addParameter("end_time",
				df.format(new Date(personInfo.getEndTime())));
		requestParams.setMultipart(true);

		x.http().post(requestParams, new CommonCallback<String>() {

			@Override
			public void onCancelled(CancelledException arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Throwable ex, boolean arg1) {
				new File(personInfo.getImagePath()).delete();
				ex.printStackTrace();

			}

			@Override
			public void onFinished() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String result) {
				new File(personInfo.getImagePath()).delete();
				Result r = analysisJSON(result);
				System.out.println(r);
			}
		});

	}

	public interface OnImageChangeListener {
		public void onImageChange(byte[] data);
	}

	public boolean onImageChange(byte[] cameraData) {

		Log.i("test", "onImageChange");
		// byte[] cameraData = cameraBinder.getFaceData();

		if (cameraData == null || preview == null) {
			return false;
		}
		int height = preview.getPreViewSize().height;
		int width = preview.getPreViewSize().width;

		// ////////////////////本地判断有没有人脸////////////////////////////////
		// local
		//
		if (AuthorizationUtils.isAuthSuccess() && facepp != null
				&& faceAppInitOk) {
			try {

				Log.e("test", "auth=" + AuthorizationUtils.isAuthSuccess()
						+ " facepp=" + facepp);
				Face[] faces = facepp.detect(cameraData, width, height,
						Facepp.IMAGEMODE_NV21);
				showtToast("本地识别到人脸个数:" + faces.length);
				Log.e("test", "face detect faces count=" + faces.length);
				if (faces == null || faces.length <= 0) {
					if (personInfo != null
							&& personInfo.getPersons().size() > 0) {
						personInfo.setEndTime(System.currentTimeMillis());
						if (personInfo.getPersons().size() > 0) {
							for (Person person : personInfo.getPersons()) {
								uploadPersonInfo(personInfo, person);
							}
						}

						personInfo.clearAllInfo();
					}
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// ///////////////////////////////////////////////////
		YuvImage yuvimage = new YuvImage(cameraData, ImageFormat.NV21, width,
				height, null);// 20、20分别是图的宽度与高度
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		yuvimage.compressToJpeg(
				new Rect(0, 0, yuvimage.getWidth(), yuvimage.getHeight()), 100,
				baos);// 80--JPG图片的质量[0-100],100最高
		byte[] jdata = baos.toByteArray();

		Bitmap mBitmap = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);

		if (mBitmap == null)
			return false;
		try {

			// mBitmap = SexRecogUtil.adjustPhotoRotation(mBitmap, degree);
			final Bitmap faceBitmap = mBitmap.copy(Config.RGB_565, true);
			// CameraActivity.changeImage(faceBitmap);
			String path = saveJpeg(faceBitmap);
			//
			// Intent intent = new Intent(ACTION_IMAGE_CHANGE);
			// intent.putExtra("image", path);
			// sendBroadcast(intent);
			// Message msg=mHandler.obtainMessage();
			// msg.obj=faceBitmap;
			// mHandler.sendMessage(msg);

			setFaces(faceBitmap.getWidth(), faceBitmap.getHeight(), faceBitmap,
					path);

			mBitmap.recycle();
			System.gc();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	static public String saveJpeg(Bitmap bm) {

		long dataTake = System.currentTimeMillis();
		String jpegName = "/mnt/sdcard/temp.jpg";

		// File jpegFile = new File(jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);

			// Bitmap newBM = bm.createScaledBitmap(bm, 600, 800, false);

			bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
			bos.flush();
			bos.close();
			return jpegName;
		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		return null;
	}

	private class FaceppDetect {
		DetectCallback callback = null;

		private String fileCache = "/mnt/sdcard/cache.jpg";

		public void setDetectCallback(DetectCallback detectCallback) {
			callback = detectCallback;
		}

		public void detect(final Bitmap image) {
			// ByteArrayOutputStream stream;
			// stream = new ByteArrayOutputStream();
			// image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
			// image.recycle();

			// x.task().run(new Runnable() {
			//
			// @Override
			// public void run() {
			// ByteArrayOutputStream stream;
			// try {
			// stream = new ByteArrayOutputStream();
			// image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
			// image.recycle();
			// byte[] result = commonOperate.detectByte(stream
			// .toByteArray());
			// String detectResult = new String(result);
			// Log.i("test", "detectResult=" + detectResult);
			// callback.detectResult(new JSONObject(detectResult));
			// } catch (Exception e1) {
			//
			// e1.printStackTrace();
			// }
			//
			// }
			// });

			RequestParams requestParams = new RequestParams(
					"https://api-cn.faceplusplus.com/facepp/v3/detect");
			requestParams.addParameter("api_key",
					"4U0cuE35ad0nO2MBloOsgtS3ZLzmlAxy");
			requestParams.addParameter("api_secret",
					"JhESL9bQ-snkA2TJs7Nd5mDCHY9vTCQZ");
			requestParams.addParameter("return_attributes",
					"gender,age,smiling,glass,headpose,facequality,blur,race");

			FileOutputStream stream;
			try {
				stream = new FileOutputStream(fileCache);
				image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
				image.recycle();
				stream.close(); //

				requestParams.addBodyParameter("image_file",
						new File(fileCache));
				requestParams.setMultipart(true);

			} catch (Exception e1) {

				e1.printStackTrace();
			}

			x.http().post(requestParams, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) { // TODO
																	// Auto-generated
																	// method
																	// stub

				}

				@Override
				public void onError(Throwable ex, boolean arg1) {
					// TODO Auto-generated method stub File cachefile = new
					// File(fileCache);
					// if (cachefile.exists()) {
					// cachefile.delete();
					// }
					ex.printStackTrace();
				}

				@Override
				public void onFinished() { // TODO Auto-generated

				}

				@Override
				public void onSuccess(String result) {
					// File cachefile = new File(fileCache);
					// if (cachefile.exists()) {
					// cachefile.delete();
					// }

					if (callback != null) {
						try {
							callback.detectResult(new JSONObject(result));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}
			});

			//
		}
	}

	interface DetectCallback {
		void detectResult(JSONObject rst);
	}
}
