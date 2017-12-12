package com.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.haoyikuai.shop.android.R;
import com.market.utils.Log;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class SlidingMenu extends RelativeLayout implements OnClickListener {
	private Bitmap bmbg;


	private ImageView iv_bg;
	private ImageView iv_trigger;

	private RelativeLayout bgview;


	private boolean isOn = true;
	private boolean isFirstInit=true;

	private OnMenuBtnClickListener onMenuBtnClickListener;

	private Scroller mScroller;
	
	private Button btn_sildingmenucwf;
	private Button btn_sildingmenucws;

	public SlidingMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, null);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {


		mScroller = new Scroller(getContext());

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bgview = (RelativeLayout) inflater.inflate(R.layout.view_slidingmenu,
				this);

		iv_bg = (ImageView) bgview.findViewById(R.id.iv_sildingmenuBg);
		iv_trigger = (ImageView) bgview
				.findViewById(R.id.iv_sildingmenuTrigger);
		iv_trigger.setOnClickListener(this);
		btn_sildingmenucwf = (Button)bgview.findViewById(R.id.btn_sildingmenucwf);
		btn_sildingmenucws = (Button)bgview.findViewById(R.id.btn_sildingmenucws);
		btn_sildingmenucwf.setGravity(Gravity.CENTER);
		bgview.findViewById(R.id.btn_sildingmenucwf).setOnClickListener(this);
		bgview.findViewById(R.id.btn_sildingmenucws).setOnClickListener(this);

	}
	
	

	@Override
	public void computeScroll() {

		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);

		if(isFirstInit){
			closeMenuBar(false);
			
			isFirstInit=false;
		}
		iv_bg.setImageBitmap(createBackground(getWidth(), getHeight()));

	}

	private void openMenuBar() {
		if (!isOn) {
			ObjectAnimator.ofFloat(this, "translationX", 0).setDuration(500).start();
			ObjectAnimator.ofFloat(iv_trigger, "rotation", 0,360).setDuration(400).start();
//			smoothScrollBy(dx, 0);
			isOn = true;
		}
	}

	private void closeMenuBar(boolean hasAnimation) {
		if (isOn) {
			bgview.measure(0, 0);
			int dx = (bgview.getMeasuredWidth())-bgview.getMeasuredHeight();
			if (hasAnimation) {
				ObjectAnimator.ofFloat(this, "translationX", dx).setDuration(500).start();
				ObjectAnimator.ofFloat(iv_trigger, "rotation", 360,0).setDuration(400).start();
			} else {
				ObjectAnimator.ofFloat(this, "translationX", dx).setDuration(0).start();
			}
			isOn = false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private Bitmap createBackground(int width, int height) {
		if (bmbg != null)
			return bmbg;

		bmbg = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmbg);
		Paint bgpaint = new Paint();
		bgpaint.setColor(0x55555555);
		bgpaint.setStyle(Style.FILL);
		bgpaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		bgpaint.setAntiAlias(true);
		final int HALFOFHEIGHT = height / 2;
		RectF rect = new RectF(HALFOFHEIGHT, 0, width, height);
		Path path = new Path();
		path.addCircle(HALFOFHEIGHT, HALFOFHEIGHT, HALFOFHEIGHT, Direction.CW);
		path.addRect(rect, Direction.CW);
		canvas.drawPath(path, bgpaint);
		return bmbg;
	}

	public void smoothScrollBy(int dx, int dy) {

		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy, 1000);
		invalidate();
	}

	public void quickScrollBy(int dx, int dy) {

		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy, 0);
		invalidate();
	}
	

	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.iv_sildingmenuTrigger:
			if (isOn) {
				closeMenuBar(true);
			} else {
				openMenuBar();
			}

			break;

		case R.id.btn_sildingmenucwf:
			if (onMenuBtnClickListener != null)
				onMenuBtnClickListener.onButton1Click();
			break;

		case R.id.btn_sildingmenucws:
			if (onMenuBtnClickListener != null)
				onMenuBtnClickListener.onButton2Click();
			break;
		}
	}

	public interface OnMenuBtnClickListener {
		public void onButton1Click();

		public void onButton2Click();
	}

	public OnMenuBtnClickListener getOnMenuBtnClickListener() {
		return onMenuBtnClickListener;
	}

	public void setOnMenuBtnClickListener(
			OnMenuBtnClickListener onMenuBtnClickListener) {
		this.onMenuBtnClickListener = onMenuBtnClickListener;
	}

	private RelativeLayout.LayoutParams getCurrentLayoutParams() {
		return (RelativeLayout.LayoutParams) this.getLayoutParams();
	}
	
	private void setViewCenter()
	{
		if(btn_sildingmenucws != null && btn_sildingmenucwf != null)
		{
			btn_sildingmenucws.setGravity(Gravity.CENTER);
			btn_sildingmenucwf.setGravity(Gravity.CENTER);
			btn_sildingmenucwf.invalidate();
			btn_sildingmenucws.invalidate();
		}
	}

}
