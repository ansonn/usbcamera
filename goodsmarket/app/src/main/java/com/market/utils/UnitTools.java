package com.market.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haoyikuai.shop.android.R;
import com.market.bean.Spec;

public class UnitTools {

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Dialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);// �õ�����view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// ���ز���
		// main.xml�е�ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// ��ʾ����
		// ���ض���
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// ʹ��ImageView��ʾ����
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// ���ü�����Ϣ

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// �����Զ�����ʽdialog

		loadingDialog.setCancelable(false);// �������á����ؼ���ȡ��
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// ���ò���
		return loadingDialog;

	}

	public static Toast createMassgeToast(Context context, String msg,
			int imageResource) {

		Toast toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, 0);
		TextView mTextView = new TextView(context);
		LinearLayout mLinearLayout = new LinearLayout(context);
		mLinearLayout.setBackgroundResource(android.R.drawable.toast_frame);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		ImageView mImageView = new ImageView(context);
		if (imageResource < 1)
			imageResource = R.drawable.selected;
		mImageView.setImageResource(imageResource);
		mLinearLayout.addView(mImageView);
		mTextView.setTextColor(Color.WHITE);
		mTextView.setText("\n" + msg);
		mLinearLayout.addView(mTextView);
		toast.setView(mLinearLayout);
		toast.setDuration(500);
		toast.show();
		return toast;
	}

	public static final String DATEYMDHMSBACKSLASH = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEYMDBACKSLASH = "yyyy-MM-dd";

	public static String long2Date(String dateFormat, long millsecond) {
		millsecond = millsecond * 1000;
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millsecond);
		return sdf.format(date);
	}

	public static String long2DateByDefault(long millsecond) {
		return long2Date(DATEYMDHMSBACKSLASH, millsecond);
	}

	public static String long2DateByDATEYMD(long millsecond) {
		return long2Date(DATEYMDBACKSLASH, millsecond);
	}
	
	public static String formatDoubleTag(Double tax , String format)
	{
		if(tax == null)
			return "0.00";
		DecimalFormat df=new DecimalFormat(format);
		return df.format(tax);
	}
	
	public static String formatDoubleTagTail2(Double tax)
	{
		if(tax == null)
			return "0.00";
		DecimalFormat df=new DecimalFormat("#.00");
		return df.format(tax);
	}
}
