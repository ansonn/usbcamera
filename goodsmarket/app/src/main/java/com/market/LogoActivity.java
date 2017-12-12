package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haoyikuai.shop.android.R;

/**
 * 
 * @tag 应用启动的第一页
 * @author 陈伟斌
 * @date 2015-1-14
 */
public class LogoActivity extends BaseActivity {

	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);

		mHandler = new Handler();
		mHandler.postDelayed(new DimissTask(), 3000);
	}

	class DimissTask implements Runnable {

		@Override
		public void run() {
			// 后续这里需判断用户session是否过期，没过期时不用再次登陆
			Intent loginIntent = new Intent();
			loginIntent.setClass(LogoActivity.this, LoginActivity.class);
			startActivity(loginIntent);

			finish();
		}
	}
}
