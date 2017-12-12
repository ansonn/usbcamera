package com.market.adapter;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.AddressListActivity.LISTSTYLE;
import com.market.bean.Address;

public class AddressAdapter extends BaseAdapter {
	private ArrayList<Address> addressList;
	private Context context;
	private int IMGBACK;
	private LISTSTYLE liststyle = LISTSTYLE.MANGER_LIST;
	
	public AddressAdapter(ArrayList<Address> addressList , Context context)
	{
		this.addressList = addressList;
		this.context = context;
		IMGBACK = R.drawable.godetail;
	}
	
	public AddressAdapter(Context context,LISTSTYLE liststyle)
	{
		this.context = context;
		this.liststyle = liststyle;
	}
	
	public void setListStyle(LISTSTYLE liststyle)
	{
		this.liststyle = liststyle;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (addressList != null)
			return addressList.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if (addressList != null)
			return addressList.get(index);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View contentView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if(contentView == null)
		{
			LayoutInflater ifl = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			view = ifl.inflate(R.layout.item_address_list, null);
		}
		else
		{
			view = contentView;
		}
		
		TextView tv_itemaddressname = (TextView)view.findViewById(R.id.tv_itemaddressname);
		TextView tv_itemaddressmoblie = (TextView)view.findViewById(R.id.tv_itemaddressmoblie);
		TextView tv_itemaddressdesc = (TextView)view.findViewById(R.id.tv_itemaddressdesc);
		ImageView isdefaultTail = (ImageView)view.findViewById(R.id.iv_addressisdefault_tail);
		
		if(liststyle == LISTSTYLE.MANGER_LIST)
		{
			(view.findViewById(R.id.ll_markaddressisdefault)).setVisibility(View.GONE);
			
		}
		else
		{
			isdefaultTail = (ImageView)view.findViewById(R.id.iv_addressisdefault);
			IMGBACK = R.drawable.deselect;
		}
		
		if(addressList != null && addressList.size() > index)
		{
			Address address = addressList.get(index);
			tv_itemaddressname.setText(address.getName());
			tv_itemaddressmoblie.setText(address.getMobile());
			tv_itemaddressdesc.setText(address.getProvince().name+address.getCity().name + address.getCounty().name+address.getAddress());
			if(address.getIs_default() > 0 )
				isdefaultTail.setImageResource(R.drawable.selected);
			else
			{
				isdefaultTail.setImageResource(IMGBACK);
			}
		}
		
		return view;
	}

	public ArrayList<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(ArrayList<Address> addressList) {
		this.addressList = addressList;
	}
}
