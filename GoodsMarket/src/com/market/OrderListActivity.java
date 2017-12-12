package com.market;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.OrderListAdapter;
import com.market.bean.OrderList;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

public class OrderListActivity extends BaseActivity implements OnHttpCallBack,
		OnRefreshListener {
	private final int DEFAULT_ROW = 10;
	private int page_index = 1;
	private int list_row = DEFAULT_ROW;
	private PullToRefreshListView pullToRefreshListView;
	private ListView orderlist;
	private OrderList orderArraylist;
	private OrderListAdapter orderadapter;
	private boolean hasMoreData = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_order_list);
		initView();
		getOrderList(getFristPage(), getDefaultSize());
	}

	private void initView() {
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(this.getResources().getString(
				R.string.order_orderseek));
		pullToRefreshListView = (PullToRefreshListView) this
				.findViewById(R.id.lv_orderList);
		orderadapter = new OrderListAdapter(this,this);
		orderlist = pullToRefreshListView.getRefreshableView();
		orderlist.setAdapter(orderadapter);
		
		pullToRefreshListView.setScrollLoadEnabled(true);
		pullToRefreshListView.setPullRefreshEnabled(false);
		pullToRefreshListView.setOnRefreshListener(this);
		orderlist.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View viewcontent, int index,
					long position) {
				// TODO Auto-generated method stub
			
			}
			
		});
	}

	private void getOrderList(int page_index, int list_row) {
		this.showProcessDialog();
		OrderHttpOperation.getOrderList(Constants.CALLBACK_FLAG_GET_ORDERLIST,
				page_index, list_row, this, this);
	}

	private int getFristPage() {
		page_index = 1;
		return page_index;
	}

	private int getNextPage() {
		page_index++;
		return page_index;
	}

	private int getDefaultSize() {
		return DEFAULT_ROW;
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub

		if (Constants.CALLBACK_FLAG_GET_ORDERLIST == requestId) {
			if (httpResult.isSuccess()) {
				orderArraylist = JsonToBean.json2OrderList(httpResult
						.getResult());
				Log.d("Constants.CALLBACK_FLAG_GET_ORDERLIST");
				if(page_index == 1)
					orderadapter.setNewOrderList(orderArraylist.getOrder_list());
				else
					orderadapter.setOrderList(orderArraylist.getOrder_list());
				orderadapter.notifyDataSetChanged();
				
				Log.d("notifyDataSetChanged()");
				if (orderArraylist != null)
					hasMoreData = true;
				pullToRefreshListView.onPullDownRefreshComplete();
				pullToRefreshListView.onPullUpRefreshComplete();
				pullToRefreshListView.setHasMoreData(hasMoreData);// 判断是否有新数据
			} else {
				Log.d("httpResult.getResult()  "+httpResult.getResult());
				this.showMessageToast(httpResult.getResult());
			}
		}
		if (Constants.CALLBACK_FLAG_DELETEDORDER == requestId) 
		{
			if (httpResult.isSuccess()) {
				getOrderList(getFristPage(), getDefaultSize());
			}else
			{
				showMessageToast(httpResult.getResult());
			}
		}
		if (Constants.CALLBACK_FLAG_GETCOMMENTLIST == requestId) 
		{
			if (httpResult.isSuccess()) {
				
			}else
			{
				
			}
		}
		if (Constants.CALLBACK_FLAG_CANCELORDER == requestId) 
		{
			if (httpResult.isSuccess()) {
				
			}else
			{
				
			}
		}
		this.closeProcessDialog();
	}



	@Override
	public void onPullDownToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase refreshView) {
		// TODO Auto-generated method stub
		getOrderList(getNextPage(), getDefaultSize());
	}

}
