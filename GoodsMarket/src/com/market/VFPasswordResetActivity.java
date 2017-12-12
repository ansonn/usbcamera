package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.haoyikuai.shop.android.R;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;

public class VFPasswordResetActivity extends BaseActivity implements OnClickListener , OnHttpCallBack{
	private String indentify;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_forgot_passwordreset);
		setTitle();
		getExtra();
	}

	private void setTitle() {
		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.forgot_vf_pwreset_title));
		super.setStringHeaderRightButton(this.getResources().getString(R.string.forgot_operation), this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case ResourceLocation.BUTTON_HEADER_RIGHT:
				break;
		}
	}
	
	private String getExtra() {
		Intent intent = this.getIntent();
		indentify = intent.getStringExtra(Constants.MAP_FORGOTINDENTIFY);
		return indentify;
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		
	}
	
	
}
