package com.market.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haoyikuai.shop.android.R;
import com.market.utils.Log;

public class FloatMenu extends RelativeLayout implements OnClickListener {
	private int width;
	private int height;
	private int left;
	private int right;
	private int top;
	private int bottom;
	private int cf_origenalmarginLeft;
	private int cs_origenalmarginLeft;

	private Context context;
	private ImageView bg;
	private ImageView imb_chat, imb_chatbg;
	private Button btnSwitcher;
	private Button btnChatwithServer;
	private Button btnChatwithFriends;

	private int margin_bottom = 200;
	
	private final int BTNWIDTH = 150;
	private Bitmap bmbg;

	private boolean isOn = false;
	private boolean isinited = false;

	private RelativeLayout.LayoutParams lpmm;
	private RelativeLayout.LayoutParams lpmw;
	private RelativeLayout.LayoutParams lpmwbg;
	private RelativeLayout.LayoutParams lpbtnfriend;
	private RelativeLayout.LayoutParams lpbtnserver;

	private TranslateAnimation translate;
	private TranslateAnimation translateII;
	private Animation operatingAnim;
	private Animation operatingAnimc;
	private AnimationListener animationListener;

	public FloatMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public FloatMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	
	

	private void init(Context context) {
		this.context = context;
		btnSwitcher = new Button(context);
		btnChatwithServer = new Button(context);
		btnChatwithFriends = new Button(context);
		tost = Toast.makeText(context, "", Toast.LENGTH_SHORT);
	}

	private void initView() {
		if (!isinited) {
			RelativeLayout.LayoutParams rl = (LayoutParams) this
					.getLayoutParams();
			rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
			rl.addRule(RelativeLayout.ALIGN_RIGHT, RelativeLayout.TRUE);
			rl.setMargins(-width, 0 , 0, margin_bottom);
			this.setLayoutParams(rl);
			this.invalidate();
//			if(isOn)
//				rl.setMargins(-width, rl.topMargin, rl.rightMargin, rl.bottomMargin);
//			else
//				rl.setMargins(width-height, rl.topMargin, rl.rightMargin, rl.bottomMargin);

			bg = new ImageView(context);
			bg.setImageBitmap(createBackground());
			imb_chat = new ImageView(context);
			imb_chat.setTag("imb_chat");
			imb_chatbg = new ImageView(context);
			imb_chatbg.setTag("imb_chatbg");
			initAnimation();

			lpmm = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			lpmw = new RelativeLayout.LayoutParams(height - 15, height - 15);
			lpmwbg = new RelativeLayout.LayoutParams(height, height);

			lpmw.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			lpmw.leftMargin = 10;
			lpmwbg.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			// lpmwbg.leftMargin = 10;

			imb_chat.setLayoutParams(lpmw);
			imb_chat.setImageResource(R.drawable.liao);
			imb_chat.setScaleType(ScaleType.CENTER_CROP);
			imb_chatbg.setLayoutParams(lpmwbg);

			bg.setLayoutParams(lpmm);
			this.addView(bg);
			this.addView(imb_chat);
			this.addView(imb_chatbg);

			initButton();

			imb_chatbg.setOnClickListener(this);
		}
		isinited = true;
	}

	private void initButton() {
		btnChatwithFriends.setTag("btnChatwithFriends");
		btnChatwithServer.setTag("btnChatwithServer");
		btnChatwithServer.setTextColor(Color.WHITE);
		btnChatwithFriends.setTextColor(Color.WHITE);

		btnChatwithServer.setText(this.getResources().getString(
				R.string.goodsov_chatwithserver));
		btnChatwithFriends.setText(this.getResources().getString(
				R.string.goodsov_chatwithfriend));

		lpbtnfriend = new RelativeLayout.LayoutParams(BTNWIDTH, height - 15);
		lpbtnfriend
				.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		cf_origenalmarginLeft = (int) (height * 1.2);
		lpbtnfriend.setMargins(cf_origenalmarginLeft, 0, 0, 0);
		btnChatwithFriends.setLayoutParams(lpbtnfriend);
		btnChatwithFriends.setBackgroundDrawable(this.getResources()
				.getDrawable(R.drawable.bg_slidding_menu_cf));
		this.addView(btnChatwithFriends);

		lpbtnserver = new RelativeLayout.LayoutParams(BTNWIDTH, height - 15);
		lpbtnserver
				.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		cs_origenalmarginLeft = (int) (height * 1.3) + BTNWIDTH;
		lpbtnserver.setMargins(cs_origenalmarginLeft, 0, 0, 0);
		btnChatwithServer.setLayoutParams(lpbtnserver);
		btnChatwithServer.setBackgroundDrawable(this.getResources()
				.getDrawable(R.drawable.bg_slidding_menu_cs));
		this.addView(btnChatwithServer);

		btnChatwithFriends.setOnClickListener(this);
		btnChatwithServer.setOnClickListener(this);
	
	}

