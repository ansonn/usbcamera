package com.market;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.Address;
import com.market.bean.OrderBeforePosted;
import com.market.bean.OrderId;
import com.market.bean.PaymentType;
import com.market.bean.SkuBean;
import com.market.bean.Voucher;
import com.market.bean.VoucherList;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.UnitTools;
import com.market.view.SwitchView;
import com.market.view.SwitchView.OnSwitchViewSeletedListener;

public class OrderEdittingActivity extends BaseActivity implements OnClickListener , OnHttpCallBack{
	private View ll_order_receiver_info;
	private View rl_order_perchasingway;
	private View rl_order_invoiceinfo;
	private View rl_order_detail;
	private View rl_order_coupon;
	
	private TextView tv_recevierName;
	private TextView tv_recevierPhone;
	private TextView tv_recevierAddress;
	private TextView tv_purchasingway;
	private TextView tv_invoicetypeinfo;
	private TextView tv_invoiceheadinfo;
	private TextView tv_ordergoodsvalue;
	private TextView tv_ordergoodstallage;
	private TextView tv_orderfreight;
	private TextView tv_tempforfeeshouldpay;
	private TextView tv_tmpforalgininvoiceinfo;
	private TextView tv_order_couponinfo;
	
	private EditText et_orderremark;
	
	private long sku_ids[];
	private String currentivHeader;
	private String currentivType;
	
	private SwitchView sv_needinvoiceinfo;
	private Double currentFreight;
	
	private Button btn_orderposted;
	
	private OrderBeforePosted orderBeforePosted ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_fill_order);
		initDate();
		initView();
		setTitle();
