package com.serenegiant.usbcameratest;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sendong.facesystem.util.ImageTool;
import com.sendong.facesystem.util.SexRecogUtil;
import com.serenegiant.usbcameratest.PreviewService.CameraBinder;
import com.serenegiant.usbcameratest.PreviewService.OnImageChangeListener;
import com.serenegiant.widget.Preview;

public class CameraActivity extends Activity implements Callback {

	private Camera mCamera;
	private static Preview preview; 
	private static Handler mHandler; 
	private static ImageView mImageView;
	private CameraBinder cameraBinder;

	private FrameLayout frameLayout;
	private ImageChangeReceiver imageChangeReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!com.sendong.facesystem.util.Config.hasGUI) {
			startService(new Intent(this, PreviewService.class));
			finish();
		}else{
			mHandler = new Handler(this);

			setContentView(R.layout.preview_window);
			frameLayout = (FrameLayout) findViewById(R.id.surfaceView);
			mImageView = (ImageView) findViewById(R.id.camera_image);

			startService(new Intent(this, PreviewService.class));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bindService(new Intent(this, PreviewService.class),
					serviceConnection, Context.BIND_AUTO_CREATE);


			imageChangeReceiver = new ImageChangeReceiver();

			this.registerReceiver(imageChangeReceiver, new IntentFilter(
					PreviewService.ACTION_IMAGE_CHANGE));
		}
	}

	public static void changeImage(Bitmap bitmap) {
		Log.i("test", "changeImage bitmap=" + bitmap);
		if (mImageView != null) {
			Message msg = new Message();
			msg.obj = bitmap;
			mHandler.sendMessage(msg);
		}
	}

	class ImageChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			String path = intent.getStringExtra("image");
			Message msg = mHandler.obtainMessage();
			msg.obj = path;
			mHandler.sendMessage(msg);
		}
	}

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i("test", "onServiceConnected");
			// if (preview == null) {
			preview = new Preview(CameraActivity.this);
			// }
			cameraBinder = (CameraBinder) service;
			mCamera = cameraBinder.getCamera(preview, mHandler);
			preview.setCamera(mCamera);
			frameLayout.addView(preview);
			// cameraBinder.setOnImageChangeListener(new OnImageChangeListener()
			// {
			//
			// @Override
			// public void onImageChange(byte[] data) {
			// CameraActivity.this.onImageChange(data);
			//
			// }
			// });

		}
	};

	@Override
	protected void onDestroy() {
		// sendBroadcast(new Intent("action.preview_window_off"));
		// unbindService(serviceConnection);
		// this.unregisterReceiver(imageChangeReceiver);
		mImageView = null;
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showState();
			// sendBroadcast(new Intent("action.preview_window_off"));
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			// returb==
		}
		return true;
	}

	public void showState() {
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Intent notifyIntent = new Intent(this, CameraActivity.class);
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent appIntent = PendingIntent.getActivity(
				CameraActivity.this, 0, notifyIntent, 0);
		// 创建Notication，并设置先关参数
		Notification myNoti = new Notification();

		myNoti.tickerText = "facedetect正在后台运行";
		myNoti.icon = R.drawable.ic_launcher;
		// 设置notification发生时同时屏幕发亮
		myNoti.defaults = Notification.DEFAULT_LIGHTS;
		myNoti.flags |= Notification.FLAG_ONGOING_EVENT;
		// 设置Notification流言的参数
		myNoti.setLatestEventInfo(CameraActivity.this, "facedetect",
				"facedetect正在后台运行", appIntent);
		// 送出Notification
		notificationManager.notify(0, myNoti);

	}

	@Override
	public boolean handleMessage(Message msg) {
		// mImageView.setImageDrawable(get)
		Log.i("test", "images=" + msg.obj);
		Bitmap bitmap = BitmapFactory.decodeFile(msg.obj + "");
		mImageView.setImageBitmap(bitmap);
		return false;
	}
}
