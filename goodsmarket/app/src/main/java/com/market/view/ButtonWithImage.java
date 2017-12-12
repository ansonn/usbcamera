package com.market.view;

import com.market.utils.Log;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;

public class ButtonWithImage extends Button {
	private Context context;
	private AttributeSet attrs;
	private Drawable siblingImage;
	private Bitmap siblingbitmap;
	private int paddingImage = 12;
	private int widthofimage;
	private int heightofimage;
	private final int TEXTSIZE = 14;
	private final int TEXTCOLOR = -1;// white
	private Paint mBitmapPaint;

	public ButtonWithImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context, null);
	}

	public ButtonWithImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	public ButtonWithImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		this.context = context;
		this.attrs = attrs;
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		this.setGravity(Gravity.RIGHT|Gravity.CENTER_VERTICAL);
		
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = this.getMeasuredWidth();
		int height = this.getMeasuredHeight();
		if( siblingbitmap != null)
		{
			widthofimage = siblingbitmap.getWidth();
			heightofimage = siblingbitmap.getHeight();
			width = widthofimage + width;
		}
		setMeasuredDimension(width, height);
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (siblingbitmap == null && siblingImage != null) {
		}
		int height = this.getMeasuredHeight();
		
		if(siblingbitmap != null)
		{
			int imgGravityh = height/2 - widthofimage/2;
			canvas.drawBitmap(siblingbitmap, this.getPaddingLeft(), imgGravityh,
				mBitmapPaint);
		}
		super.onDraw(canvas);

	}

	
	public Drawable getSiblingImage() {
		return siblingImage;
	}

	public void setSiblingImage(Drawable siblingImage) {
		this.siblingImage = siblingImage;
		siblingbitmap = ((BitmapDrawable) siblingImage).getBitmap();
	}

	public int getPaddingImage() {
		return paddingImage;
	}

	public void setPaddingImage(int paddingImage) {
		this.paddingImage = paddingImage;
	}

}
