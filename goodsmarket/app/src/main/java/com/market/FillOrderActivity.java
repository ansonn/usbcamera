package com.market;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.view.SwitchView;
import com.market.view.SwitchView.OnSwitchViewSeletedListener;


public class FillOrderActivity extends BaseActivity implements OnClickListener{
	private EditText et_orderremark;
	private Resources resources;
	private int paddingLeft;
	private Drawable dr ;
	private View rl_order_coupon;
	private View rl_order_detail;
	private View rl_order_invoiceinfo;
	private View rl_order_perchasingway;
	private View ll_order_receiver_info;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_fill_order);
		et_orderremark = (EditText)this.findViewById(R.id.et_orderremark);
		resources = FillOrderActivity.this.getResources();
		paddingLeft = (int)resources.getDimension(R.dimen.common_margin);
		dr = resources.getDrawable(R.drawable.bg_edittexxt_editable);
		
		et_orderremark.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View thiz) {
				// TODO Auto-generated method stub
				
				if(et_orderremark.isFocused())
				{	et_orderremark.setBackgroundDrawable(dr);
				et_orderremark.setPadding(paddingLeft, 0, 0, 0);
				}
			}
			
		});
		SwitchView sv = (SwitchView)this.findViewById(R.id.sv_needinvoiceinfo);
		sv.setIsOn(false);
		sv.setOnSeletedListener(new OnSwitchViewSeletedListener(){

			@Override
			public void onseleted(View view) {
				// TODO Auto-generated method stub
				showMessageToast("is selected");
			}

			@Override
			public void ondisseleted(View view) {
				// TODO Auto-generated method stub
				showMessageToast("is disselected");
			}
			
		});
	}

	private void initView()
	{
//		private View rl_order_coupon;
//		private View rl_order_detail;
//		private View rl_order_invoiceinfo;
//		private View rl_order_perchasingway;
//		private View ll_order_receiver_info;
		rl_order_coupon = this.findViewById(R.id.rl_order_coupon);
		rl_order_detail = this.findViewById(R.id.rl_order_detail);
		rl_order_invoiceinfo = this.findViewById(R.id.rl_order_invoiceinfo);
		rl_order_perchasingway = this.findViewById(R.id.rl_order_perchasingway);
		ll_order_receiver_info = this.findViewById(R.id.ll_order_receiver_info);
		
		rl_order_coupon.setOnClickListener(this);
		rl_order_detail.setOnClickListener(this);
		rl_order_invoiceinfo.setOnClickListener(this);
		rl_order_perchasingway.setOnClickListener(this);
		ll_order_receiver_info.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
