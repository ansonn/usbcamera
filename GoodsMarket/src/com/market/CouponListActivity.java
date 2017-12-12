package com.market;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.utils.ResourceLocation;

/**
 * �Ż�ȯչʾ
 * @author pumkid
 *
 */

public class CouponListActivity extends BaseActivity implements OnClickListener{
	private View ll_coupon_insert;
	private View btn_right;
	private int ibtn_right = 0;
	private ListView lv_couponlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_coupon_list);
		initTitle();
		initView();
		

	}
	
	private void initTitle()
	{
		Resources resources = this.getResources();
		this.setActivityTitle(resources.getString(R.string.coupon_title));
		this.setDeftualtHeaderLeftButton();
		ibtn_right = this.setStringHeaderRightButton(resources.getString(R.string.coupon_header_add), this);
		
	}
	
	private void initView()
	{
		ll_coupon_insert = this.findViewById(R.id.ll_coupon_insert);
		lv_couponlist = (ListView)this.findViewById(R.id.lv_couponlist);
		if(ibtn_right != 0)
		btn_right = this.findViewById(ibtn_right);
		
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId())
		{
			case ResourceLocation.BUTTON_HEADER_RIGHT:
			{
					if(btn_right != null && ll_coupon_insert != null)
					{
						btn_right.setVisibility(View.INVISIBLE);
						ll_coupon_insert.setVisibility(View.VISIBLE);
					}
			}
		}
	}
}
