package com.market.adapter;

import java.util.List;

import com.market.bean.Voucher;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CouponListAdapt extends BaseAdapter {
	private List<Voucher> listVoucher;
	private Context mContext;
	
	public CouponListAdapt(Context mContex, List<Voucher> listVoucher) {
		// TODO Auto-generated constructor stub
		this.mContext = mContex;
		this.listVoucher = listVoucher;
	}
	
	public CouponListAdapt(Context mContex) {
		// TODO Auto-generated constructor stub
		this.mContext = mContex;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(listVoucher != null)
			return listVoucher.size();
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
	public View getView(int position, View View, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater lf = (LayoutInflater)this.mContext.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		
		return null;
	}

	public void setListVoucher(List<Voucher> listVoucher) {
		this.listVoucher = listVoucher;
	}

	public List<Voucher> getListVoucher() {
		return this.listVoucher;
	}
}
