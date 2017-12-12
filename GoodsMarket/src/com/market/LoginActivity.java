package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.haoyikuai.shop.android.R;
import com.market.dbmanage.SharePrefrencesUtils;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.Util;

public class LoginActivity extends BaseActivity implements OnHttpCallBack,
		OnClickListener {

	private View tencentQQ;
	private View sina;
	private View btn_userregist;

	private View btn_login;
	private View btn_findPass;
	private EditText edt_loginName;
	private EditText edt_loginPassword;
	private SharePrefrencesUtils spPrefrencesUtils;
	private String loginName;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);

		spPrefrencesUtils = new SharePrefrencesUtils(this);
		// ////////////////////判断cookie是否过期////////////////////////////
		String cookie = spPrefrencesUtils.getLoginCookie();

		if (!TextUtils.isEmpty(cookie) && !Util.isCookieExpires(cookie)) {
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			this.startActivity(intent);

			finish();
		}
		// /////////////////////////////////////////////////

		initView();
		setTitle();

	}

	public void initView() {

		tencentQQ = this.findViewById(R.id.ll_tencentQQLogin);
		sina = this.findViewById(R.id.ll_sinaLogin);
		btn_login = this.findViewById(R.id.btn_login);
		edt_loginName = (EditText) this.findViewById(R.id.edt_loginName);
		edt_loginPassword = (EditText) this
				.findViewById(R.id.edt_loginPassword);
		btn_userregist = this.findViewById(R.id.btn_userregist);
		btn_findPass = this.findViewById(R.id.btn_findPass);

		btn_findPass.setOnClickListener(this);
		btn_userregist.setOnClickListener(this);
		btn_login.setOnClickListener(this);

		edt_loginName.setText(spPrefrencesUtils.getUserName());
		edt_loginPassword.setText(spPrefrencesUtils.getPassWord());

//		hideHeaderLeftButton();
//		hideHeaderRightButton();
	}

	public void setTitle() {
//		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.login_title));
//		super.setImageHeaderRightButton(R.drawable.search_nor, this);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (requestId == Constants.CALLBACK_FLAG_LOGIN) {
			// 登陆结果返回
			// LogUtils.d(httpResult.toString());
			if (httpResult.isSuccess()) {
				// 登陆成功，缓存session
				spPrefrencesUtils.setPassword(password);
				spPrefrencesUtils.setUserName(loginName);

				String result = httpResult.getResult();
				// User user = JsonToBean.json2User(result);
				spPrefrencesUtils.saveLoginedUserInfo(result);
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				this.startActivity(intent);

			} else {
				Toast.makeText(LoginActivity.this,
						httpResult.getResult().toString(), Toast.LENGTH_SHORT)
						.show();

			}
			this.closeProcessDialog();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login: {
			password = edt_loginPassword.getText().toString().trim();
			loginName = edt_loginName.getText().toString().trim();

			if (TextUtils.isEmpty(password) || TextUtils.isEmpty(loginName)) {
				Toast.makeText(LoginActivity.this,
						R.string.username_and_password_no_empty,
						Toast.LENGTH_SHORT).show();
				return;
			}
			this.showProcessDialog();
			HttpComm.login(loginName, password, LoginActivity.this,
					Constants.CALLBACK_FLAG_LOGIN, this);
		}
			break;
		case R.id.btn_userregist: {
			// 跳转到忘记注册页面
			Intent intentReAct = new Intent();
			intentReAct.setClass(this, RegistActivity.class);
			this.startActivity(intentReAct);
		}
			break;
		case R.id.btn_findPass: {
			// 跳转到忘记密码页面
			Intent intentFogotpass = new Intent();
			intentFogotpass.setClass(this, FogotPasswordActivity.class);
			this.startActivity(intentFogotpass);
		}
			break;
		}
	}
}
