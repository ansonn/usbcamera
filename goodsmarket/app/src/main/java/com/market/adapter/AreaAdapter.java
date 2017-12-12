package com.market.adapter;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.AreaBean;

public class AreaAdapter extends BaseAdapter{
	
	private ArrayList<AreaBean> arealist;


	private Context context;
	
	public AreaAdapter(Context context)
	{
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(arealist != null)
			return arealist.size();
		
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arealist.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		View contentView;
		if(view == null)
		{
			LayoutInflater lf = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			view = lf.inflate(R.layout.item_area_list, null);
			contentView = view;
		}
		else
			contentView = view;
			
			TextView area_description = (TextView)contentView.findViewById(R.id.tv_address_description);
			area_description.setText(arealist.get(index).name);
		
		return view;
	}

	public ArrayList<AreaBean> getArealist() {
		return arealist;
	}

	public void setArealist(ArrayList<AreaBean> arealist) {
		this.arealist = arealist;
	}
}
