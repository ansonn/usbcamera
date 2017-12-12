package com.market.junit;

import java.util.List;

import com.market.bean.Category;
import com.market.bean.GoodsList;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

import android.test.AndroidTestCase;

public class HttpTest extends AndroidTestCase {

	public void testHttpComm() {
		HttpComm.getGoodListByCategoryId(34, new OnHttpCallBack() {

			@Override
			public void onHttpCallBack(int requestId, HttpResult httpResult) {
				
				String result=httpResult
						.getResult();
				GoodsList list = JsonToBean.json2GoodList(httpResult
						.getResult());
				
				System.out.println(list);

			}
		}, 20, 0, 0, 0, null, null, null, 0);
	}

	public void testGetGoodList() {
		HttpComm.getGoodList(0, 5, Constants.SORT_SALES_VOLUME,
				new OnHttpCallBack() {

					@Override
					public void onHttpCallBack(int requestId,
							HttpResult httpResult) {
						JsonToBean.json2GoodList(httpResult.getResult());

					}
				}, 0);
	}
}
