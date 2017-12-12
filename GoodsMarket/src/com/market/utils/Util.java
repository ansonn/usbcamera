package com.market.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	public final static int LESSTHANMAXLENGTH = -2;
	public final static int ACCESSIBLE = 1;
	public final static int UNACCESSIBLE = -1;

	public static int CheckUserName(String userName, final int maxlength) {
		if (userName == null || userName.length() < maxlength)
			return LESSTHANMAXLENGTH;
		String regex = "[A-Za-z0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(userName);
		if (m.find()) {
			if (userName.equals(m.group())) {
				return ACCESSIBLE;
			} else {
				return UNACCESSIBLE;
			}
		} else {
			return UNACCESSIBLE;
		}
	}

	public static int CheckMobile(String mobile, final int maxlength) {
		if (mobile == null || mobile.length() < maxlength)
			return LESSTHANMAXLENGTH;
		String regex = "[0-9]+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		if (m.find()) {
			if (mobile.equals(m.group())) {
				return ACCESSIBLE;
			} else {
				return UNACCESSIBLE;
			}
		} else {
			return UNACCESSIBLE;
		}
	}

	public static boolean isEmail(String email) {
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}

	public static boolean isCookieExpires(String cookie) {
		String[] cookieItem = cookie.split(";");
		String expiresString = null;
		for (String str : cookieItem) {
			if (str.trim().startsWith("expires=")) {
				expiresString = str.trim().replace("expires=", "");
				break;
			}
		}

		try {
			DateFormat format = new SimpleDateFormat(
					"EEE, dd-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date date = format.parse(expiresString);
			return date.before(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean isNumber(char ch) {
		if (ch <= '9' && ch >= '0')
			return true;
		return false;
	}
}
