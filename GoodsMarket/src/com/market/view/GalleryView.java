package com.market.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.market.bean.AdPicture;
import com.market.bean.Category;
import com.market.utils.ScreenUtil;

/**
 * 
 * @tag Í¼Æ¬Õ¹Ê¾
 * @author ³ÂÎ°±ó
 * @date 2015-1-21
 */
public class GalleryView extends LinearLayout implements OnItemSelectedListener {
	private Gallery mGallery;
	private GalleryAdapter mGalleryAdapter;
	private AdPicture[] imageList;
	private LinearLayout llPoints;
	private List<ImageView> pointList;
	private BitmapUtils bitmapUtils;

	public GalleryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context)
				.inflate(R.layout.gallery_view_layout, this);

		mGallery = (Gallery) findViewById(R.id.gallery);
		llPoints = (LinearLayout) findViewById(R.id.ll_points);
		mGalleryAdapter = new GalleryAdapter();
		mGallery.setAdapter(mGalleryAdapter);

		mGallery.setOnItemSelectedListener(this);

		bitmapUtils = new BitmapUtils(getContext());
		
		
		mGallery.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, ScreenUtil.getScreenHeight(getContext())/4));
	}

	public void setData(AdPicture[] list) {
		imageList = list; 

		mGalleryAdapter.setData(imageList);

		pointList = new ArrayList<ImageView>();
		llPoints.removeAllViews();

		for (int i = 0; i < imageList.length; i++) {
			ImageView imageView = new ImageView(getContext());
			pointList.add(imageView);

			if (i == 0) {
				imageView.setImageResource(R.drawable.point_selected);
			} else {
				imageView.setImageResource(R.drawable.point_nor);
			}
			llPoints.addView(imageView);
		}

		mGalleryAdapter.notifyDataSetChanged();
	}

	class GalleryAdapter extends BaseAdapter {

		private AdPicture[] imageList;

		public void setData(AdPicture[] imageList) {
			this.imageList = imageList;
		}

		@Override
		public int getCount() {
			if (imageList == null) {
				return 0;
			}
			return imageList.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
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
			if (convertView != null) {
				imageView = (ImageView) convertView;
			} else {
				imageView = new ImageView(getContext());
				imageView.setAdjustViewBounds(true);
				imageView.setLayoutParams(new Gallery.LayoutParams(
						LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				imageView.setScaleType(ScaleType.FIT_XY);
			}
			AdPicture adPicture = imageList[position];
			bitmapUtils.display(imageView, adPicture.getPic_url());
			// imageView.setBackgroundDrawable(imageList.get(position));
			return imageView;
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (pointList == null) {
			return;
		}
		for (int i = 0; i < pointList.size(); i++) {
			ImageView imageView = pointList.get(i);
			if (i == position) {
				imageView.setImageResource(R.drawable.point_selected);
			} else {
				imageView.setImageResource(R.drawable.point_nor);
			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
}
