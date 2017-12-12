package com.market.adapter;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.market.OrderCommentListActivity;
import com.market.OrderGoodsCommentActivity;
import com.market.OrderOverviewActivity;
import com.market.bean.Order;
import com.market.bean.OrderGoods;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.UnitTools;

public class OrderListAdapter extends BaseAdapter implements OnClickListener{
	private Context context;
	private ArrayList<Order> orderList;
	private OnHttpCallBack callback;
	private BitmapUtils bitmapUtils;
	private ArrayList<Order> deletedList;
	private int imgWidth;
	private int imgHeight;
	private int imgPadding;

	public OrderListAdapter(Context context,OnHttpCallBack callback) {
		this.context = context;
		deletedList = new ArrayList<Order>();
		bitmapUtils = new BitmapUtils(context);
		Resources re = context.getResources();
		this.imgWidth = (int) re.getDimension(R.dimen.ordergoods_img_width);
		this.imgHeight = (int) re.getDimension(R.dimen.ordergoods_img_height);
		this.imgPadding = (int) re.getDimension(R.dimen.common_margin);
		this.callback = callback;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (orderList != null)
			return orderList.size();
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View contentview, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = null;
		final Order order = orderList.get(index);

		LayoutInflater lf = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		view = lf.inflate(R.layout.item_oreder, null);
		LinearLayout ll_orderOverviews = (LinearLayout) view
				.findViewById(R.id.ll_orderOverviews);

		TextView tv_orderTotalAmount = (TextView) view
				.findViewById(R.id.tv_orderTotalAmount);
		TextView tv_orderStatus = (TextView) view
				.findViewById(R.id.tv_orderStatus);
		TextView tv_ordernumber = (TextView) view
				.findViewById(R.id.tv_ordernumber);
		TextView tv_orderCreatedTime = (TextView) view
				.findViewById(R.id.tv_orderCreatedTime);
		
		
		initButton(order, view);
		
		tv_orderTotalAmount.setText(order.getOrder_amount() + "");
		tv_orderStatus.setText(order.getStatus_name());
		tv_ordernumber.setText(order.getOrder_no());
		tv_orderCreatedTime.setText(UnitTools.long2DateByDefault(order
				.getCreate_time()));

		int goodsLength = 0;
		OrderGoods[] ogs = order.getOrder_goods();
		if (ogs != null)
			goodsLength = ogs.length;
		if (goodsLength > 0) {
			ImageView ivoverView;
			for (int i = 0; i < goodsLength; i++) {
				ivoverView = new ImageView(context);
				ivoverView.setLayoutParams(new LinearLayout.LayoutParams(
						imgWidth, imgHeight));
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ivoverView
						.getLayoutParams();
				lp.setMargins(imgPadding, imgPadding, imgPadding, imgPadding);
				ll_orderOverviews.addView(ivoverView);
				bitmapUtils.display(ivoverView, ogs[i].getThumb(),
						new BitmapLoadCallBack<View>() {

							@Override
							public void onLoadCompleted(View arg0, String arg1,
									Bitmap arg2, BitmapDisplayConfig arg3,
									BitmapLoadFrom arg4) {
								((ImageView) arg0).setImageBitmap(arg2);
							}

							@Override
							public void onLoadFailed(View arg0, String arg1,
									Drawable arg2) {
								((ImageView) arg0)
										.setImageResource(R.drawable.loading);

							}

							@Override
							public void onPreLoad(View container, String uri,
									BitmapDisplayConfig config) {
								// TODO Auto-generated method stub
								((ImageView) container)
										.setImageResource(R.drawable.loading);
							}

						});
			}
		}

		return view;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(Order[] orderarr) {
		if (this.orderList == null)
			this.orderList = new ArrayList<Order>();
		for (int i = 0; i < orderarr.length; i++) {
			orderList.add(orderarr[i]);
		}
	}
	
	public void setNewOrderList(Order[] orderarr) {
		this.orderList = new ArrayList<Order>();
		for (int i = 0; i < orderarr.length; i++) {
			orderList.add(orderarr[i]);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Order order = (Order)view.getTag();
		Intent intent;
		switch(view.getId())
		{
		
		
		case R.id.btn_orderreceipt:
			Log.d("btn_orderreceipt ");
			break;
		case R.id.btn_orderdelete:
			Log.d("btn_orderdelete ");
			OrderHttpOperation.deleteOrder(order.getOrder_id(), Constants.CALLBACK_FLAG_DELETEDORDER, this.callback, context);
			break;
		case R.id.btn_ordercomment:
			Log.d("btn_ordercomment ");
			intent = new Intent();
			intent.putExtra(Constants.LONGEXTRA, order.getOrder_id());
			intent.setClass(context, OrderCommentListActivity.class);
			context.startActivity(intent);
			break;
		case R.id.btn_ordercancel:
			Log.d("btn_ordercancel ");
			break;
		case R.id.btn_orderrefund:
			Log.d("btn_orderrefund ");
			break;
		case R.id.btn_orderpurchase:
		case R.id.ll_ordernumber:
			Log.d("ll_ordernumber ");
			intent = new Intent();
			long orderid = order.getOrder_id();
			intent.putExtra(Constants.LONGEXTRA,orderid);
			Log.d("Constants.LONGEXTRA "+orderid);
			intent.setClass(context, OrderOverviewActivity.class);
			context.startActivity(intent);
			break;
		}
	}
	
	private void initButton(Order order, View view)
	{
		/**btn_orderreceipt
		 * btn_orderdelete
		 * btn_ordercomment
		 * btn_ordercancel
		 * btn_orderrefund
		 * btn_orderpurchase
		 */
		LinearLayout ll_ordernumber = (LinearLayout)view.findViewById(R.id.ll_ordernumber);
		Button btn_orderreceipt = (Button)view.findViewById(R.id.btn_orderreceipt);
		Button btn_orderdelete = (Button)view.findViewById(R.id.btn_orderdelete);
		Button btn_ordercomment = (Button)view.findViewById(R.id.btn_ordercomment);
		Button btn_ordercancel = (Button)view.findViewById(R.id.btn_ordercancel);
		Button btn_orderpurchase = (Button)view.findViewById(R.id.btn_orderpurchase);
		Button btn_orderrefund = (Button)view.findViewById(R.id.btn_orderrefund);
		
		btn_orderreceipt.setTag(order);
		btn_orderdelete.setTag(order);
		btn_ordercomment.setTag(order);
		btn_ordercancel.setTag(order);
		btn_orderpurchase.setTag(order);
		btn_orderrefund.setTag(order);
		ll_ordernumber.setTag(order);
		
		
		btn_orderreceipt.setOnClickListener(this);
		btn_orderdelete.setOnClickListener(this);
		btn_ordercomment.setOnClickListener(this);
		btn_ordercancel.setOnClickListener(this);
		btn_orderpurchase.setOnClickListener(this);
		btn_orderrefund.setOnClickListener(this);
		ll_ordernumber.setOnClickListener(this);
		
		boolean isShowDelete = order.getIs_show_delete() == null ? false : order.getIs_show_delete();
		boolean isShowReceipt = order.getIs_show_receipt() == null ? false : order.getIs_show_receipt();
		boolean isShowComment = order.getIs_show_comment() == null ? false : order.getIs_show_comment();
		boolean isShowCancel = order.getIs_show_cancel() == null ? false : order.getIs_show_cancel();
		boolean isShowPurchase = order.getIs_show_pay() == null ? false : order.getIs_show_pay();
		boolean isShowRefund = order.getIs_show_refund() == null ? false : order.getIs_show_refund();
		if(!isShowDelete)
			btn_orderdelete.setVisibility(View.GONE);
		else
			btn_orderdelete.setVisibility(View.VISIBLE);

		if(!isShowReceipt)
			btn_orderreceipt.setVisibility(View.GONE);
		else
			btn_orderreceipt.setVisibility(View.VISIBLE);
		
		if(!isShowComment)
			btn_ordercomment.setVisibility(View.GONE);
		else
			btn_ordercomment.setVisibility(View.VISIBLE);
		
		if(!isShowCancel)
			btn_ordercancel.setVisibility(View.GONE);
		else
			btn_ordercancel.setVisibility(View.VISIBLE);
		
		if(!isShowPurchase)
			btn_orderpurchase.setVisibility(View.GONE);
		else
			btn_orderpurchase.setVisibility(View.VISIBLE);
		
		if(!isShowRefund)
			btn_orderrefund.setVisibility(View.GONE);
		else
			btn_orderrefund.setVisibility(View.VISIBLE);
	}
	
	public void deletedOrders()
	{
		
	}
}
