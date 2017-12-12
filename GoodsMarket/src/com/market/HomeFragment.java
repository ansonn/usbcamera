package com.market;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.HomeGoodGridAdapter;
import com.market.bean.AdPicture;
import com.market.bean.Ads;
import com.market.bean.Category;
import com.market.bean.GoodsInfo;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.view.GalleryView;

public class HomeFragment extends BaseFragment implements OnHttpCallBack,
		OnItemClickListener {

	private static final int GOODS_COUNT_PER_CATEGORY = 6;// 第个分类列表显示的个数
	private GalleryView mGralleryView;
	private HomeGoodGridAdapter hotestGoodAdapter;
	private HomeGoodGridAdapter newestGoodAdapter;
	private GridView gvHotestGrid;
	private GridView gvNewestGrid;

	private Button btnTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.main_fragment, container);
		mGralleryView = (GalleryView) view.findViewById(R.id.gallery_view);
		// 设置标题
		btnTitle = (Button) view.findViewById(R.id.common_header_btn_center);
		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.main_page_title);

		gvHotestGrid = (GridView) view.findViewById(R.id.gv_hotestgoods);
		gvNewestGrid = (GridView) view.findViewById(R.id.gv_newsgoods);

		hotestGoodAdapter = new HomeGoodGridAdapter(getActivity());
		newestGoodAdapter = new HomeGoodGridAdapter(getActivity());

		gvHotestGrid.setAdapter(hotestGoodAdapter);
		gvNewestGrid.setAdapter(hotestGoodAdapter);

		gvHotestGrid.setOnItemClickListener(this);
		gvNewestGrid.setOnItemClickListener(this);

		
		showProcessDialog();
		HttpComm.getAdList(this, Constants.CALLBACK_FLAG_GET_HOME_PAGE_AD);

		HttpComm.getGoodList(0, GOODS_COUNT_PER_CATEGORY,
				Constants.SORT_SALES_VOLUME, this,
				Constants.CALLBACK_FLAG_GET_HOTEST_GOODS);

		HttpComm.getGoodList(0, GOODS_COUNT_PER_CATEGORY,
				Constants.SORT_POST_TIME, this,
				Constants.CALLBACK_FLAG_GET_NEWEST_GOODS);
		HttpComm.getRecommendCategoryList(this,
				Constants.CALLBACK_FLAG_GET_RECOMMAND_CATEGORY);

		// initGallery();

		return view;
	}

	// private void initGallery() {
	// List<Drawable> list = new ArrayList<Drawable>();
	// list.add(getResources().getDrawable(R.drawable.gallery1));
	// list.add(getResources().getDrawable(R.drawable.gallery2));
	// list.add(getResources().getDrawable(R.drawable.gallery3));
	// list.add(getResources().getDrawable(R.drawable.gallery4));
	// mGralleryView.setData(list);
	// }

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		
		if (requestId == Constants.CALLBACK_FLAG_GET_HOTEST_GOODS) {
			if (httpResult != null && httpResult.isSuccess()) {
				List<GoodsInfo> list = JsonToBean
						.json2SimpleGoodList(httpResult.getResult());
				hotestGoodAdapter.updateDate(list);
			}
		} else if (requestId == Constants.CALLBACK_FLAG_GET_NEWEST_GOODS) {
			List<GoodsInfo> list = JsonToBean.json2SimpleGoodList(httpResult
					.getResult());
			newestGoodAdapter.updateDate(list);
		} else if (requestId == Constants.CALLBACK_FLAG_GET_RECOMMAND_CATEGORY) {
			List<Category> list = JsonToBean
					.json2RecommandCategoryList(httpResult.getResult());
			// mGralleryView.setData(list);
			closeProcessDialog();
		} else if (requestId == Constants.CALLBACK_FLAG_GET_HOME_PAGE_AD) {
			if(httpResult.isSuccess())
			{
				String result = httpResult.getResult();
				Ads ads = JsonToBean.json2Ads(result);
	
				AdPicture[] adPicture = ads.getPics();
				mGralleryView.setData(adPicture);
				System.out.println();
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		GoodsInfo goodsInfo = (GoodsInfo) parent.getAdapter().getItem(position);
		Intent intent = new Intent();
		intent.putExtra(Constants.OBJECTEXTRA, goodsInfo);
		intent.setClass(getActivity(), GoodsOverviewActivity.class);
		startActivity(intent);

	}


	@Override
	protected void isShowtoUser(String latest) {
		// TODO Auto-generated method stub
		
	}

}
