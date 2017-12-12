package com.market.utils;

import android.content.Context;
import android.text.TextUtils;

import com.market.dbmanage.SharePrefrencesUtils;

public class UserStatus {
	static SharePrefrencesUtils spPrefrencesUtils;
	static String cookie ;
	public static boolean isLogin(Context context)
	{
		if(spPrefrencesUtils == null)
		 spPrefrencesUtils = new SharePrefrencesUtils(context);
		if(cookie == null)
			cookie = spPrefrencesUtils.getLoginCookie();
		if (!TextUtils.isEmpty(cookie) && !Util.isCookieExpires(cookie))
			return true;
		return false;
	}
}
