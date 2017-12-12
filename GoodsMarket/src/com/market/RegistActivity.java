package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haoyikuai.shop.android.R;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;
import com.market.utils.TimeCount;
import com.market.utils.Util;

public class RegistActivity extends BaseActivity implements OnHttpCallBack,
		OnClickListener {
	private View btn_getverify;
	private EditText edt_signname;
	private EditText edt_signpassword;
	private EditText edt_signemobile;
	private EditText edt_signesecurity;
	private EditText edt_signeemail;
	private TimeCount timecount;
	private String btn_before;
	private String btn_onclick;
	private String user_name;
	private String user_password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_regist);
		setTitle();
		initView();
	}

	private void initView() {
		btn_getverify = this.findViewById(R.id.btn_getverify);
		edt_signname = (EditText) this.findViewById(R.id.edt_signname);
		edt_signpassword = (EditText) this.findViewById(R.id.edt_signpassword);
		edt_signemobile = (EditText) this.findViewById(R.id.edt_signemobile);
		edt_signesecurity = (EditText) this.findViewById(R.id.edt_signesecurity);
		edt_signeemail = (EditText) this.findViewById(R.id.edt_signeemail);
		
		btn_before = getResources().getString(R.string.regist_getsecurity);
		btn_onclick = btn_before;
		btn_getverify.setOnClickListener(this);
	}

	public void setTitle() {
		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.regist_title));
		super.setStringHeaderRightButton(
				this.getString(R.string.regist_operation), this);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (requestId == Constants.CALLBACK_FLAG_REGIST) {
			if (httpResult.isSuccess()) {
				ShowToastImageCorrect(httpResult.getResult());
				Intent intent = new Intent();
				intent.putExtra(Constants.NAMEEXTRA, user_name);
				intent.putExtra(Constants.PASSWORDEXTRA, user_password);
				this.setResult(Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE0, intent);
				finish();
			} else {
				showMessageToast(httpResult.getResult().toString());
			}
		} else if (requestId == Constants.CALLBACK_FLAG_REGISTVERIFYCODE) {

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_getverify:
			getVerifyCode();
			break;
		case ResourceLocation.BUTTON_HEADER_RIGHT:
			regist();
			break;
		}
	}

	private void getVerifyCode() {
		if (edt_signemobile == null)
			return;
		String strsignemobile = edt_signemobile.getText().toString();
		if(Util.CheckMobile(strsignemobile, 11) != Util.ACCESSIBLE)
		{
			return;
		}
		timecount = new TimeCount(60000, 1000, btn_before,
				btn_onclick, (Button) btn_getverify);
		
		HttpComm.getRegVerify(edt_signemobile.getText().toString(), this,
				Constants.CALLBACK_FLAG_REGISTVERIFYCODE);
		timecount.start();

	}

	private void regist()
	{
		if(edt_signname == null || edt_signpassword == null
				||edt_signemobile == null
				||edt_signesecurity == null)
			return;
		String name = edt_signname.getText().toString().trim();
		String password = edt_signpassword.getText().toString().trim();
		String verifyCode = edt_signesecurity.getText().toString().trim();
		String mobile = edt_signemobile.getText().toString().trim();
		String email = edt_signeemail.getText().toString().trim();
		if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password))
			super.showMessageToast(this.getResources().getString(R.string.regist_info_not_completed));
//		else if(TextUtils.isEmpty(mobile) && TextUtils.isEmpty(email))
//			super.showMessageToast(this.getResources().getString(R.string.regist_bothemailnmobileempty_err));
//		else if(!TextUtils.isEmpty(mobile) && TextUtils.isEmpty(verifyCode))
//			super.showMessageToast(this.getResources().getString(R.string.regist_verifyuncompleted_err));
		else if(!Util.isEmail(email))
			super.showMessageToast(this.getResources().getString(R.string.regist_email_format_error));
		else if(Util.CheckUserName(name, 6) != Util.ACCESSIBLE)
			super.showMessageToast(this.getResources().getString(R.string.regist_username_format_error));
		else if(password.length() < 6)
			super.showMessageToast(this.getResources().getString(R.string.regist_password_length_lessthan6));
		else
			HttpComm.register(name, password, mobile, verifyCode,email ,this, Constants.CALLBACK_FLAG_REGIST);
	
	}
	
	
}
