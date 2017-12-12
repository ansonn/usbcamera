package com.market;

import java.net.URLDecoder;

import android.os.Bundle;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

import com.haoyikuai.shop.android.R;
import com.market.bean.GoodsInfo;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class GoodsDetailWebViewActivity extends BaseActivity implements
		OnHttpCallBack {

	private WebView webView;
	private GoodsInfo goodsInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		setContentView(R.layout.activity_goods_detail_webview);

		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setLoadWithOverviewMode(true);
		goodsInfo = (GoodsInfo) getIntent().getSerializableExtra(
				Constants.EXTRA_GOODS);

		String url=Constants.SERVER_ROOT_URL+"/"+goodsInfo.getUrl();
		webView.loadUrl(url);
//		HttpComm.getGoodsInfo(goodsInfo.getId() + "", this,
//				Constants.CALLBACK_FLAG_GET_GOODSINOF);
		
		setDeftualtHeaderLeftButton();
		setActivityTitle(goodsInfo.getTitle());
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {

		if (requestId == Constants.CALLBACK_FLAG_GET_GOODSINOF) {
			GoodsInfo detailGoodInfo = JsonToBean.json2Good(httpResult
					.getResult());
			webView.loadData(URLDecoder.decode(detailGoodInfo.getContent()),
					"text/html", "gb2312");
//			webView.loadUrl(detailGoodInfo.getUrl());
		}

	}
}
