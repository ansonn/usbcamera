package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.RefundAdapter;
import com.market.bean.Page;
import com.market.bean.RefundBean;
import com.market.bean.RefundInfo;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.RefundHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.MyLog;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RefundListActivity extends BaseActivity implements OnHttpCallBack,OnItemClickListener{
	private int page_index = 0;
	private int list_row = 10;
	private Page nowPage;
	private PullToRefreshListView pfl_refundlist;
	private RefundAdapter refundAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_refundlist);
		initData();
		initView();
		initTitle();
		this.showProcessDialog();
		RefundHttpOperation.getRefundList(page_index, list_row, this, Constants.CALLBACK_FLAG_GETREFUNDLIST, this);
	}
	
	private void initData(){
		refundAdapter = new RefundAdapter(this);
	}
	
	private void initView()
	{
		this.pfl_refundlist = (PullToRefreshListView)this.findViewById(R.id.pfl_refundlist);
		ListView contentList = pfl_refundlist.getRefreshableView();
		contentList.setOnItemClickListener(this);
		contentList.setAdapter(refundAdapter);
		
		pfl_refundlist.setScrollLoadEnabled(true);
		pfl_refundlist.setPullLoadEnabled(false);
		pfl_refundlist.setPullRefreshEnabled(false);
		
		this.pfl_refundlist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				RefundHttpOperation.getRefundList(getPageIndex(), list_row, RefundListActivity.this, Constants.CALLBACK_FLAG_GETREFUNDLIST, RefundListActivity.this);
				showProcessDialog();
			}
		});
	}
	
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(getResources().getString(R.string.refund_refundsearch));
	}

	private int getPageIndex()
	{
		return ++page_index;
	}
	
	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if(Constants.CALLBACK_FLAG_GETREFUNDLIST == requestId)
		{
			if(httpResult.isSuccess())
			{
				RefundInfo refundInfo = JsonToBean.json2RefundInfo(httpResult.getResult());
				refundAdapter.addToRefundBeanList(refundInfo.getRefund_list());
				refundAdapter.notifyDataSetChanged();
				nowPage = refundInfo.getPage();
				boolean hasmore = false;
				if(nowPage != null)
				{
					page_index =  nowPage.getNowPage();
					hasmore = page_index != nowPage.getTotalPages();
				}
				pfl_refundlist.onPullUpRefreshComplete();
				pfl_refundlist.setHasMoreData(hasmore);
				if(!hasmore)
					pfl_refundlist.setScrollLoadEnabled(false);
			}
			else
			{
				pfl_refundlist.onPullUpRefreshComplete();
				pfl_refundlist.setHasMoreData(false);
			}
		
		}
		this.closeProcessDialog();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int index, long position) {
		RefundBean refundBean = (RefundBean)parent.getAdapter().getItem(index);
		if(refundBean != null)
		{
			Intent intent = new Intent();
			intent.putExtra(Constants.LONGEXTRA, refundBean.getId());
			intent.setClass(this, RefundDetailActivity.class);
			this.startActivity(intent);
		}
	}
}
