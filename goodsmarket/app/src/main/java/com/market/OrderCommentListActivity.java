package com.market;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.haoyikuai.shop.android.R;
import com.market.adapter.OrderCommenListAdapter;
import com.market.bean.OrderDetail;
import com.market.bean.OrderGoods;
import com.market.bean.OrderId;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class OrderCommentListActivity extends BaseActivity implements OnHttpCallBack{
	private long order_id;
	private ListView lv_ordercommentlist;
	private OrderCommenListAdapter orderCommenListAdapter;
	private OrderDetail orderDetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_ordercommentlist);
		initData();
		initView();
		initTitle();
		OrderHttpOperation.getOrder(Constants.CALLBACK_FLAG_GET_ORDER, order_id, this, this);
	
	}
	
	private void initData()
	{
		Intent intent  = this.getIntent();
		this.order_id = intent.getLongExtra(Constants.LONGEXTRA, -1);
		orderCommenListAdapter = new OrderCommenListAdapter(this,order_id);
	}
	private void initView()
	{
		lv_ordercommentlist = (ListView)this.findViewById(R.id.lv_ordercommentlist);
		lv_ordercommentlist.setAdapter(orderCommenListAdapter);
	}
	
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
	}
	
	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if(requestId == Constants.CALLBACK_FLAG_GET_ORDER)
		{
			if(httpResult.isSuccess())
			{
				orderDetail = JsonToBean.json2OrderDetail(httpResult.getResult());
				orderCommenListAdapter.setOrderGoods(orderDetail.getOrder_goods());
//				orderCommenListAdapter.notifyDataSetChanged();
				OrderHttpOperation.getCommentListByOrderId(order_id, Constants.CALLBACK_FLAG_GETORDERCOMMENTLIST, this, this);
			}
		}
		if(requestId == Constants.CALLBACK_FLAG_GETORDERCOMMENTLIST)
		{
			if(httpResult.isSuccess())
			{
				try {
					JSONArray jsonArray = new JSONArray(httpResult.getResult());
					for(int i = 0 ; i < jsonArray.length() ; i++)
					{
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if(jsonObject.has("sku_id"))
						{
							long sku_id = jsonObject.getLong("sku_id");
							orderCommenListAdapter.deleteOrderGoods(sku_id);
							orderCommenListAdapter.notifyDataSetChanged();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				this.ShowToastImageCorrect(httpResult.getResult());
			}
		}
	}
}
