package com.market.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haoyikuai.shop.android.R;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;

/**
 * 
 * @author pumkid
 *
 *         用以下方式在XML中加入对应的代码
 *****************************************************
 *         <com.market.view.SwitchView ***************
 *         android:layout_width="70dip" **************
 *         android:layout_height="31dip"　　************
 *         android:paddingLeft="2dip" button Padding *
 *         />*****************************************
 *****************************************************
 */
public class SwitchView extends RelativeLayout {
	private Paint btnPaint;
	private Paint bgPaint;

	private Bitmap bm_bgon;
	private Bitmap bm_bgoff;
	private Bitmap bm_btn;

	private int color_bg_on = 0;// #000000
	private int color_bg_off = -1;// #FFFFFF
	private int color_btn = Color.CYAN;
	private float btn_begin;
	private float btn_end;

	private ImageView sb;
	private ImageView line;
	private ImageView lineoff;
	private Context context;
	private final static boolean SWITCHOFF = false;
	private final static boolean SWITCHON = true;
	private boolean isOn = SWITCHOFF;
	private boolean status = SWITCHOFF;
	OnSwitchViewSeletedListener onSeletedListener;
	private boolean selectable = true;
	private boolean hasadd = false;
	private AttributeSet attrs;

	private boolean requstStatus = false;

	private final int DURATION = 150;
	private int durationf = 200;
	
	private int btn_padding = 2;
	private int bg_width;
	private int bg_height;
	private float btn_radius;
	private Rect rect = new Rect();

	private float action_btn_begin = 0;
	private float action_btn_current = 0;

	private float distant = 0.0f;
	private float precent = 0.0f;

	private Animation translate_on;
	private Animation aalph_on;
	private Animation translate_off;
	private Animation aalph_off;
	
	private AnimationListener animationListener;
	
	public void setOnSeletedListener(OnSwitchViewSeletedListener listener) {
		this.onSeletedListener = listener;
	}

