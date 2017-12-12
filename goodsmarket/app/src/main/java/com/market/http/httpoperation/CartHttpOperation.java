package com.market.http.httpoperation;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.market.http.HttpComm;
import com.market.http.OnHttpCallBack;
import com.market.utils.CartOperation;
import com.market.utils.Constants;

public class CartHttpOperation extends HttpComm {
	public static void public_getCart(OnHttpCallBack callBack, int requestId, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Cart");
		argMap.put("c", "public_getCart");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void public_addToCart(long sku_id, int number ,OnHttpCallBack callBack, int requestId,Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Cart");
		argMap.put("c", "public_addToCart");
		argMap.put("sku_id", sku_id+"");
		argMap.put("number", number+"");
	
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void public_changeNumberInCart(Long sku_id, int number, OnHttpCallBack callBack, int requestId, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Cart");
		argMap.put("c", "public_changeNumberInCart");
		argMap.put("sku_id", sku_id+"");
		argMap.put("number", number+"");
	
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void public_deleteFromCart(long[] sku_ids, OnHttpCallBack callBack, int requestId, Context context)
	{

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Cart");
		argMap.put("c", "public_deleteFromCart");
		argMap.put("sku_ids", CartOperation.getSkuIdJson(sku_ids));
	
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
}
