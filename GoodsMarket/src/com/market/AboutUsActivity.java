package com.market;


import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.AuthCode;
import com.market.utils.Constants;

public class AboutUsActivity extends BaseActivity implements OnHttpCallBack {

	private TextView tvVersion;
	private TextView tvCopyRight;
	private ImageView ivAbout;
	private BitmapUtils bitmapUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_about);
		setDeftualtHeaderLeftButton();
		setActivityTitle(getString(R.string.about));
		;

		tvVersion = (TextView) findViewById(R.id.tv_version);
		tvCopyRight = (TextView) findViewById(R.id.tv_copy_right);
		ivAbout = (ImageView) findViewById(R.id.iv_about);
		bitmapUtils = new BitmapUtils(this);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.loading_big);
		bitmapUtils.configDefaultLoadingImage(R.drawable.loading_big);

		HttpComm.getAbountUsInfo(AuthCode.APP_ID, this,
				Constants.CALLBACK_FLAG_ABOUT_US, this);
		
		showProcessDialog(getString(R.string.loading));
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {

		try {

			String result = httpResult.getResult();
			System.out.println("result=" + result);
			JSONObject jsonObject = new JSONObject(result);
			String iconUrl = jsonObject.getString("icon");
			if (iconUrl != null) {
				bitmapUtils.display(ivAbout, iconUrl);
			}
			tvCopyRight.setText(jsonObject.getString("copyright"));
			
			PackageManager pm = getPackageManager();
			PackageInfo pi;
			try {
				pi = pm.getPackageInfo(getPackageName(), 0);
				String versionName = pi.versionName;
				tvVersion.setText(versionName);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		closeProcessDialog();
	}
}
