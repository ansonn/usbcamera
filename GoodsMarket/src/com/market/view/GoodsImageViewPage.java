package com.market.view;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class GoodsImageViewPage extends ViewPager {
	private Context context;
	private OnPageScorllListener onPageScorllListener;
	private TextView title;
	private String[] urlsinpage;
	private float mTrans;
	private static final String TAG = "GoodsImageViewPage";
	/**
	 * 保存position与对于的View
	 */
	private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
	private View mLeft;
	private View mRight;

	public GoodsImageViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GoodsImageViewPage(Context context) {
		super(context);
	}

	public void init(Context context) {
		this.context = context;
	}

	public void setPageImages(String[] urls) {
		MyPagerAdapter myPagerView = new MyPagerAdapter(context, this);
		this.setAdapter(myPagerView);
		myPagerView.setUrls(urls);
		urlsinpage = urls;
		myPagerView.notifyDataSetChanged();
		if (title != null && urlsinpage != null)
		title.setText((1) + "/" + urlsinpage.length);
		this.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (title != null && urlsinpage != null)
					title.setText((position + 1) + "/" + urlsinpage.length);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
		mLeft = findViewFromObject(position);
		mRight = findViewFromObject(position + 1);
		animateStack(mLeft, mRight, effectOffset, positionOffsetPixels);
		
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}
	
	

	public void setObjectForPosition(View view, int position) {
		mChildrenViews.put(position, view);
	}

	/**
	 * 通过过位置获得对应的View
	 * 
	 * @param position
	 * @return
	 */
	public View findViewFromObject(int position) {
		return mChildrenViews.get(position);
	}

	private boolean isSmall(float positionOffset) {
		return Math.abs(positionOffset) < 0.0001;
	}

	protected void animateStack(View left, View right, float effectOffset,
			int positionOffsetPixels) {
		if (right != null) {
			mTrans = effectOffset;
			ViewHelper.setScrollX(right, (int) mTrans);
		}
		if (left != null) {
			left.bringToFront();
		}
	}

	class MyPagerAdapter extends PagerAdapter {
		private String[] urls;
		private Context context;
		private GoodsImageViewPage goodsImageViewPage;
		private BitmapUtils bitmaputils;

		public MyPagerAdapter(String[] urls, Context context,
				GoodsImageViewPage goodsImageViewPage) {
			this.urls = urls;
			this.context = context;
			this.goodsImageViewPage = goodsImageViewPage;
			this.bitmaputils = new BitmapUtils(context);
		}

		public MyPagerAdapter(Context context,
				GoodsImageViewPage goodsImageViewPage) {
			this(null, context, goodsImageViewPage);
		}

		public void setUrls(String[] urls) {
			this.urls = urls;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (urls != null)
				return urls.length;
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = new ImageView(this.context);

			imageView.setScaleType(ScaleType.CENTER_CROP);
			container.addView(imageView);
			bitmaputils.display(imageView, urls[position],
					new BitmapLoadCallBack<View>() {

						@Override
						public void onPreLoad(View container, String uri,
								BitmapDisplayConfig config) {
							// TODO Auto-generated method stub
							super.onPreLoad(container, uri, config);
							((ImageView) container).setImageResource(R.drawable.loading);
						}

						@Override
						public void onLoadCompleted(View container, String arg1,
								Bitmap bitmap, BitmapDisplayConfig arg3,
								BitmapLoadFrom arg4) {
							// TODO Auto-generated method stub
							((ImageView)container).setImageBitmap(bitmap);
						}

						@Override
						public void onLoadFailed(View container, String arg1,
								Drawable arg2) {
							// TODO Auto-generated method stub
							((ImageView) container).setImageResource(R.drawable.loading);
						}
					});
			goodsImageViewPage.setObjectForPosition(imageView, position);
			return imageView;
		}
	}

	interface OnPageScorllListener {
		public void OnPageScorlled();
	}

	public OnPageScorllListener getOnPageScorllListener() {
		return onPageScorllListener;
	}

	public void setOnPageScorllListener(
			OnPageScorllListener onPageScorllListener) {
		this.onPageScorllListener = onPageScorllListener;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

}
