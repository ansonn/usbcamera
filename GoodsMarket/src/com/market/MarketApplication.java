package com.market;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.ErrorCode;
import io.rong.imlib.RongIMClient;
import android.app.Application;
import android.util.Log;

import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class MarketApplication extends Application implements OnHttpCallBack {

	@Override
	public void onCreate() {

		super.onCreate();
		RongIM.init(this);

		HttpComm.getRongYunToken(this, Constants.CALLBACK_RONGYUN_TOKEN);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (requestId == Constants.CALLBACK_RONGYUN_TOKEN) {
			//连接
			String token=httpResult.getResult();
			try {
				RongIM.connect(token, new RongIMClient.ConnectCallback() {

				    @Override
				    public void onSuccess(String s) {
				        // 此处处理连接成功。
				        Log.d("Connect:", "Login successfully.");
				    }

				    @Override
				    public void onError(ErrorCode errorCode) {
				        // 此处处理连接错误。
				        Log.d("Connect:", "Login failed.");
				    }
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
