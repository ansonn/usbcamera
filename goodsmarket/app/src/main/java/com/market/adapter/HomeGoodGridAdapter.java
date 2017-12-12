package com.market.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.market.bean.GoodsInfo;
import com.market.utils.ScreenUtil;
import com.market.utils.UnitTools;

public class HomeGoodGridAdapter extends BaseAdapter {

	private List<GoodsInfo> list;
	private BitmapUtils bitmapUtils;
	private Context mContext;

	public HomeGoodGridAdapter(Context context) {
		mContext = context;
		bitmapUtils = new BitmapUtils(context);
	}

	public void updateDate(List<GoodsInfo> list) {
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
		if(list != null && list.size() > position)
			return list.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		GoodsInfo goods = list.get(position);
		if (convertView != null) {
			imageView = (ImageView) convertView;

		} else {
			imageView = new ImageView(mContext);
			imageView.setBackgroundResource(R.drawable.category_grid_border);

			int height = (ScreenUtil.getScreenWidth(mContext)
					- 4
					* UnitTools.dip2px(mContext, mContext.getResources()
							.getDimension(R.dimen.home_gridview_item_space)))/3;
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.FILL_PARENT, height);
			imageView.setPadding(5, 5, 5, 5);
			imageView.setLayoutParams(layoutParams);

		}

		bitmapUtils.display(imageView, goods.getThumb(),
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

		return imageView;
	}
}
