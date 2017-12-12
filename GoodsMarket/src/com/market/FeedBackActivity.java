package com.market;

import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.AuthCode;
import com.market.utils.Constants;

public class FeedBackActivity extends BaseActivity implements OnHttpCallBack {

	private EditText etFeedBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_feedback);
		setDeftualtHeaderLeftButton();
		setActivityTitle(getString(R.string.feedback_title));

		etFeedBack = (EditText) findViewById(R.id.et_feedback);
		setStringHeaderRightButton(getString(R.string.submit),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						String content = etFeedBack.getText().toString();
						if (TextUtils.isEmpty(content)) {
							showMessageToast(getString(R.string.please_input_feedback));
							return;
						}
						showProcessDialog();
						HttpComm.submitFeedback(content, FeedBackActivity.this,
								Constants.CALLBACK_FLAG_FEEDBACK,
								FeedBackActivity.this);

					}
				});

		// showProcessDialog(getString(R.string.loading));
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (Constants.CALLBACK_FLAG_FEEDBACK == requestId) {
			if (httpResult.isSuccess()) {
				showDialog(R.string.submit_success);
			} else {
				showMessageToast(httpResult.getResult());
			}
			closeProcessDialog();
		}
	}
}
