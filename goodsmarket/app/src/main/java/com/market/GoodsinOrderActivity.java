package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.GoodsinOrderAdapter;
import com.market.bean.OrderDetail;
import com.market.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class GoodsinOrderActivity extends BaseActivity{
	private ListView lv_goodslistinorder;
	private GoodsinOrderAdapter goodsinOrderAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_goodsinorder);
		initData();
		initView();
		initTitle();
	}
	
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(getResources().getString(R.string.order_goodsdetail));
	}

	private void initData()
	{
		goodsinOrderAdapter = new GoodsinOrderAdapter(this);
		Intent intent = this.getIntent();
		OrderDetail orderGoods = (OrderDetail)intent.getSerializableExtra(Constants.OBJECTEXTRA);
		if(orderGoods != null)
			goodsinOrderAdapter.setOrderGoods_list(orderGoods.getOrder_goods());
	}

	private void initView()
	{
		lv_goodslistinorder = (ListView)this.findViewById(R.id.lv_goodslistinorder);
		lv_goodslistinorder.setAdapter(goodsinOrderAdapter);
	}
}
