package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.ForgotIndentifierInfo;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class VFChooseActivity extends BaseActivity implements OnClickListener,
		OnHttpCallBack {
	private String indentify;
	private String mobileNumber;
	private String emailAddress;
	private TextView tv_identifyMobile;
	private TextView tv_identifyEmail;
	private View ll_identifyEmail;
	private View ll_identifyMobile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_forgot_vfchoose);
		setTitle();
		initView();
		getEmailnMobile(getExtra());

	}

	private void setTitle() {
		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.forgot_vf_choose_title));

	}

	private void initView() {
		tv_identifyMobile = (TextView) this
				.findViewById(R.id.tv_identifyMobile);
		tv_identifyEmail = (TextView) this.findViewById(R.id.tv_identifyEmail);
		ll_identifyEmail = this.findViewById(R.id.ll_identifyEmail);
		ll_identifyMobile = this.findViewById(R.id.ll_identifyMobile);


		ll_identifyEmail.setOnClickListener(this);
		ll_identifyMobile.setOnClickListener(this);

		ll_identifyEmail.setClickable(false);
		ll_identifyMobile.setClickable(false);
	}

	private String getExtra() {
		Intent intent = this.getIntent();
		indentify = intent.getStringExtra(Constants.MAP_FORGOTINDENTIFY);
		return indentify;
	}

	private void getEmailnMobile(String id) {
		if (id == null)
			return;
		HttpComm.getForgotInfo(id, this, Constants.CALLBACK_FLAG_FORGOTPWINFO);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_identifyEmail:
		
			break;
		case R.id.ll_identifyMobile:
			Intent getVFCodeIntent = new Intent();
			getVFCodeIntent.setClass(this, VFMobileActivity.class);
			getVFCodeIntent.putExtra(Constants.MAP_FORGOTINDENTIFY, indentify);
			this.startActivity(getVFCodeIntent);
			break;
		default:

		}
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (requestId == Constants.CALLBACK_FLAG_FORGOTPWINFO) {
			if (httpResult == null)
				return;
			if (httpResult.isSuccess()) {
				if (httpResult.getResult() != null) {
					 ForgotIndentifierInfo fiifon = JsonToBean.json2ForgotIndentifierInfo(httpResult.getResult());
					 if (fiifon.getMobile() != null &&
					 fiifon.getMobile().length() > 1) {
					 ll_identifyMobile.setClickable(true);
					 this.mobileNumber = fiifon.getMobile();
					 tv_identifyMobile.setText(fiifon.getMobile());
					
					 }
					 if (fiifon.getMobile() != null &&
					 fiifon.getEmail().length() > 1) {
					 ll_identifyEmail.setClickable(true);
					 this.emailAddress = fiifon.getEmail();
					 tv_identifyEmail.setText(fiifon.getEmail());
					 }
				}
			} else {
				showMessageToast(httpResult.getResult().toString());
			}
		} else if (requestId == Constants.CALLBACK_FLAG_REGISTVERIFYCODE) {

		}
	}

}
