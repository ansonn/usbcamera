package com.market;

import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;
import com.market.utils.UnitTools;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 为了方便管理，所有Activity都继承这个类
 * 
 * @author 陈伟斌
 * 
 */
public class BaseActivity extends Activity {
	protected Toast toastMessage;
	protected Dialog loadingDialog;
	private int needProcessBar_count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 设置Activity标题
	 * 
	 * @param title
	 * @param onClicklistener
	 * @author pumkid
	 */
	public void setActivityTitle(String tittle) {
		Button header_abtn = (Button) this
				.findViewById(ResourceLocation.BUTTON_HEADER_CENTER);
		if (header_abtn != null) {
			header_abtn.setVisibility(View.VISIBLE);
			header_abtn.setText(tittle);
		}
	}

	/**
	 * 设置标题头部按扭
	 * 
	 * @param title
	 * @param onClicklistener
	 * @author pumkid
	 */
	public void setHeaderButton(final int btn_id, final int imageBg,
			String title, OnClickListener onClicklistener) {
			View view =  this.findViewById(btn_id);
		if (view != null) {
			view.setVisibility(View.VISIBLE);
			if (imageBg > 0)
			{
				ImageButton header_imagebtn = (ImageButton)view;
				header_imagebtn.setImageResource(imageBg);
				header_imagebtn.setScaleType(ScaleType.CENTER_INSIDE);
			}
			else
			{
				Button header_abtn = (Button)view;
				header_abtn.setText(title);
			}
			view.setOnClickListener(onClicklistener);
		}
	}

	/**
	 * 设置标题返回事件头部按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public void setDeftualtHeaderLeftButton() {
		setHeaderButton(ResourceLocation.BUTTON_HEADER_LEFTIMG,
				ResourceLocation.IMG_HEADER_BACKICON, Constants.TITLEEMPTY,
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						BaseActivity.this.finish();
					}

				});
	}



	/**
	 * 设置标题返回事件头部按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public void setDeftualtHeaderLeftButton(OnClickListener onCliclistener) {
		setHeaderButton(ResourceLocation.BUTTON_HEADER_LEFTIMG,
				ResourceLocation.IMG_HEADER_BACKICON, Constants.TITLEEMPTY,
				onCliclistener);
	}

	/**
	 * 设置标题头部右则按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public int setStringHeaderRightButton(String oprationName,
			OnClickListener onClickListener) {

		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					BaseActivity.this.finish();
				}

			};
		setHeaderButton(ResourceLocation.BUTTON_HEADER_RIGHT,
				ResourceLocation.IMG_HEADER_EMPTY, oprationName,
				onClickListener);

		return ResourceLocation.BUTTON_HEADER_RIGHT;
	}

	/**
	 * 设置标题头部右则按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public int setImageHeaderRightButton(int imageResource,
			OnClickListener onClickListener) {

		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					BaseActivity.this.finish();
				}

			};
		setHeaderButton(ResourceLocation.BUTTON_HEADER_RIGHT, imageResource,
				"", onClickListener);

		return ResourceLocation.BUTTON_HEADER_RIGHT;
	}

	/**
	 * 设置标题头部右则按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public int setImagenTextHeaderRightButton(int imageResource, String text,
			OnClickListener onClickListener) {
		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					BaseActivity.this.finish();
				}

			};

		com.market.view.ButtonWithImage header_abtn = (com.market.view.ButtonWithImage) this
				.findViewById(ResourceLocation.BUTTON_HEADER_RIGHT);
		header_abtn.setSiblingImage(this.getResources().getDrawable(
				imageResource));
		if (text.length() < 1)
			header_abtn.setText(" ");
		else
			header_abtn.setText(text);
		header_abtn.setOnClickListener(onClickListener);
		header_abtn.setVisibility(View.VISIBLE);
		return ResourceLocation.BUTTON_HEADER_RIGHT;
	}

	protected void showMessageToast(String message) {
		if (toastMessage == null) {
			toastMessage = Toast.makeText(this, message, Toast.LENGTH_SHORT);
		} else
			toastMessage.setText(message);
		toastMessage.show();
	}

	private Dialog createProcessDialog(String msg) {
		loadingDialog = UnitTools.createLoadingDialog(this, msg);
		return loadingDialog;
	}

	protected void showProcessDialog() {
		if (loadingDialog == null)
			loadingDialog = createProcessDialog(null);
		if (loadingDialog != null) {
			if (loadingDialog.isShowing()) {
				needProcessBar_count++;
				return;
			}
			loadingDialog.show();
			needProcessBar_count++;
		}
	}

	protected void showProcessDialog(String msg) {
		if (loadingDialog == null)
			loadingDialog = createProcessDialog(msg);
		if (loadingDialog != null) {
			if (loadingDialog.isShowing()) {
				needProcessBar_count++;
				return;
			}
			loadingDialog.show();
			needProcessBar_count++;
		}
	}

	protected void closeProcessDialog() {
		if (loadingDialog != null && needProcessBar_count > 0) {
			if (loadingDialog.isShowing())
				if (--needProcessBar_count == 0)
					loadingDialog.cancel();
		}
	}

	protected void forceCloseProcessDialog() {
		if (loadingDialog != null) {
			if (loadingDialog.isShowing())
				loadingDialog.cancel();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d(" onStop ");
		this.forceCloseProcessDialog();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(" onDestroy ");
		this.forceCloseProcessDialog();
		super.onDestroy();

	}
	
	protected void ShowToastImageCorrect(String message)
	{
		UnitTools.createMassgeToast(this,message, 0);
	}

	protected void ShowToastImage(String message, int imgResourceId)
	{
		UnitTools.createMassgeToast(this, message, imgResourceId);
	}

}
