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
import com.market.bean.GoodsInfo;

public class GoodListAdapter extends BaseAdapter {

	private List<GoodsInfo> list;

	private Context mContext;
	BitmapUtils bitmapUtils;
	public GoodListAdapter(Context context,BitmapUtils bitmapUtils) {
		mContext = context;
		this.bitmapUtils = bitmapUtils;
	}

	public void updateData(List<GoodsInfo> list) {
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
		GoodsInfo goodInfo = list.get(position);
		if (convertView != null) {
			view = convertView;

		} else {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_good_list, null);
		}

		ImageView imageView = (ImageView) view.findViewById(R.id.iv_thumb);

		TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
		TextView tvPrice = (TextView) view.findViewById(R.id.tv_price);
		TextView tvComment = (TextView) view.findViewById(R.id.tv_good_comment);

		tvTitle.setText(goodInfo.getTitle());
		tvPrice.setText("гд"+goodInfo.getMarket_price());
		int goodCommentRate=(int) (goodInfo.getStore() / 5.0f * 100.0f);
		tvComment.setText(mContext.getString(R.string.good_comment, goodCommentRate,goodInfo.getComment_count()));
		bitmapUtils.display(imageView, goodInfo.getThumb(),
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