	public SwitchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		init(attrs, context);
	}

	public SwitchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init(attrs, context);
	}

	public SwitchView(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
		init(null, context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		initSize(l, t, r, b);
		createViews(rect);
		Log.d("onLayout");
	}

	
	
	
	private void addViews() {
		if (sb != null) {
			sb.setImageBitmap(bm_btn);
		}
		if (line != null) {
			line.setImageBitmap(bm_bgon);
			line.setVisibility(View.GONE);
		}
		if (lineoff != null) {
			lineoff.setImageBitmap(bm_bgoff);
		}
		
	}

	/**
	 * 初始化颜色和尺寸
	 * 
	 * @param attrs
	 * @param context
	 * @author pumkid
	 */
	private void init(AttributeSet attrs, Context context) {
		this.attrs = attrs;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_switchview, this);
		sb = (ImageView) view.findViewById(R.id.switchlayout_switchButton);
		line = (ImageView) view.findViewById(R.id.switchlayout_line);
		lineoff = (ImageView) view.findViewById(R.id.switchlayout_lineoff);

		Resources resource = context.getResources();

		color_btn = resource
				.getColor(ResourceLocation.COLOR_SWITCH_BUTTON_SELF);
		color_bg_on = resource
				.getColor(ResourceLocation.COLOR_SWITCH_BUTTON_BGON);
		color_bg_off = resource
				.getColor(ResourceLocation.COLOR_SWITCH_BUTTON_BGOFF);

		btnPaint = createPaint(color_btn, Style.FILL_AND_STROKE, Paint.ANTI_ALIAS_FLAG);
		bgPaint = createPaint(color_bg_off, Style.FILL, Paint.ANTI_ALIAS_FLAG);

	}

	/**
	 * 
	 * 初始化具体尺寸 在onLayout中触发
	 * 
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 * @author pumkid
	 */
	private void initSize(int l, int t, int r, int b) {
		this.getDrawingRect(rect);
		if (attrs != null) {
			bg_width = r - l;
			bg_height = b - t;
			if (bg_height % 2 == 0)
				bg_height += 1; // 单数，对
			btn_padding = this.getPaddingLeft();
			btn_radius = (bg_height / 2) - btn_padding;
		}
		btn_begin = bg_height / 2;// rect.left + btn_radius + btn_padding;
		btn_end = bg_width - bg_height;// rect.right - btn_radius
										// * 2 - btn_padding*3;
		durationf = this.DURATION + bg_width;
	}

	/**
	 * 初始化所有View
	 * 
	 * @param rect
	 * @author pumkid
	 */
	private void createViews(Rect rect) {
		
		if (hasadd)
			return;

		Canvas canvas;
		RectF rf = new RectF(rect.left, rect.top, rect.right, rect.bottom);
		if (bm_bgon == null) {
			bm_bgon = Bitmap.createBitmap(bg_width, bg_height,
					Bitmap.Config.ARGB_8888);
			canvas = new Canvas(bm_bgon);
			if (bgPaint != null) {
				bgPaint.setColor(Color.GREEN);
				canvas.drawRoundRect(rf, bg_height / 2, bg_height / 2,
						this.bgPaint);
			}

		}
		if (bm_bgoff == null) {
			bm_bgoff = Bitmap.createBitmap(bg_width, bg_height,
					Bitmap.Config.ARGB_8888);
			canvas = new Canvas(bm_bgoff);
			if (bgPaint != null) {
				bgPaint.setColor(color_bg_off);
				canvas.drawRoundRect(rf, bg_height / 2, bg_height / 2,
						this.bgPaint);
			}

		}
		if (bm_btn == null) {
			bm_btn = Bitmap.createBitmap(bg_width, bg_height,
					Bitmap.Config.ARGB_8888);
			canvas = new Canvas(bm_btn);
			canvas.drawCircle(btn_begin, bg_height / 2, btn_radius,
					this.btnPaint);
		}
		initAnimation();
		addViews();
		if (requstStatus != status)
			changeStatus(0, 0, 0);
		
		hasadd = true;
	}

	private Paint createPaint(int color, Style style, int flag) {
		Paint tmp = new Paint();
		tmp.setColor(color);
		tmp.setStyle(style);
		tmp.setFlags(flag);
		tmp.setAntiAlias(true);
		return tmp;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			return true;
		case MotionEvent.ACTION_UP:
			changeStatus(0, 0, durationf);
			return true;
		case MotionEvent.ACTION_MOVE:
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Change the state of the switch button, and trigger the task when event
	 * happens
	 * 
	 * @param cx
	 *            unused
	 * @param disp
	 *            unused
	 * @author pumkid
	 */
	private void changeStatus(int cx, int disp, long duration) {
		Animation translate;
		Animation aalph;

		if (status == SWITCHON) {
			translate_on.setDuration(duration);
			sb.startAnimation(translate_on);
			aalph_on.setDuration(duration);
			lineoff.startAnimation(aalph_on);
			lineoff.setVisibility(View.VISIBLE);
			status = SWITCHOFF;
			if (onSeletedListener != null)
				onSeletedListener.ondisseleted(this);
			
		} else if (status == SWITCHOFF) {
			translate_off.setDuration(duration);
			sb.startAnimation(translate_off);
			aalph_off.setDuration(duration);
			lineoff.startAnimation(aalph_off);
			lineoff.setVisibility(View.GONE);
			status = SWITCHON;
			if (onSeletedListener != null)
				onSeletedListener.onseleted(this);
		}
	}

	public void initAnimation()
	{
		if(animationListener == null)
		{
			animationListener = new AnimationListener()
			{

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					if(lineoff.getVisibility() == View.VISIBLE)
						line.setVisibility(View.GONE);
					else
						line.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
						line.setVisibility(View.VISIBLE);
				}
				
			};
		}
		translate_on = new TranslateAnimation(btn_end, 0, 0, 0);
		translate_on.setFillAfter(true);
		aalph_on = new AlphaAnimation(0f, 1f);
		aalph_on.setAnimationListener(animationListener);
		translate_off = new TranslateAnimation(0, btn_end, 0, 0);
		translate_off.setFillAfter(true);
		aalph_off = new AlphaAnimation(1f, 0f);
		aalph_off.setAnimationListener(animationListener);
	}
	
	public boolean isOn() {
		return status;
	}

	public interface OnSwitchViewSeletedListener {
		public void onseleted(View view);

		public void ondisseleted(View view);
	}

	public void setIsOn(boolean isOn) {
		if (status != isOn) {
			if (hasadd) {
				changeStatus(0, 0, 300);
			} else {
				this.requstStatus = isOn;
			}
		} else
			status = isOn;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

}
