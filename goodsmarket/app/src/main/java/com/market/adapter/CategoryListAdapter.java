package com.market.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.market.bean.Category;

public class CategoryListAdapter extends BaseAdapter {

	private List<Category> list;
	private BitmapUtils bitmapUtils;
	private Context mContext;

	public CategoryListAdapter(Context context) {
		mContext = context;
		bitmapUtils = new BitmapUtils(context);
	}

	public void updateDate(List<Category> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list == null) {
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		Category category = list.get(position);
		if (convertView != null) {
			view = convertView;

		} else {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_category_list, null);
		}

		ImageView imageView = (ImageView) view.findViewById(R.id.iv_thumb);
		ImageView nextIcon = (ImageView) view.findViewById(R.id.iv_next_icon);
		TextView tvCategoryName = (TextView) view
				.findViewById(R.id.tv_category_name);
		TextView tvSubCategoryName = (TextView) view
				.findViewById(R.id.tv_sub_category_name);
		if(category.getChild_id_array().length<1){
			nextIcon.setVisibility(View.GONE);
		}else{
			nextIcon.setVisibility(View.VISIBLE);
		}
		
		tvCategoryName.setText(category.getCategory_name());
		String subType = "";
		if (category.getSubCategoryList() != null) {
			for (Category subCate : category.getSubCategoryList()) {
				if (!"".equals(subType)) {
					subType += "/";
				}
				subType += subCate.getCategory_name();
			}
		}
		tvSubCategoryName.setText(subType);

		bitmapUtils.display(imageView, category.getImage(),
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						((ImageView) arg0).setImageBitmap(arg2);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						((ImageView) arg0).setImageResource(R.drawable.loading);

					}
				});

		return view;
	}
}