//		OrderHttpOperation.getInfobeforepostOrder(Constants.CALLBACK_FLAG_GET_INFOBEFOREPOSTORDER, this, this);
		HttpComm.getVoucherList(this, Constants.CALLBACK_FLAG_GET_INFOBEFOREPOSTORDER, this);
	}
	
	private void setTitle()
	{
		this.setActivityTitle(getResources().getString(R.string.order_fill));
		this.setDeftualtHeaderLeftButton();
	}
	private void initDate()
	{
		Intent intent = this.getIntent();
		sku_ids = intent.getLongArrayExtra(Constants.LONGARREXTRA);
		if(sku_ids != null)
		{
			this.showProcessDialog();
			OrderHttpOperation.getInfobeforepostOrder(sku_ids, Constants.CALLBACK_FLAG_CARTCHECKOUTBEFOREPURCHASED, this, this);
		}
	}
	
	private void initView()
	{
		ll_order_receiver_info = this.findViewById(R.id.ll_order_receiver_info);
		rl_order_perchasingway = this.findViewById(R.id.rl_order_perchasingway);
		rl_order_invoiceinfo = this.findViewById(R.id.rl_order_invoiceinfo);
		rl_order_detail = this.findViewById(R.id.rl_order_detail);
		rl_order_coupon = this.findViewById(R.id.rl_order_coupon);
		
		tv_recevierName = (TextView)this.findViewById(R.id.tv_recevierName);
		tv_recevierPhone = (TextView)this.findViewById(R.id.tv_recevierPhone);
		tv_recevierAddress = (TextView)this.findViewById(R.id.tv_recevierAddress);
		tv_purchasingway = (TextView)this.findViewById(R.id.tv_purchasingway);
		tv_invoicetypeinfo = (TextView)this.findViewById(R.id.tv_invoicetypeinfo);
		tv_ordergoodsvalue = (TextView)this.findViewById(R.id.tv_ordergoodsvalue);
		tv_ordergoodstallage = (TextView)this.findViewById(R.id.tv_ordergoodstallage);
		tv_orderfreight = (TextView)this.findViewById(R.id.tv_orderfreight);
		tv_tempforfeeshouldpay = (TextView)this.findViewById(R.id.tv_tempforfeeshouldpay);
		tv_invoiceheadinfo = (TextView)this.findViewById(R.id.tv_invoiceheadinfo);
		tv_tmpforalgininvoiceinfo = (TextView)this.findViewById(R.id.tv_tmpforalgininvoiceinfo);
		et_orderremark = (EditText)this.findViewById(R.id.et_orderremark);
		tv_order_couponinfo = (TextView)this.findViewById(R.id.tv_order_couponinfo);
		
		sv_needinvoiceinfo = (SwitchView)this.findViewById(R.id.sv_needinvoiceinfo);
		btn_orderposted = (Button)this.findViewById(R.id.btn_orderposted);
		
		btn_orderposted.setOnClickListener(this);
		rl_order_perchasingway.setOnClickListener(this);
		rl_order_detail.setOnClickListener(this);
		ll_order_receiver_info.setOnClickListener(this);
		rl_order_invoiceinfo.setOnClickListener(this);
		rl_order_coupon.setOnClickListener(this);
		
		sv_needinvoiceinfo.setOnSeletedListener(new OnSwitchViewSeletedListener() {
			
			@Override
			public void onseleted(View view) {
				// TODO Auto-generated method stub
				setPageDetail(orderBeforePosted);
			}
			
			@Override
			public void ondisseleted(View view) {
				// TODO Auto-generated method stub
				setPageDetail(orderBeforePosted);
			}
		});
	}

	
	

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		switch(view.getId())
		{
			case R.id.ll_order_receiver_info:
				intent.setClass(this, AddressListActivity.class);
				intent.putExtra(Constants.STYLEEXTRA, Constants.INTEGER_ADDRESSLISTACTIVITY_LISTSTYLES);
				this.startActivityForResult(intent, Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST0);
				break;
			case R.id.rl_order_invoiceinfo:
				if(!sv_needinvoiceinfo.isOn())
					return ;
				intent.setClass(this, VoucherChoosedActivity.class);
				intent.putExtra(Constants.STRINGEXTRA, Constants.INTEGER_ADDRESSLISTACTIVITY_LISTSTYLES);
				this.startActivityForResult(intent, Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST1);
				break;
			case R.id.rl_order_detail:
				if(orderBeforePosted == null || orderBeforePosted.getSku_list() == null)
					return;
				SkuBean[] sbarr = orderBeforePosted.getSku_list();
				ArrayList<SkuBean> sblist = new ArrayList<SkuBean>();
				for(SkuBean item : sbarr)
					sblist.add(item);
				intent.setClass(this, GoodsPurchasedListActivity.class);
				intent.putExtra(Constants.OBJECTEXTRA, sblist);
				this.startActivity(intent);
				break;
			case R.id.rl_order_coupon:
				if(orderBeforePosted == null)
					return;
				intent.setClass(this, VoucherListActivity.class);
				VoucherList vl = new VoucherList();
				vl.setVoucher_list(orderBeforePosted.getVoucher_list());
				intent.putExtra(Constants.OBJECTEXTRA, vl);
				this.startActivityForResult(intent, Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST2);
				break;
			case R.id.btn_orderposted:
				postOrder(this.orderBeforePosted);
				break;
			
		}
		
	}
	
	private void postOrder(OrderBeforePosted orderBeforePosted)
	{
		if(orderBeforePosted == null)
			return;
		if(sv_needinvoiceinfo != null)
			orderBeforePosted.setIs_invoice(sv_needinvoiceinfo.isOn() ? 1 : 0);
		else
			orderBeforePosted.setIs_invoice(0);
		
		if(orderBeforePosted.getIs_invoice() == 1)
			orderBeforePosted.setInvoice_title(currentivHeader == null ? "" : currentivHeader); 
		else 
			orderBeforePosted.setInvoice_title("");
		
		if(et_orderremark != null)
			orderBeforePosted.setRemark(et_orderremark.getText().toString().trim());
		orderBeforePosted.setSku_ids(sku_ids);
		if(orderBeforePosted.getPayment_type_list() != null && orderBeforePosted.getPayment_type_list().length > 0)
		{
			orderBeforePosted.setPayment_type(orderBeforePosted.getPayment_type_list()[0].getType());
		}
			
		OrderHttpOperation.submitOrder(orderBeforePosted, Constants.CALLBACK_FLAG_SUBMITANORDER, this, this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(data != null && requestCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST0
				&& resultCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE0)
		{
			Address address = (Address)data.getSerializableExtra(Constants.OBJECTEXTRA);
			this.setAddress(address);
			OrderHttpOperation.getFareofOrder(sku_ids, address.getAddress_id(), Constants.CALLBACK_FLAG_OBTAINFAREOFCHOSENADDRESS, this, this);
			orderBeforePosted.setAddress(address);
		}
		if(data != null && requestCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST1
				&& resultCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE1)
		{
			String voucherc = data.getStringExtra(Constants.STRINGEXTRA);
			if(voucherc == null)
				voucherc = "";
			setVoucherInfo(voucherc);
				
		}
		if(data != null && requestCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_REQUEST2
				&& resultCode == Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE2)
		{
			Voucher voucher = (Voucher)data.getSerializableExtra(Constants.OBJECTEXTRA);
			setCouponInfo(orderBeforePosted,voucher);
		}
	}
	
	private void setCouponInfo(OrderBeforePosted orderBeforePosted , Voucher voucher)
	{
		if(voucher == null)
		{
			orderBeforePosted.setExtraPrice(0.0);
			orderBeforePosted.setVoucher(null);
			return;
		}
			
		if(voucher.getNeed_money() > orderBeforePosted.getCart_total())
		{
			showMessageToast("不能使用");
			return ;
		}
		Double extra = voucher.getPar_value();
		orderBeforePosted.setExtraPrice(-extra);
		orderBeforePosted.setVoucher(voucher);
		setPageDetail(orderBeforePosted);
	}
	
	private void setAddress(Address address)
	{
		if(this.tv_recevierName != null && this.tv_recevierPhone != null
				&& this.tv_recevierAddress != null)
		{
			this.tv_recevierName.setText(address.getName());
			this.tv_recevierPhone.setText(address.getMobile());
			this.tv_recevierAddress.setText(getAddressString(address));
		}
	}
	
	private String getAddressString(Address address)
	{
		StringBuilder sb = new StringBuilder();
		if(address.getProvince()!=null)
			sb.append(address.getProvince().name);
		if(address.getCity()!=null)
			sb.append(address.getCity().name);
		if(address.getCounty()!=null)
			sb.append(address.getCounty().name);
		sb.append(address.getAddress());
		return sb.toString();
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		this.closeProcessDialog();
		if(requestId == Constants.CALLBACK_FLAG_GET_INFOBEFOREPOSTORDER)
		{
			Log.d(httpResult.getResult());
		}
		else if(requestId == Constants.CALLBACK_FLAG_CARTCHECKOUTBEFOREPURCHASED)
		{
			if(httpResult.isSuccess())
			{
				
				this.orderBeforePosted = JsonToBean.json2OrderBeforePosted(httpResult.getResult());
				setPageDetail(orderBeforePosted);
			}
		}
		else if(requestId == Constants.CALLBACK_FLAG_OBTAINFAREOFCHOSENADDRESS)
		{
			if(httpResult.isSuccess())
			{
				currentFreight = Double.valueOf(httpResult.getResult());
				if(orderBeforePosted != null)
				{
					orderBeforePosted.setFare(currentFreight);
					setPageDetail(orderBeforePosted);
				}
			}
		}
		else if(requestId == Constants.CALLBACK_FLAG_SUBMITANORDER)
		{
			if(httpResult.isSuccess())
			{
				OrderId orderid = JsonToBean.json2Orderid(httpResult.getResult());
				long orderIdlong = orderid.getOrder_id();
				Intent intent = new Intent();
				intent.setClass(this,OrderOverviewActivity.class);
				intent.putExtra(Constants.LONGEXTRA, orderIdlong);
				
				this.startActivity(intent);
			}
			else
			{
				showMessageToast(httpResult.getResult());
			}
		}
	}
	
	private void setPageDetail(OrderBeforePosted orderBeforePosted){
		if(orderBeforePosted == null)
			return ;
		Address address = orderBeforePosted.getAddress();
		if(address != null )
		{
			tv_recevierName.setText(address.getName());
			tv_recevierPhone.setText(address.getMobile());
			StringBuilder sb = new StringBuilder();
			sb.append(address.getProvince().name);
			sb.append(address.getCity().name);
			sb.append(address.getCounty().name);
			sb.append(address.getAddress());
			tv_recevierAddress.setText(sb.toString());
		}
		
		PaymentType[] paymentType = orderBeforePosted.getPayment_type_list();
		if(paymentType != null && paymentType.length > 0)
		{
			tv_purchasingway.setText(paymentType[0].getName());
		}
		String[] invarr = orderBeforePosted.getInvoice_title_type_list();
		if(invarr != null && invarr.length > 0 && currentivHeader == null)
		{
			tv_invoicetypeinfo.setText(invarr[0]);
			tv_invoiceheadinfo.setText(invarr[0]);
		}
		else if(currentivHeader != null)
		{
			setVoucherInfo(currentivHeader);
		}
	
		tv_ordergoodsvalue.setText("￥"+UnitTools.formatDoubleTagTail2(orderBeforePosted.getCart_total()));
		tv_orderfreight.setText("￥"+UnitTools.formatDoubleTagTail2(orderBeforePosted.getFare()));
		tv_tmpforalgininvoiceinfo.setText(getResources().getString(R.string.invoiceinfo, orderBeforePosted.getTax_rate()));
		/**
		 * 设置总价
		 */
		Double tax = (orderBeforePosted.getTax_rate()/100.0)*orderBeforePosted.getCart_total();
		String taxs = UnitTools.formatDoubleTagTail2(tax);
		if(!sv_needinvoiceinfo.isOn() || tax == 0d)
		{
			taxs = "0.00";
			tax = 0.00d;
		}
		tv_ordergoodstallage.setText("￥"+taxs);
		
		String totals= UnitTools.formatDoubleTagTail2((orderBeforePosted.getOrder_total()+tax+orderBeforePosted.getFare()+(orderBeforePosted.getExtraPrice() == null ? 0.0 : orderBeforePosted.getExtraPrice())));
		tv_tempforfeeshouldpay.setText("￥"+totals);
		if(orderBeforePosted.getVoucher() != null)
		{
			tv_order_couponinfo.setText(orderBeforePosted.getVoucher().getName());
		}
	}
	
	
	private void setVoucherInfo(String name)
	{
		currentivHeader = name;
		if(!name.equals(getResources().getString(R.string.forperson)))
		{
			currentivType = getResources().getString(R.string.forcompany);
			tv_invoicetypeinfo.setText(currentivType);
			tv_invoiceheadinfo.setText(currentivHeader);
		}
		else
		{
			currentivType = getResources().getString(R.string.forperson);
			tv_invoicetypeinfo.setText(currentivType);
			tv_invoiceheadinfo.setText(currentivType);
		}
		
	}
}
