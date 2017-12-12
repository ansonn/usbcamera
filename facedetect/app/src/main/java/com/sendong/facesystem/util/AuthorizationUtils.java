package com.sendong.facesystem.util;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import com.megvii.facepp.sdk.Facepp;
import com.megvii.licencemanage.sdk.LicenseManager;

public class AuthorizationUtils {
	private LicenseManager licenseManager;
	private String API_KEY = "uufPLjHoLZ23SwhXkc7xpzveX0IwM1ZJ";
	private String API_SECRET = "0p3dTJKjWpGAFuHBE_sohQv1oGw_5Sr6";
	private static boolean isAuthSuccess = false;
	private AuthResult authResult;
	private SharedPreferences spPreferences;
	private final String KEY_AUTH_TIME = "KEY_AUTH_TIME";
	private final String KEY_LICENCE = "KEY_LICENCE";
	private int licence_during = LicenseManager.DURATION_365DAYS;

	public void netWorkAuthoriza(Context context, AuthResult authResult) {
		spPreferences = context.getSharedPreferences("sp_auth",
				Context.MODE_PRIVATE);
		this.authResult = authResult;
		licenseManager = new LicenseManager(context);
		
		String uuid = ConUtil.getUUIDString(context);
		long[] apiName = { Facepp.getApiName() };
		String content = licenseManager.getContent(uuid, licence_during,
				apiName);

		
	

		if(System.currentTimeMillis()>spPreferences.getLong(KEY_AUTH_TIME, 0)
				+ licence_during* 24 * 60 * 60 * 1000){
					isAuthSuccess=false;
		}else{
			String errorStr = licenseManager.getLastError();
			Log.w("ceshi", "getContent++++errorStr===" + errorStr);
			if ("MG_RETCODE_OK".equals(errorStr)) {
				isAuthSuccess = true;
			} else {
				isAuthSuccess = false;
			}
		}

	
		String license=spPreferences.getString(KEY_LICENCE, "");
		Log.e("ceshi", "isAuthSuccess===" + isAuthSuccess+" license="+license);
		if (isAuthSuccess&&!TextUtils.isEmpty(license)) {
			licenseManager.setLicense(license);
			authState(true);
		} else {
			org.xutils.http.RequestParams requestParams = new org.xutils.http.RequestParams(
					"https://api.megvii.com/megviicloud/v1/sdk/auth");
			requestParams.addParameter("api_key", API_KEY);
			requestParams.addParameter("api_secret", API_SECRET);
			requestParams.addParameter("auth_msg", content);
			x.http().post(requestParams, new CommonCallback<String>() {

				@Override
				public void onCancelled(CancelledException arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(Throwable arg0, boolean arg1) {
					arg0.printStackTrace();
					authState(false);
					Log.e("test", "getauth onError " + arg0.getMessage());
				}

				@Override
				public void onFinished() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(String successStr) {
					boolean isSuccess = licenseManager.setLicense(successStr);
					if (isSuccess) {
						
						Editor editor=spPreferences.edit();
						
						editor.putLong(KEY_AUTH_TIME,
								System.currentTimeMillis());
						editor.putString(KEY_LICENCE, successStr);
						editor.commit();
						
						long a=spPreferences.getLong(KEY_AUTH_TIME, 0);
						authState(true);
					} else {
						authState(false);
					}
					String errorStr = licenseManager.getLastError();
					Log.e("test", "setLicense++++errorStr===" + errorStr);

				}
			});
		}
	}

	public static boolean isAuthSuccess() {
		return isAuthSuccess;
	}

	private void authState(final boolean isSuccess) {
		isAuthSuccess = isSuccess;
		Log.e("ceshi", "网络授权结果" + isSuccess);
		if (authResult != null) {
			authResult.authState(isSuccess);
		
		}

	}

}
