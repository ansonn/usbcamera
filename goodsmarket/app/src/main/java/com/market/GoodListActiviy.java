package com.market;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.market.adapter.GoodListAdapter;
import com.market.bean.Category;
import com.market.bean.GoodsInfo;
import com.market.bean.GoodsList;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

public class GoodListActiviy extends BaseActivity implements
		OnItemClickListener, OnClickListener, OnHttpCallBack,
		OnRefreshListener<ListView> {

	private Button btnTitle;
	private ListView lvGoods;
	private GoodListAdapter goodListAdapter;
	private Category category;
	private PullToRefreshListView pullToRefreshListView;
	private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
	private BitmapUtils bitmapUtils;
	private int pageIndex = 0;
	private List<GoodsInfo> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置标题

		setContentView(R.layout.activity_good_list);

		bitmapUtils = new BitmapUtils(this);
		goodListAdapter = new GoodListAdapter(this, bitmapUtils);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_goodList);
		pullToRefreshListView.setPullLoadEnabled(false);
		pullToRefreshListView.setScrollLoadEnabled(true);
		pullToRefreshListView.setOnRefreshListener(this);

		lvGoods = pullToRefreshListView.getRefreshableView();
		lvGoods.setAdapter(goodListAdapter);
		lvGoods.setOnItemClickListener(this);
		category = (Category) getIntent().getSerializableExtra(
				Constants.EXTRA_CATEGORY);
		btnTitle = (Button) findViewById(R.id.common_header_btn_center);

		btnTitle.setVisibility(View.VISIBLE);

		setDeftualtHeaderLeftButton();
		setStringHeaderRightButton(getString(R.string.select), this);
		// lvGoods.setOnItemClickListener(this);

		if (category != null) {
			btnTitle.setText(category.getCategory_name());
		}

		list = new ArrayList<GoodsInfo>();
		 // /////////////////////////////////////////////
		 if (category == null) {
		 category = new Category();
		 category.setCategory_id(143);
		 }
		 // ////////////////////////////////////////
		HttpComm.getGoodListByCategoryId(category.getCategory_id(), this, 20,
				0, -1, -1, null, null, null,
				Constants.CALLBACK_FLAG_GET_GOOD_LIST);
		showProcessDialog(getString(R.string.loading));

		lvGoods.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
				false, true));

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodsInfo goodsInfo = (GoodsInfo) parent.getAdapter().getItem(position);

		Intent intent = new Intent();
		intent.setClass(this, GoodsDetailWebViewActivity.class);
		intent.putExtra(Constants.EXTRA_GOODS, goodsInfo);
		startActivity(intent);

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == ResourceLocation.BUTTON_HEADER_RIGHT) {
			// 筛选
		}

	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (requestId == Constants.CALLBACK_FLAG_GET_GOOD_LIST) {
			if (httpResult != null && httpResult.isSuccess()) {
				GoodsList goodsList = JsonToBean.json2GoodList(httpResult
						.getResult());

				boolean hasMoreData = false;
				if (pageIndex == 0) {
					list.clear();
				}

				GoodsInfo[] goodInfoArray = goodsList.getGoods_list();
				for (GoodsInfo goodsInfo : goodInfoArray) {
					list.add(goodsInfo);
				}
				if (goodsList != null) {
					goodListAdapter.updateData(list);
					hasMoreData = true;
				} else {
					hasMoreData = false;
				}
				pullToRefreshListView.onPullDownRefreshComplete();
				pullToRefreshListView.onPullUpRefreshComplete();
				pullToRefreshListView.setHasMoreData(hasMoreData);// 判断是否有新数据
				closeProcessDialog();
				setLastUpdateTime();
			} else {
				Toast.makeText(this, httpResult.getResult(), 0).show();
			}
		}

	}

	private void setLastUpdateTime() {
		String text = formatDateTime(System.currentTimeMillis());
		pullToRefreshListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) {
		if (0 == time) {
			return "";
		}

		return mDateFormat.format(new Date(time));
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 刷新第一页
		HttpComm.getGoodListByCategoryId(category.getCategory_id(), this, 20,
				pageIndex++, -1, -1, null, null, null,
				Constants.CALLBACK_FLAG_GET_GOOD_LIST);

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// 需判断页码
		pageIndex = 0;
		HttpComm.getGoodListByCategoryId(category.getCategory_id(), this, 20,
				pageIndex, -1, -1, null, null, null,
				Constants.CALLBACK_FLAG_GET_GOOD_LIST);

	}
}
