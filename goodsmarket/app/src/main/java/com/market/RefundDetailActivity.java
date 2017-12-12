package com.market;

import com.haoyikuai.shop.android.R;
import com.market.bean.RefundBean;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.RefundHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.UnitTools;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class RefundDetailActivity extends BaseActivity implements OnHttpCallBack{
	private long refund_id;
	private TextView tv_refundorderid;
	private TextView tv_refundcreatetime;
	private TextView tv_refundway;
	private TextView tv_refundreason;
	private TextView tv_refundresult;
	private TextView tv_refunddealtime;
	private TextView tv_refundmomeydirct;
	private TextView tv_refundaccount;
	private TextView tv_refundamount;
	private Resources resources;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_refunddetail);
		initData();
		initView();
		initTitle();
		this.showProcessDialog();
		RefundHttpOperation.getRefundInfo(refund_id, this, Constants.CALLBACK_FLAG_GETREFUNDINFO, this);
	}
	
	private void initData()
	{
		resources = this.getResources();
		Intent intent = this.getIntent();
		refund_id = intent.getLongExtra(Constants.LONGEXTRA, -1);
	}
	
	private void initView()
	{
		tv_refundorderid = (TextView)this.findViewById(R.id.tv_refundorderid);
		tv_refundcreatetime = (TextView)this.findViewById(R.id.tv_refundcreatetime);
		tv_refundway = (TextView)this.findViewById(R.id.tv_refundway);
		tv_refundreason = (TextView)this.findViewById(R.id.tv_refundreason);
		tv_refundresult = (TextView)this.findViewById(R.id.tv_refundresult);
		tv_refunddealtime = (TextView)this.findViewById(R.id.tv_refunddealtime);
		tv_refundmomeydirct = (TextView)this.findViewById(R.id.tv_refundmomeydirct);
		tv_refundaccount = (TextView)this.findViewById(R.id.tv_refundaccount);
		tv_refundamount  = (TextView)this.findViewById(R.id.tv_refundamount);
	}
	
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(getResources().getString(R.string.refund_refunddetail));
	}
	
	private void setViews(RefundBean refundBean)
	{
		if(refundBean == null || resources == null)
			return;
		String handlingTime = "";
		String createTime = "";
		if(refundBean.getCreate_time() != null)
			createTime = UnitTools.long2DateByDefault(refundBean.getCreate_time());
		if(refundBean.getHandling_time() != null)
			handlingTime = UnitTools.long2DateByDefault(refundBean.getHandling_time());
		CompleteRefundDetail(tv_refundorderid,R.string.refund_orderid,refundBean.getOrder_id()+"");
		CompleteRefundDetail(tv_refundcreatetime,R.string.refund_createtme,createTime);
		CompleteRefundDetail(tv_refundway,R.string.refund_way,refundBean.getRefund_type_name());
		CompleteRefundDetail(tv_refundreason,R.string.refund_reason,refundBean.getNote());
		CompleteRefundDetail(tv_refundresult,R.string.refund_result,refundBean.getStatus_name());
		CompleteRefundDetail(tv_refunddealtime,R.string.refund_dealtime,handlingTime);
		CompleteRefundDetail(tv_refundmomeydirct,R.string.refund_momeydirct,refundBean.getRefund_type_name());
		CompleteRefundDetail(tv_refundaccount,R.string.refund_account,refundBean.getAccount_name());
		CompleteRefundDetail(tv_refundamount,R.string.refund_amount,refundBean.getAmount()+"");
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(requestId == Constants.CALLBACK_FLAG_GETREFUNDINFO)
		{
			if(httpResult.isSuccess())
			{
				RefundBean refundBean = JsonToBean.json2RefundBean(httpResult.getResult());
				setViews(refundBean);
			}
		}
		this.closeProcessDialog();
	}
	
	private void CompleteRefundDetail(TextView view , int resourceid, Object object)
	{
		if(object == null)
			object = new String("");
		if(view != null)
		view.setText(resources.getString(resourceid, object));
		
	}
}
