package com.market;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.market.adapter.GoodsPurchasedListAdapter;
import com.market.bean.SkuBean;
import com.market.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class GoodsPurchasedListActivity extends BaseActivity{
	private GoodsPurchasedListAdapter goodsAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_goodspurchasedlist);
		initData();
		initView();
		initTitile();
	}
	
	private void initData()
	{
		Intent intent = this.getIntent();
		ArrayList<SkuBean> skubean_list = (ArrayList<SkuBean>)intent.getSerializableExtra(Constants.OBJECTEXTRA);
		goodsAdapter = new GoodsPurchasedListAdapter(this);
		goodsAdapter.setSkuBean_list(skubean_list);
		
	}
	
	private void initView()
	{
		ListView lv_goodsPurchasedList = (ListView)this.findViewById(R.id.lv_goodsPurchasedList);
		if(goodsAdapter != null)
			lv_goodsPurchasedList.setAdapter(goodsAdapter);
		
	}
	
	private void initTitile()
	{
		this.setActivityTitle(getResources().getString(R.string.order_goodspurchasedlistTitle));
		this.setDeftualtHeaderLeftButton();
		
	}
	
	
}
