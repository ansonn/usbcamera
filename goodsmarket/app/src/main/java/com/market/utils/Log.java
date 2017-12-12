package com.market.utils;

public class Log {
	private static final String TAG = "MARKET_LOGS";
	private static boolean debugable = true;
	
	public static void d(String message)
	{
		if(debugable)
			android.util.Log.d(TAG, message);
	}
	
	public static void d(String tag, String message)
	{
		if(debugable)
			android.util.Log.d(tag, message);
	}

	public static void e(String message)
	{
		if(debugable)
			android.util.Log.e(TAG, message);
	}
	
	public static void e(String tag, String message)
	{
		if(debugable)
			android.util.Log.e(tag, message);
	}
	
	public static void i(String message)
	{
		if(debugable)
			android.util.Log.i(TAG, message);
	}
	
	public static void i(String tag, String message)
	{
		if(debugable)
			android.util.Log.i(tag, message);
	}

}
