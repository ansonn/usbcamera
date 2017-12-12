package com.market;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.CategoryListAdapter;
import com.market.bean.Category;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class CategoryFragment extends Fragment implements OnHttpCallBack,
		OnItemClickListener {

	private Button btnTitle;
	private ListView lvCategory;
	private CategoryListAdapter categoryAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.category_fragment, container);

		// …Ë÷√±ÍÃ‚
		btnTitle = (Button) view.findViewById(R.id.common_header_btn_center);
		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.category_title);

		categoryAdapter = new CategoryListAdapter(getActivity());
		lvCategory = (ListView) view.findViewById(R.id.lv_category);
		lvCategory.setAdapter(categoryAdapter);
		HttpComm.getCategoryList(this,
				Constants.CALLBACK_FLAG_GET_CATEGORY_LIST);

		lvCategory.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (requestId == Constants.CALLBACK_FLAG_GET_CATEGORY_LIST) {
			if (httpResult.isSuccess()) {
				List<Category> list = JsonToBean.json2CategoryList(httpResult
						.getResult());
				categoryAdapter.updateDate(list);
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Category category = (Category) parent.getAdapter().getItem(position);
		Intent intent = new Intent();
		intent.setClass(getActivity(), SubCategoryActivity.class);
		intent.putExtra(Constants.EXTRA_CATEGORY, category);
		startActivity(intent);
	}
}
