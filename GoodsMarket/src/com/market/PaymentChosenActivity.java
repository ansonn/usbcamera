package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.PaymentAdapter;
import com.market.bean.PayBillBean;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class PaymentChosenActivity extends BaseActivity implements OnHttpCallBack{
	private long orderid;
	private PaymentAdapter paymentAdapter;
	private ListView lv_paymentlist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_paymentway);
		initData();
		initView();
		initTitle();
	}
	
	private void initData()
	{
		Intent intent = this.getIntent();
		orderid = intent.getLongExtra(Constants.LONGEXTRA, -1);
		paymentAdapter = new PaymentAdapter(this);
		OrderHttpOperation.getPaymentList(orderid, Constants.CALLBACK_FLAG_OBTAINPAYMENT, this, this);
		
	}
	private void initView()
	{
		lv_paymentlist = (ListView)this.findViewById(R.id.lv_paymentlist);
		lv_paymentlist.setAdapter(paymentAdapter);
	}
	private void initTitle()
	{
		this.setActivityTitle(getResources().getString(R.string.payment_title));
		this.setDeftualtHeaderLeftButton();
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if(requestId == Constants.CALLBACK_FLAG_OBTAINPAYMENT)
		{
			if(httpResult.isSuccess())
			{
				PayBillBean pbb = JsonToBean.json2PayBillBean(httpResult.getResult());
				paymentAdapter.setPaymentarr(pbb.getPayment_list());
				paymentAdapter.notifyDataSetChanged();
			}
		}
	}
	
}
