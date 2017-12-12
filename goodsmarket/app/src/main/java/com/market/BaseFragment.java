package com.market;

import com.market.utils.Constants;
import com.market.utils.ResourceLocation;
import com.market.utils.UnitTools;

import de.greenrobot.event.EventBus;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

	protected Dialog loadingDialog;
	private int needProcessBar_count = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		EventBus.getDefault().register(this,"isShowtoUser",String.class);

	}

	private Dialog createProcessDialog(String msg) {
		loadingDialog = UnitTools.createLoadingDialog(getActivity(), msg);
		loadingDialog.setCancelable(true);
		return loadingDialog;
	}
	
	protected abstract void isShowtoUser(String latest);

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

	/**
	 * 设置标题头部按扭
	 * 
	 * @param title
	 * @param onClicklistener
	 * @author pumkid
	 */
	public void setHeaderButton(View containerView, final int btn_id,
			final int imageBg, String title, OnClickListener onClicklistener) {
		Button header_abtn = (Button) containerView.findViewById(btn_id);
		if (header_abtn != null) {
			header_abtn.setVisibility(View.VISIBLE);
			header_abtn.setText(title);
			if (imageBg > 0)
				header_abtn.setBackgroundResource(imageBg);
			header_abtn.setOnClickListener(onClicklistener);
		}
	}

	/**
	 * 设置标题返回事件头部按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public void setDeftualtHeaderLeftButton(View containerView) {
		setHeaderButton(containerView,ResourceLocation.BUTTON_HEADER_LEFT,
				ResourceLocation.IMG_HEADER_BACKICON, Constants.TITLEEMPTY,
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						getActivity().finish();
					}

				});
	}

	/**
	 * 设置标题返回事件头部按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public void setDeftualtHeaderLeftButton(View containerView,OnClickListener onCliclistener) {
		setHeaderButton(containerView,ResourceLocation.BUTTON_HEADER_LEFT,
				ResourceLocation.IMG_HEADER_BACKICON, Constants.TITLEEMPTY,
				onCliclistener);
	}

	/**
	 * 设置标题头部右则按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public int setStringHeaderRightButton(View containerView,String oprationName,
			OnClickListener onClickListener) {

		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}

			};
		setHeaderButton(containerView,ResourceLocation.BUTTON_HEADER_RIGHT,
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
	public int setImageHeaderRightButton(View containerView,int imageResource,
			OnClickListener onClickListener) {

		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}

			};
		setHeaderButton(containerView,ResourceLocation.BUTTON_HEADER_RIGHT, imageResource,
				"", onClickListener);

		return ResourceLocation.BUTTON_HEADER_RIGHT;
	}

	/**
	 * 设置标题头部右则按扭
	 * 
	 * @param title
	 * @author pumkid
	 */
	public int setImagenTextHeaderRightButton(View containerVier,
			int imageResource, String text, OnClickListener onClickListener) {
		if (onClickListener == null)
			onClickListener = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					getActivity().finish();
				}

			};

		com.market.view.ButtonWithImage header_abtn = (com.market.view.ButtonWithImage) containerVier
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

	protected Toast toastMessage;
	protected void showMessageToast(String message) {
		if (toastMessage == null) {
			toastMessage = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
		} else
			toastMessage.setText(message);
		toastMessage.show();
	}
}
