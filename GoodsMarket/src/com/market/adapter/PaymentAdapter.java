package com.market.adapter;

import com.haoyikuai.shop.android.R;
import com.market.bean.Payment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PaymentAdapter extends  BaseAdapter{
	private Payment[] paymentarr;
	private Context context;
	
	public PaymentAdapter(Context context)
	{
		this.context = context;
	}
	
	
	@Override
	public int getCount() {
		if(paymentarr != null)
			return paymentarr.length;
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(paymentarr != null && paymentarr.length > index)
			return paymentarr[index];
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
		if(context == null || this.paymentarr == null)
			return null;
		Payment paymen = paymentarr[index];
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.item_payment, null);
		}
		TextView tv_paymentway = (TextView)contentView.findViewById(R.id.tv_paymentway);
		tv_paymentway.setText(paymen.getName());
		return contentView;
	}


	public Payment[] getPaymentarr() {
		return paymentarr;
	}


	public void setPaymentarr(Payment[] paymentarr) {
		this.paymentarr = paymentarr;
	}

}
