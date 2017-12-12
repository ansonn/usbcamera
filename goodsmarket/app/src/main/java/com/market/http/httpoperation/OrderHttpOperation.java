package com.market.http.httpoperation;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.market.bean.NewOrder;
import com.market.bean.OrderBeforePosted;
import com.market.http.HttpComm;
import com.market.http.OnHttpCallBack;
import com.market.utils.CartOperation;
import com.market.utils.Constants;

/**
 * 
 * @author pumkid 用于order的网络处理
 *
 */
public class OrderHttpOperation extends HttpComm {
	public static void getOrderList(final int requestId, int page_index,
			int list_row, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getOrderList");
		argMap.put("page_index", page_index + "");
		argMap.put("list_row", list_row + "");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getOrder(final int requestId, long order_id,
			OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getOrderInfo");
		argMap.put("order_id", order_id + "");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void submitOrder(final int requestId, NewOrder newOrder,
			OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Order");
		argMap.put("c", "submitOrder");
		argMap.put("address_id", newOrder.getAddress_id() + "");
		argMap.put("payment_type", newOrder.getPayment_type() + "");
		argMap.put("remark", newOrder.getRemark());
		argMap.put("is_invoice*", newOrder.getIs_invoice() + "");
		argMap.put("invoice_title", newOrder.getInvoice_title());
		argMap.put("voucher_id", newOrder.getVoucher_id() + "");

		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getCommentListByOrderId(final int requestId,
			int orderid, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getCommentListByOrderId");
		argMap.put("order_id", orderid + "");

		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getInfobeforepostOrder(long[] sku_ids,
			final int requestId, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Order");
		argMap.put("c", "Order");
		argMap.put("sku_ids",  CartOperation.getSkuIdJson(sku_ids));
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getFareofOrder(long[] sku_ids, long addressid,
			final int requestId, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Order");
		argMap.put("c", "calcFare");
		argMap.put("sku_ids",  CartOperation.getSkuIdJson(sku_ids));
		argMap.put("address_id", addressid + "");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getPaymentList(long orderId, final int requestId,
			OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Order");
		argMap.put("c", "getPaymentList");
		argMap.put("order_id", orderId+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void submitOrder(OrderBeforePosted orderBeforePosted,
			final int requestId, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Order");
		argMap.put("c", "submitOrder");
		argMap.put("sku_ids",  CartOperation.getSkuIdJson(orderBeforePosted.getSku_ids()));
		argMap.put("address_id", orderBeforePosted.getAddress().getAddress_id()+"");
		argMap.put("payment_type", orderBeforePosted.getPayment_type()+"");
		argMap.put("remark", orderBeforePosted.getRemark());
		argMap.put("is_invoice", orderBeforePosted.getIs_invoice()+"");
		argMap.put("invoice_title", orderBeforePosted.getInvoice_title());
		argMap.put("voucher_id", orderBeforePosted.getVoucher() == null ? "" : orderBeforePosted.getVoucher().getVoucher_id()+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void deleteOrder(Long order_id , final int requestId, OnHttpCallBack callBack, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "deleteOrder");
		argMap.put("order_id",  order_id+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void cancelOrder(Long order_id , final int requestId, OnHttpCallBack callBack, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "cancelOrder");
		argMap.put("order_id",  order_id+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void getCommentListByOrderId(long order_id , final int requestId, OnHttpCallBack callBack, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getCommentListByOrderId");
		argMap.put("order_id",  order_id+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
	public static void commentOrder(long order_id, long sku_id, int score, String content, final int requestId, OnHttpCallBack callBack, Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "commentOrder");
		argMap.put("order_id",  order_id+"");
		argMap.put("sku_id",  sku_id+"");
		argMap.put("score",  score+"");
		argMap.put("content",  content);
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}
	
}
