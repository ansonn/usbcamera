package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.VoucherAdapter;
import com.market.bean.Voucher;
import com.market.bean.VoucherList;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;

public class VoucherListActivity extends BaseActivity implements OnHttpCallBack, OnClickListener{
	
	private VoucherAdapter voucherAdapter;
	private ListView lv_couponlist;
	private View ll_coupon_insert;
	private View btn_coupon_add_ensure;
	private EditText edt_coupon_numberSigned;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_coupon_list);
		initdata();
		initView();
		initTitle();
	}
	
	private void initTitle()
	{
		this.setActivityTitle(getResources().getString(R.string.coupon_title));
		this.setDeftualtHeaderLeftButton();
		this.setStringHeaderRightButton(getResources().getString(R.string.coupon_header_add), this);
	}
	
	private void initView()
	{
		ll_coupon_insert = this.findViewById(R.id.ll_coupon_insert);
		btn_coupon_add_ensure = this.findViewById(R.id.btn_coupon_add_ensure);
		edt_coupon_numberSigned = (EditText)this.findViewById(R.id.edt_coupon_numberSigned);
		btn_coupon_add_ensure.setOnClickListener(this);
	}
	
	private void initdata()
	{
		Intent intent = this.getIntent();
		VoucherList vl = (VoucherList)intent.getSerializableExtra(Constants.OBJECTEXTRA);
		lv_couponlist = (ListView)this.findViewById(R.id.lv_couponlist);
		if(vl == null)
			HttpComm.getVoucherList(this, Constants.CALLBACK_FLAG_GET_VERCHERLISR, this);
		else
		{
			voucherAdapter = new VoucherAdapter(this, vl.getVoucher_list());
			lv_couponlist.setAdapter(voucherAdapter);
			voucherAdapter.notifyDataSetChanged();
		}
		lv_couponlist.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View thiz, int index,
					long position) {
				Voucher voucher = null;
				if(voucherAdapter != null)
					voucher = (Voucher)voucherAdapter.getItem(index);
				if(voucher != null)
				{
					Intent intent = new Intent();
					intent.putExtra(Constants.OBJECTEXTRA, voucher);
					setResult(Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE2, intent);
					finish();
				}
			}
			
		});
		
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(httpResult.isSuccess())
		{
			if(Constants.CALLBACK_FLAG_GET_VERCHERLISR == requestId)
			{
				VoucherList vl =JsonToBean.json2VoucherList(httpResult.getResult());
				voucherAdapter = new VoucherAdapter(this, vl.getVoucher_list());
				lv_couponlist.setAdapter(voucherAdapter);
				voucherAdapter.notifyDataSetChanged();
			}
			else if(Constants.CALLBACK_FLAG_GET_ACTIVEVOUCHER == requestId)
			{
				this.closeProcessDialog();
			}
		}else
		{
			this.forceCloseProcessDialog();
			this.showMessageToast(httpResult.getResult());
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId())
		{
			case ResourceLocation.BUTTON_HEADER_RIGHT:
				Log.d("lv_couponlist != null :"+(lv_couponlist != null));
				if(ll_coupon_insert != null)
					ll_coupon_insert.setVisibility(View.VISIBLE);
				setHeaderRightVisible(View.INVISIBLE);
				break;
			case R.id.btn_coupon_add_ensure:
				if(edt_coupon_numberSigned == null)
					break;
				String key = edt_coupon_numberSigned.getText().toString().trim();
				HttpComm.activeVoucher(Constants.CALLBACK_FLAG_GET_ACTIVEVOUCHER, key, this, this);
				if(ll_coupon_insert != null)
					ll_coupon_insert.setVisibility(View.GONE);
				setHeaderRightVisible(View.VISIBLE);
				this.showProcessDialog();
				break;
		}
	}
	
	private void setHeaderRightVisible(int visible)
	{
		View viewheadr = this.findViewById(ResourceLocation.BUTTON_HEADER_RIGHT);
		if(viewheadr != null)
			viewheadr.setVisibility(visible);
	}
}
