package com.serenegiant.usb;

import org.xutils.x;

import android.app.Application;

public class UsbApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		CrashHandler crashHandler = CrashHandler.getInstance();
//		crashHandler.init(getApplicationContext());
		
		x.Ext.init(this);
		x.Ext.setDebug(false);

	}

}