	private void initAnimation() {
		if (animationListener == null) {
			animationListener = new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					if (!isOn) {
						if (lpbtnserver != null)
							lpbtnserver.setMargins(width, 0, 0, 0);
						if (lpbtnfriend != null)
							lpbtnfriend.setMargins(width, 0, 0, 0);
						if (btnChatwithFriends != null) {
							btnChatwithFriends.setLayoutParams(lpbtnfriend);
						}
						if (btnChatwithServer != null) {
							btnChatwithServer.setLayoutParams(lpbtnserver);
						}
						if (animationListener == null) {
							animationListener = new AnimationListener() {

								@Override
								public void onAnimationEnd(Animation arg0) {
									// TODO Auto-generated method stub
									if (!isOn) {
										if (lpbtnserver != null)
											lpbtnserver.setMargins(width, 0, 0, 0);
										if (lpbtnfriend != null)
											lpbtnfriend.setMargins(width, 0, 0, 0);
										if (btnChatwithFriends != null) {
											btnChatwithFriends.setLayoutParams(lpbtnfriend);
										}
										if (btnChatwithServer != null) {
											btnChatwithServer.setLayoutParams(lpbtnserver);
										}
										FloatMenu.this.invalidate();
									}

								}

								@Override
								public void onAnimationRepeat(Animation arg0) {
									// TODO Auto-generated method stub
								}

								@Override
								public void onAnimationStart(Animation arg0) {
									// TODO Auto-generated method stub
									if (isOn) {
										if (lpbtnserver != null)
											lpbtnserver.setMargins(cs_origenalmarginLeft, 0, 0,
													0);
										if (lpbtnfriend != null)
											lpbtnfriend.setMargins(cf_origenalmarginLeft, 0, 0,
													0);
										if (btnChatwithFriends != null) {
											btnChatwithFriends.setLayoutParams(lpbtnfriend);
										}
										if (btnChatwithServer != null) {
											btnChatwithServer.setLayoutParams(lpbtnserver);
										}
										FloatMenu.this.invalidate();
									}

								}

							};
						}
						

						translate = new TranslateAnimation(0, width - height, 0, 0);
						translateII = new TranslateAnimation((width - height), 0, 0, 0);
						translate.setAnimationListener(animationListener);
						translateII.setAnimationListener(animationListener);
						operatingAnim = AnimationUtils.loadAnimation(context, R.anim.roanim);
						LinearInterpolator lin = new LinearInterpolator();
						operatingAnim.setInterpolator(lin);
						operatingAnim.setFillAfter(true);
						operatingAnim.setDuration(300);

						operatingAnimc = AnimationUtils.loadAnimation(context, R.anim.roanimr);
						operatingAnimc.setInterpolator(lin);
						operatingAnimc.setFillAfter(true);
						operatingAnimc.setDuration(300);

						translateII.setDuration(300);
						translateII.setFillAfter(true);
						translate.setDuration(300);
						translate.setFillAfter(true);		FloatMenu.this.invalidate();
					}

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					if (isOn) {
						if (lpbtnserver != null)
							lpbtnserver.setMargins(cs_origenalmarginLeft, 0, 0,
									0);
						if (lpbtnfriend != null)
							lpbtnfriend.setMargins(cf_origenalmarginLeft, 0, 0,
									0);
						if (btnChatwithFriends != null) {
							btnChatwithFriends.setLayoutParams(lpbtnfriend);
						}
						if (btnChatwithServer != null) {
							btnChatwithServer.setLayoutParams(lpbtnserver);
						}
						FloatMenu.this.invalidate();
					}

				}

			};
		}
		

		translate = new TranslateAnimation(0, width - height, 0, 0);
		translateII = new TranslateAnimation((width - height), 0, 0, 0);
		translate.setAnimationListener(animationListener);
		translateII.setAnimationListener(animationListener);
		operatingAnim = AnimationUtils.loadAnimation(context, R.anim.roanim);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		operatingAnim.setFillAfter(true);
		operatingAnim.setDuration(300);

		operatingAnimc = AnimationUtils.loadAnimation(context, R.anim.roanimr);
		operatingAnimc.setInterpolator(lin);
		operatingAnimc.setFillAfter(true);
		operatingAnimc.setDuration(300);

		translateII.setDuration(300);
		translateII.setFillAfter(true);
		translate.setDuration(300);
		translate.setFillAfter(true);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);

		width = r - l;
		height = b - t;
		left = l;
		right = r;
		top = t;
		bottom = b;
		initView();
	}

	private Bitmap createBackground() {
		if (bmbg != null)
			return bmbg;
		bmbg = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bmbg);
		Paint bgpaint = new Paint();
		bgpaint.setColor(0x55555555);
		bgpaint.setStyle(Style.FILL_AND_STROKE);
		bgpaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		bgpaint.setAntiAlias(true);
		RectF rect = new RectF(height / 2, 0, width, height);
		Path path = new Path();
		path.addCircle(height / 2, height / 2, height / 2, Direction.CW);
		path.addRect(rect, Direction.CW);
		canvas.drawPath(path, bgpaint);
		return bmbg;
	}

	Toast tost;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("---------------onClick------------------------");
		if (v.getTag() != null) {
			if (v.getTag().equals("btnChatwithFriends")) {
				tost.setText("Chat with Friends");
				tost.show();
				return;
			}
			if (v.getTag().equals("btnChatwithServer")) {
				tost.setText("Chat with Server");
				tost.show();
				return;
			}
			
		}
		if (isOn) {
			isOn = false;
			this.setAnimation(translate);
			this.startAnimation(translate);
			imb_chat.startAnimation(operatingAnim);
			lpmwbg.setMargins(width - height, lpmwbg.topMargin, 0,
					lpmwbg.bottomMargin);
			

		} else {
			isOn = true;
			this.setAnimation(translateII);
			this.startAnimation(translateII);
			imb_chat.startAnimation(operatingAnimc);
			lpmwbg.setMargins(0, 0, 0, 0);

		}
		imb_chatbg.setLayoutParams(lpmwbg);
		bg.setLayoutParams(lpmm);
		// this.invalidate();

	}

}
