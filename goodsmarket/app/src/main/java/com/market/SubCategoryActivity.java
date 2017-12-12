package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.CategoryListAdapter;
import com.market.bean.Category;
import com.market.utils.Constants;

public class SubCategoryActivity extends BaseActivity implements
		OnItemClickListener {

	private Button btnTitle;
	private ListView lvCategory;
	private CategoryListAdapter categoryAdapter;
	private Category category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// 设置标题

		setContentView(R.layout.activity_subcategory);

		category = (Category) getIntent().getSerializableExtra(
				Constants.EXTRA_CATEGORY);

		lvCategory = (ListView) findViewById(R.id.lv_categoryList);

		btnTitle = (Button) findViewById(R.id.common_header_btn_center);
		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(category.getCategory_name());

		categoryAdapter = new CategoryListAdapter(this);

		lvCategory.setAdapter(categoryAdapter);

		setDeftualtHeaderLeftButton();

		categoryAdapter.updateDate(category.getSubCategoryList());
		lvCategory.setOnItemClickListener(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Category category = (Category) parent.getAdapter().getItem(position);
		if(category.getChild_id_array().length>0){
			Intent intent = new Intent();
			intent.setClass(this, SubCategoryActivity.class);
			intent.putExtra(Constants.EXTRA_CATEGORY, category);
			startActivity(intent);
		}else{
			//跳转到商品列表
			Intent intent = new Intent();
			intent.setClass(this, GoodListActiviy.class);
			intent.putExtra(Constants.EXTRA_CATEGORY, category);
			startActivity(intent);
		}

	}
}
