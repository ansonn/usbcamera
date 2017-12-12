package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.GoodsCommentAdapter;
import com.market.bean.CommentInfo;
import com.market.bean.Page;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class GoodsCommentActivity extends BaseActivity implements OnHttpCallBack{
	private long goodsid = -1;
	private GoodsCommentAdapter goodsCommentAdapter;
	private PullToRefreshListView plv_goodscommentlist;
	private ListView goodsCommentListView;
	private int page_index = 1;
	private int list_row = 10;
	private Page nowPage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_goodscommentlist);
		initData();
		initView();
		initTitle();
		this.showProcessDialog();
		HttpComm.getGoodsCommentList(page_index, list_row, goodsid, Constants.CALLBACK_FLAG_GETGOODSCOMMENT, this, this);
	}
	
	private void initData()
	{
		Intent intent = this.getIntent();
		goodsid = intent.getLongExtra(Constants.LONGEXTRA, -1);
		goodsCommentAdapter = new GoodsCommentAdapter(this);
	}
	
	private void initView()
	{
		plv_goodscommentlist = (PullToRefreshListView)this.findViewById(R.id.plv_goodscommentlist);
		plv_goodscommentlist.setScrollLoadEnabled(true);
		plv_goodscommentlist.setPullRefreshEnabled(false);
		plv_goodscommentlist.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				HttpComm.getGoodsCommentList(getPageIndex(), list_row, goodsid, Constants.CALLBACK_FLAG_GETGOODSCOMMENT, GoodsCommentActivity.this, GoodsCommentActivity.this);
			}
		});
		goodsCommentListView = plv_goodscommentlist.getRefreshableView();
		goodsCommentListView.setAdapter(goodsCommentAdapter);
	}
	
	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton();
		this.setActivityTitle(getResources().getString(R.string.goodsov_commenttitle));
	}

	private int getPageIndex()
	{
		return ++page_index;
	}
	
	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if(requestId == Constants.CALLBACK_FLAG_GETGOODSCOMMENT)
		{
			if(httpResult.isSuccess())
			{
				CommentInfo commentInfo = JsonToBean.json2CommentInfo(httpResult.getResult());
				setViews(commentInfo);
				nowPage = commentInfo.getPage();
				plv_goodscommentlist.onPullUpRefreshComplete();
				boolean hasmore = false;
				if(nowPage != null)
				{
					page_index = nowPage.getNowPage();
					hasmore = (page_index != nowPage.getTotalPages());
				}
				plv_goodscommentlist.onPullUpRefreshComplete();
				plv_goodscommentlist.setHasMoreData(hasmore);
				if(!hasmore)
					plv_goodscommentlist.setScrollLoadEnabled(false);
			}
		}
		this.closeProcessDialog();
	}
	
	private void setViews(CommentInfo commentInfo)
	{
		if(commentInfo != null && goodsCommentAdapter != null)
		{
			goodsCommentAdapter.addCommentList(commentInfo.getComment_list());
			goodsCommentAdapter.notifyDataSetChanged();
		}
	}
}
