package com.serenegiant.usbcameratest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sendong.facesystem.util.Config;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent data) {

		if (Config.hasGUI) {
			 Intent intent=new Intent();
			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 intent.setClass(context, CameraActivity.class);
			 context.startActivity(intent);
		} else {
			context.startService(new Intent(context, PreviewService.class));
		}
	}

}
