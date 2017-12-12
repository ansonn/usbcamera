package com.market.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.GridView;
/**
 * 	��ΰ��
 */
public class AdaptionGridView extends GridView {
	public AdaptionGridView(android.content.Context context,
			android.util.AttributeSet attrs) {
		super(context, attrs);
		this.setSelector(new ColorDrawable(Color.TRANSPARENT));//����item�е�ı���Ϊ͸��
	}

	/**
	 * ���ò�����
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);

	}

}
