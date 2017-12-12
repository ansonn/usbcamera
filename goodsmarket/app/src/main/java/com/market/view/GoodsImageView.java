package com.market.view;

import com.haoyikuai.shop.android.R;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class GoodsImageView extends RelativeLayout{
	private RelativeLayout mainView;
	private CircleView cv_goodsViewprocess;
	private GoodsImageViewPage img_goodsView;
	public GoodsImageView(Context context) {
		super(context);
		init(context);
	}

	public GoodsImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public GoodsImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	
	private void init(Context context)
	{
		LayoutInflater lf = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		mainView = (RelativeLayout)lf.inflate(R.layout.view_goodsviewpage, null);
		img_goodsView = (GoodsImageViewPage)mainView.findViewById(R.id.img_goodsView);
		cv_goodsViewprocess = (CircleView)mainView.findViewById(R.id.cv_goodsViewprocess);
		img_goodsView.setTitle(cv_goodsViewprocess);
		this.addView(mainView);
	}
	
	public void setPageImages(String[] imgeurls)
	{
		if(img_goodsView != null)
			img_goodsView.setPageImages(imgeurls);
	}

	
	
}
