package com.market;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.market.adapter.OrderLogAdapter;
import com.market.bean.OrderDetail;
import com.market.bean.OrderLog;
import com.market.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class OrderLogActivity extends BaseActivity{
	private OrderLogAdapter orderLogAdapter;
	private ListView lv_orderloglist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_orderlog);
		initData();
		initView();
		initTitle();
		
	}
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(getResources().getString(R.string.order_ordertracking));
	}
	
	private void initData()
	{
		Intent intent = this.getIntent();
		OrderDetail orderDetail = (OrderDetail)intent.getSerializableExtra(Constants.OBJECTEXTRA);
		OrderLog[] log = null;
		if(orderDetail != null)
			log = orderDetail.getOrder_log_list();
		orderLogAdapter = new OrderLogAdapter(this, log);
	}
	
	private void initView()
	{
		lv_orderloglist = (ListView)this.findViewById(R.id.lv_orderloglist);
		lv_orderloglist.setAdapter(this.orderLogAdapter);
	}
}
