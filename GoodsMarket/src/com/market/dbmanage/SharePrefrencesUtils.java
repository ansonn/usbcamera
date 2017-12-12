package com.market.dbmanage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.market.bean.User;
import com.market.http.JsonToBean;

/**
 * 
 * @tag 保存 shareprefrences 工具类
 * @author 陈伟斌
 * @date 2015-3-6
 */
public class SharePrefrencesUtils {

	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public SharePrefrencesUtils(Context context) {
		mContext = context;
		mSharedPreferences = context.getSharedPreferences("market",
				Context.MODE_PRIVATE);
	}

	public void saveLoginCookie(String cookie) {
		Editor editor = mSharedPreferences.edit();
		editor.putString("cookie", cookie);
		editor.commit();
	}

	public String getLoginCookie() {
		return mSharedPreferences.getString("cookie", null);
	}

	public void setUserName(String userName) {
		Editor editor = mSharedPreferences.edit();
		editor.putString("userName", userName);
		editor.commit();
	}

	public String getUserName() {
		return mSharedPreferences.getString("userName", null);
	}

	public void setPassword(String pwd) {
		Editor editor = mSharedPreferences.edit();
		editor.putString("password", pwd);
		editor.commit();
	}

	public String getPassWord() {
		return mSharedPreferences.getString("password", null);
	}

	public User getLoginedUserInfo() {
		String json = mSharedPreferences.getString("userInfo", null);
		return JsonToBean.json2User(json);
	}

	public void saveLoginedUserInfo(String json) {
		Editor editor = mSharedPreferences.edit();
		editor.putString("userInfo", json);
		editor.commit();
	}
	
}
