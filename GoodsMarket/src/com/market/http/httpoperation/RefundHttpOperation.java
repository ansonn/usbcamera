package com.market.http.httpoperation;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.market.http.HttpComm;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class RefundHttpOperation extends HttpComm{
	public static void getRefundList(int page_index, int list_row, OnHttpCallBack callBack, int requestId, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getRefundList");
		argMap.put("page_index", page_index+"");
		argMap.put("list_row", list_row+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void getRefundInfo(Long id, OnHttpCallBack callBack, int requestId, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getRefundInfo");
		argMap.put("id", id.longValue()+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void applyToRefund(Long order_id, String note , OnHttpCallBack callBack, int requestId, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "refund");
		argMap.put("order_id", order_id.longValue()+"");
		argMap.put("note", note);
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
}
