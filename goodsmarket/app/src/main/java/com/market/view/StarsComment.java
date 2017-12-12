package com.market.view;

import com.haoyikuai.shop.android.R;
import com.market.utils.Log;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.view.View;
import android.view.View.OnClickListener;

public class StarsComment extends LinearLayout implements OnClickListener {

	private Context context;
	private boolean setEditable = false;
	private ImageView[] stars;
	private int starCount;
	private final int TOTALCOUNT = 5;

	public StarsComment(Context context) {
		super(context);
		init(context);
	}

	public StarsComment(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.context = context;
		stars = new ImageView[TOTALCOUNT];
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < stars.length; i++) {
			stars[i] = new ImageView(context);
			stars[i].setLayoutParams(layoutParams);
			stars[i].setImageResource(R.drawable.unrated);
			stars[i].setOnClickListener(this);
			stars[i].setScaleType(ScaleType.FIT_CENTER);
			this.addView(stars[i]);
		}
	}
	

	@Override
	public void onClick(View view) {
		if (setEditable) {
			for (int i = 0; i < stars.length; i++) {
				if (stars[i] == view) {
					this.setStarCount(i+1);
					break;
				}
			}
		}
	}

	public boolean isEditable() {
		return setEditable;
	}

	public void setEditable(boolean setEditable) {
		this.setEditable = setEditable;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
		if (stars == null || starCount > stars.length)
			return;
		for (int i = 0; i < this.starCount; i++)
			stars[i].setImageResource(R.drawable.rated);

		for (int i = this.starCount; i < stars.length; i++)
			stars[i].setImageResource(R.drawable.unrated);
	}

}
