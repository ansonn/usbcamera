package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haoyikuai.shop.android.R;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.TimeCount;

public class VFMobileActivity extends BaseActivity implements OnClickListener,
		OnHttpCallBack {
	private View btn_requestVF;
	private View btn_fogot_vfmobile;
	private EditText edt_vfmobilecode;
	private String indentify;
	private TimeCount timecount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_forgot_vfmobile);
		setTitle();
		initView();
		indentify = getExtra();
	}

	private String getExtra() {
		Intent intent = this.getIntent();
		indentify = intent.getStringExtra(Constants.MAP_FORGOTINDENTIFY);
		return indentify;
	}

	private void setTitle() {
		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.forgot_vf_title));
	}

	private void initView() {
		String btn_txtonloading = this.getResources().getString(
				R.string.forgot_vf_requestcodeagain);
		String btn_txtonfinish = this.getResources().getString(
				R.string.regist_getsecurity);
		btn_requestVF = this.findViewById(R.id.btn_requestVF);
		btn_fogot_vfmobile = this.findViewById(R.id.btn_fogot_vfmobile);
		edt_vfmobilecode = (EditText)this.findViewById(R.id.edt_vfmobilecode);
		
		btn_requestVF.setOnClickListener(this);
		btn_fogot_vfmobile.setOnClickListener(this);
		
		// 在按下时调用，60秒内不可用
		timecount = new TimeCount(60000, 1000, btn_txtonloading,
				btn_txtonfinish, (Button) btn_requestVF);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_requestVF:
			HttpComm.getForgotVFCode(indentify, "mobile", this, Constants.CALLBACK_FLAG_FORGOTREQUESTVFCODE);
			timecount.start();
			break;
		case R.id.btn_fogot_vfmobile:
			HttpComm.postForgotToken(indentify, edt_vfmobilecode.getText().toString(), this, Constants.CALLBACK_FLAG_FORGOTPOSRTVFCODE);
			break;
		}
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(requestId == Constants.CALLBACK_FLAG_FORGOTREQUESTVFCODE)
		{
			if(httpResult != null)
			{
					super.showMessageToast(httpResult.getResult().toString());
			}
		}
		else if(requestId == Constants.CALLBACK_FLAG_FORGOTPOSRTVFCODE)
		{
			if(httpResult != null)
			{
				if(httpResult.isSuccess())
				{
					gotoCoverPassword();
				}
				else
				{
					super.showMessageToast(httpResult.getResult().toString());
				}
			}
		}
	}
	
	private void gotoCoverPassword()
	{
		Intent gotoCoverIntent = new Intent();
		gotoCoverIntent.setClass(this, VFPasswordResetActivity.class);
		this.startActivity(gotoCoverIntent);
	}

}
