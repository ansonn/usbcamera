package com.market.adapter;

import com.haoyikuai.shop.android.R;
import com.market.bean.OrderLog;
import com.market.utils.UnitTools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderLogAdapter extends BaseAdapter{

	private Context context;
	private OrderLog[] orderLogArr;
	
	public OrderLogAdapter(Context context, OrderLog[] orderLogArr)
	{
		this.context = context;
		this.orderLogArr = orderLogArr;
	}
	
	public void setOrderLogArr(OrderLog[] orderLogArr)
	{
		this.orderLogArr = orderLogArr;
	}
	
	@Override
	public int getCount() {
		if(orderLogArr != null)
			return orderLogArr.length;
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(orderLogArr!=null && orderLogArr.length > index)
				return orderLogArr[index];
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		View contentView = view;
		if(orderLogArr == null || orderLogArr.length < index || context == null)
			return null;
		OrderLog orderlog = orderLogArr[index];
		if(contentView == null)
			contentView = LayoutInflater.from(context).inflate(R.layout.item_orderloglist	, null);
		
		TextView tv_orderlogstatus = (TextView)contentView.findViewById(R.id.tv_orderlogstatus);
		TextView tv_orderlogtimes = (TextView)contentView.findViewById(R.id.tv_orderlogtimes);
		TextView tv_orderclient = (TextView)contentView.findViewById(R.id.tv_orderclient);
		
		tv_orderlogstatus.setText(orderlog.getNote());
		tv_orderlogtimes.setText(UnitTools.long2DateByDefault(orderlog.getTime()));
		tv_orderclient.setText(orderlog.getUser());
		
		return contentView;
	}

}
