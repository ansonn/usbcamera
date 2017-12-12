package com.market.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.GridView;
/**
 * 	陈伟斌
 */
public class AdaptionGridView extends GridView {
	public AdaptionGridView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));//设置item中点的背景为透明
	}

	/**
	 * 设置不滚动
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
