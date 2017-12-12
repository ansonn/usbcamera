package com.sendong.facesystem.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

public class SexRecogUtil {

	private static final String TAG = "yan";

	/**
	 * 传进来的是原人脸bitmap，和两眼距离/中心坐标
	 * 返回刘海 Bitmap
	 * **/
	public static Bitmap getBangBitmap(Bitmap src, float eyeDis, PointF midEye){

		int w = src.getWidth();
		int h = src.getHeight();
		Point eyeLeft = new Point((int)(midEye.x - eyeDis/2), (int)midEye.y);
		Point eyeRight = new Point((int)(midEye.x + eyeDis/2), (int)midEye.y);
		float dY = eyeDis*0.3f; //往上平移的高度
		float dH = eyeDis; //截取正方形的高度
		float dW = eyeDis; //截取正方形的宽度
		int left_top_X = eyeLeft.x;
		int left_top_Y = eyeLeft.y - (int)(dY + dH);
		int right_botm_X = eyeRight.x;
		int right_botm_Y = eyeRight.y - (int)dY;

		Rect rect = new Rect(left_top_X, left_top_Y, right_botm_X, right_botm_Y);
		Log.i(TAG, "左眼坐标 x = " + eyeLeft.x + "y = " + eyeLeft.y);
		Bitmap bangBitmap = getDstRect(src, rect);

		return bangBitmap;
	}

	public static Bitmap getDstRect(Bitmap src, Rect rect){


		Bitmap dst = Bitmap.createBitmap(src, rect.left, rect.top, rect.width(), rect.height());

		return dst;
	}
	
	public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
		Matrix m = new Matrix();
		m.setRotate(orientationDegree, (float) bm.getWidth() / 2,
				(float) bm.getHeight() / 2);

		try {
			Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
					bm.getHeight(), m, true);
			bm.recycle();
			return bm1;
		} catch (OutOfMemoryError ex) {
		}
		return null;
	}
	public static boolean checkBang(Bitmap src, float th){

		int w = src.getWidth();
		int h = src.getHeight();
		int N = w*h;
		int[] pixel = new int[N];
		src.getPixels(pixel, 0, w, 0, 0, w, h);
		int r = 0; 
		int g = 0;
		int b = 0;
		int y = 0;
		int hairN = 0;
		for(int i=0; i<N; i++){
			r = Color.red(pixel[i]);
			g = Color.green(pixel[i]);
			b = Color.blue(pixel[i]);
			y = (int)((float)(r)*0.3 + (float)(g)*0.59 + (float)(b)*0.11);
			if(y < 40){
				hairN++;
			}
		}
		float rate = (float)(hairN)/(float)(N);
		Log.i(TAG, "像素点总数N = " + N + "头发数 = " + hairN + "头发所占比 = " + rate);
		if(rate > th){
			return true;
		}else{
			return false;
		}

	}
	
	public static int getPeopleHeight(int eye_Y){
		int h = eye_Y;
		return h;
	}



}
