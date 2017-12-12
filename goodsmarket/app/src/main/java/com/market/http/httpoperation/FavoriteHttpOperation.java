package com.market.http.httpoperation;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.market.http.HttpComm;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class FavoriteHttpOperation extends HttpComm {
	public static void addFavorite(long id, int requestId,
			OnHttpCallBack onHttpCallBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Favorite");
		argMap.put("c", "addFavorite");
		argMap.put("id", id + "");
		asyncAccessHttp(argMap, requestId, onHttpCallBack, false, true, context);
	}

	public static void deleteFavorite(long id, int requestId,
			OnHttpCallBack onHttpCallBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Favorite");
		argMap.put("c", "deleteFavorite");
		argMap.put("id", id + "");
		asyncAccessHttp(argMap, requestId, onHttpCallBack, false, true, context);
	}

	public static void getFavoriteList(int page_index, int list_row,
			int requestId, OnHttpCallBack onHttpCallBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Favorite");
		argMap.put("c", "getFavoriteList");
		argMap.put("page_index", page_index + "");
		argMap.put("list_row", list_row + "");
		asyncAccessHttp(argMap, requestId, onHttpCallBack, false, true, context);
	}
}
