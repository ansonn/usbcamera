package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.MyFavoriteAdapter;
import com.market.bean.Favorite;
import com.market.bean.GoodsoverviewInfo;
import com.market.bean.Page;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.FavoriteHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyFocusActivity extends BaseActivity implements OnHttpCallBack,OnItemClickListener{
	private final int DEFAULTSIZE = 10;
	private int page_index;
	private int list_row = DEFAULTSIZE;
	private ListView myfavoritelv;
	private MyFavoriteAdapter myFavoriteAdapter;
	private PullToRefreshListView plv;
	private Page nowPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_myfocus);
		initData();
		initView();
		initTitle();
		FavoriteHttpOperation.getFavoriteList(getPageIndex(), list_row, Constants.CALLBACK_FLAG_GETFAVORITE, MyFocusActivity.this, MyFocusActivity.this);
		this.showProcessDialog();
	}

	private void initData() {
		page_index = 0;
		myFavoriteAdapter = new MyFavoriteAdapter(this);
	}

	private void initView() {
		plv = (PullToRefreshListView)this.findViewById(R.id.plv_myfocusgoods);
		myfavoritelv = plv.getRefreshableView();
		myfavoritelv.setAdapter(myFavoriteAdapter);
		plv.setScrollLoadEnabled(true);
		plv.setPullLoadEnabled(false);
		plv.setPullRefreshEnabled(false);
		plv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				FavoriteHttpOperation.getFavoriteList(getPageIndex(), list_row, Constants.CALLBACK_FLAG_GETFAVORITE, MyFocusActivity.this, MyFocusActivity.this);
				showProcessDialog();
			}
		});
	}

	private int getPageIndex()
	{
		
		return ++page_index;
	}
	
	private void initTitle() {
		this.setActivityTitle(getResources().getString(R.string.my_favoritetitle));
		this.setDeftualtHeaderLeftButton();
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(requestId == Constants.CALLBACK_FLAG_GETFAVORITE)
		{
			if(httpResult.isSuccess())
			{
				Favorite favorite = JsonToBean.json2Favorite(httpResult.getResult());
				nowPage = favorite.getPage();
				GoodsoverviewInfo[] goodslist = favorite.getGoodsList();
				myFavoriteAdapter.setGoodsList(goodslist);
				myFavoriteAdapter.notifyDataSetChanged();
				plv.onPullUpRefreshComplete();
				boolean hasmore = false;
				if(nowPage != null)
				{
					page_index = nowPage.getNowPage();
					hasmore = (page_index != nowPage.getTotalPages());
				}
				plv.onPullUpRefreshComplete();
				plv.setHasMoreData(hasmore);
				if(!hasmore)
					plv.setScrollLoadEnabled(false);
			}
			else
			{
				plv.onPullUpRefreshComplete();
				plv.setHasMoreData(false);
			}
		}
		
		this.closeProcessDialog();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
