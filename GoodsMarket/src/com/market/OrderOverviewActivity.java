package com.market;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.OrderDetail;
import com.market.bean.OrderLog;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.UnitTools;

public class OrderOverviewActivity extends BaseActivity implements
		OnClickListener, OnHttpCallBack {

	private int morderId;
	private Resources resources;
	private OrderDetail orderDetail;
	private TextView tv_orderoverviewNumber;
	private TextView tv_ordercreatedTime;
	private TextView tv_orderStatus;
	private TextView tv_recevierName;
	private TextView tv_recevierPhone;
	private TextView tv_recevierAddress;
	private TextView tv_purchasingway;
	private TextView tv_invoicetypeinfo;
	private TextView tv_invoiceheadinfo;
	private TextView tv_ordergoodsvalue;
	private TextView tv_ordergoodstallage;
	private TextView tv_orderfreight;
	private TextView tv_order_overiview_fee;
	private EditText et_orderremark;
	private RelativeLayout rl_order_detail;
	private RelativeLayout rl_order_perchasingway;
	private Button btn_orderrefund;
	private Button btn_ordertracking;
	private Button btn_orderpurchase;
	private Button btn_gotocomment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order_detail);
		initResource();
		initTitle();
		initView();
		initData();
	}

	private void initResource()
	{
		resources = this.getResources();
	}
	
	private void initTitle() {
		this.setActivityTitle(resources
				.getString(R.string.order_orderoverviewTitle));
		this.setDeftualtHeaderLeftButton();
		this.setImagenTextHeaderRightButton(R.drawable.delete_grey,
				resources.getString(R.string.delete), this);
	}

	private void initData() {
		Intent intent = this.getIntent();
		long morderId = intent.getLongExtra(Constants.LONGEXTRA, -1);
		Log.d("morderId "+morderId);
		if (morderId > -1)
		{
			OrderHttpOperation.getOrder(Constants.CALLBACK_FLAG_GET_ORDER,
					morderId, this, this);
			this.showProcessDialog();
		}
	}

	private void initView()
	{

		tv_orderoverviewNumber = (TextView)this.findViewById(R.id.tv_orderoverviewNumber);
		tv_orderStatus = (TextView)this.findViewById(R.id.tv_orderStatus);
		tv_ordercreatedTime = (TextView)this.findViewById(R.id.tv_ordercreatedTime);
		
		tv_recevierName = (TextView)this.findViewById(R.id.tv_recevierName);
		tv_recevierPhone = (TextView)this.findViewById(R.id.tv_recevierPhone);
		tv_recevierAddress = (TextView)this.findViewById(R.id.tv_recevierAddress);
		tv_purchasingway = (TextView)this.findViewById(R.id.tv_purchasingway);
		
		tv_invoicetypeinfo = (TextView)this.findViewById(R.id.tv_invoicetypeinfo);
		tv_invoiceheadinfo = (TextView)this.findViewById(R.id.tv_invoiceheadinfo);
		tv_ordergoodsvalue = (TextView)this.findViewById(R.id.tv_ordergoodsvalue);
		tv_ordergoodstallage = (TextView)this.findViewById(R.id.tv_ordergoodstallage);
		tv_orderfreight = (TextView)this.findViewById(R.id.tv_orderfreight);
		tv_order_overiview_fee = (TextView)this.findViewById(R.id.tv_order_overiview_fee);
		et_orderremark = (EditText)this.findViewById(R.id.et_orderremark);
		rl_order_detail = (RelativeLayout)this.findViewById(R.id.rl_order_detail);
		btn_orderrefund = (Button)this.findViewById(R.id.btn_orderrefund);
		btn_ordertracking = (Button)this.findViewById(R.id.btn_ordertracking);
		btn_orderpurchase = (Button)this.findViewById(R.id.btn_orderpurchase);
		rl_order_perchasingway = (RelativeLayout)this.findViewById(R.id.rl_order_perchasingway);
		btn_gotocomment = (Button)this.findViewById(R.id.btn_gotocomment);
		btn_orderrefund.setOnClickListener(this);
		btn_ordertracking.setOnClickListener(this);
		btn_orderpurchase.setOnClickListener(this);
		rl_order_detail.setOnClickListener(this);
		btn_gotocomment.setOnClickListener(this);
		rl_order_perchasingway.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		Intent intent = new Intent();
		switch(view.getId())
		{
			case R.id.rl_order_detail:
				intent.putExtra(Constants.OBJECTEXTRA, this.orderDetail);
				intent.setClass(this, GoodsinOrderActivity.class);
				this.startActivity(intent);
				break;
			case R.id.btn_ordertracking:
				intent.putExtra(Constants.OBJECTEXTRA, this.orderDetail);
				intent.setClass(this, OrderLogActivity.class);
				this.startActivity(intent);
				break;
			case R.id.rl_order_perchasingway:
				intent.putExtra(Constants.LONGEXTRA, this.orderDetail.getOrder_id().longValue());
				intent.setClass(this, PaymentChosenActivity.class);
				this.startActivity(intent);
				break;
			case R.id.btn_gotocomment:
				intent.putExtra(Constants.LONGEXTRA, this.orderDetail.getOrder_id().longValue());
				intent.setClass(this, OrderCommentListActivity.class);
				this.startActivity(intent);
				break;
		}
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (httpResult.isSuccess()) {
			if (requestId == Constants.CALLBACK_FLAG_GET_ORDER) {
				orderDetail = JsonToBean.json2OrderDetail(httpResult.getResult());
				setView(orderDetail);
			}
			
		} else {
			this.showMessageToast(httpResult.getResult());
		}
		this.closeProcessDialog();
	}

	private void setView(OrderDetail orderDetail)
	{
		if(orderDetail == null)
			return;
		tv_orderoverviewNumber.setText(orderDetail.getOrder_no());
		tv_ordercreatedTime.setText(UnitTools.long2DateByDefault(orderDetail.getCreate_time()));
		tv_orderStatus.setText(orderDetail.getStatus_name());
		tv_recevierName.setText(orderDetail.getAccept_name());
		tv_recevierPhone.setText(orderDetail.getMobile());
		tv_recevierAddress.setText(orderDetail.getAddress());
		
		tv_purchasingway.setText(orderDetail.getPayment_type_name());
		tv_invoicetypeinfo.setText(orderDetail.getInvoice_content());
		tv_invoiceheadinfo.setText(orderDetail.getInvoice_title());
		tv_ordergoodsvalue.setText("гд"+orderDetail.getReal_amount());
		tv_ordergoodstallage.setText("гд"+orderDetail.getTaxes());
		tv_orderfreight.setText("гд"+orderDetail.getReal_freight());
		tv_order_overiview_fee.setText("гд"+orderDetail.getOrder_amount());
		et_orderremark.setText(orderDetail.getUser_remark());
		
		boolean isShowPay =  orderDetail.getIs_show_pay() == null ? false : orderDetail.getIs_show_pay();
		boolean isShowCancel =  orderDetail.getIs_show_cancel() == null ? false : orderDetail.getIs_show_cancel();
		boolean isShowComment =  orderDetail.getIs_show_comment() == null ? false : orderDetail.getIs_show_comment();
		boolean isShowDelete =  orderDetail.getIs_show_delete() == null ? false : orderDetail.getIs_show_delete();
		boolean isShowReceipt =  orderDetail.getIs_show_receipt() == null ? false : orderDetail.getIs_show_receipt();
		boolean isShowRefund =  orderDetail.getIs_show_refund() == null ? false : orderDetail.getIs_show_refund();
		if(isShowPay)
			btn_orderpurchase.setVisibility(View.VISIBLE);
		if(isShowRefund)
			btn_orderpurchase.setVisibility(View.VISIBLE);
		if(isShowComment)
			btn_gotocomment.setVisibility(View.VISIBLE);
		
		btn_ordertracking.setVisibility(View.VISIBLE);
		
	}
	
	
}
