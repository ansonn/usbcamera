package com.market;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.market.bean.UpdateInfo;
import com.market.bean.User;
import com.market.dbmanage.SharePrefrencesUtils;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;
import com.market.view.CircleImageView;

public class PersonFragment extends BaseFragment implements OnClickListener,
		OnHttpCallBack {
	private Button btnTitle;
	private User userInfo;
	private SharePrefrencesUtils spPrefrencesUtils;
	private CircleImageView civ_head;
	private BitmapUtils bitmapUtils;
	private TextView tvNickName;
	private LinearLayout llAccount;
	private LinearLayout llOrderQuery;
	private LinearLayout llCoupon;
	private LinearLayout llRefuseQuery;
	private LinearLayout llArround;
	private LinearLayout llMyAttention;
	private LinearLayout llMyReview;
	private LinearLayout llFeedback;
	private LinearLayout llAbount;
	private LinearLayout llUpdate;
	private ImageView ivUpdateIcon;
	private int version = 1;
	private AlertDialog alertDialog;
	private HttpUtils httpUtils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.person_fragment, container);

		llAccount = (LinearLayout) view.findViewById(R.id.ll_account);
		llOrderQuery = (LinearLayout) view.findViewById(R.id.ll_order_query);
		llCoupon = (LinearLayout) view.findViewById(R.id.ll_coupon);
		llRefuseQuery = (LinearLayout) view.findViewById(R.id.ll_refuse_query);
		llArround = (LinearLayout) view.findViewById(R.id.ll_arround);
		llMyAttention = (LinearLayout) view.findViewById(R.id.ll_my_attention);
		llMyReview = (LinearLayout) view.findViewById(R.id.ll_review);
		llFeedback = (LinearLayout) view.findViewById(R.id.ll_feedback);
		llAbount = (LinearLayout) view.findViewById(R.id.ll_my_evaluate);
		llUpdate = (LinearLayout) view.findViewById(R.id.ll_update);
		ivUpdateIcon = (ImageView) view.findViewById(R.id.iv_update_icon);
		llAccount.setOnClickListener(this);
		llOrderQuery.setOnClickListener(this);
		llCoupon.setOnClickListener(this);
		llRefuseQuery.setOnClickListener(this);
		llArround.setOnClickListener(this);
		llMyAttention.setOnClickListener(this);
		llMyReview.setOnClickListener(this);
		llFeedback.setOnClickListener(this);
		llAbount.setOnClickListener(this);
		llUpdate.setOnClickListener(this);
		// 设置标题
		btnTitle = (Button) view.findViewById(R.id.common_header_btn_center);
		civ_head = (CircleImageView) view.findViewById(R.id.iv_head);
		tvNickName = (TextView) view.findViewById(R.id.nickName);

		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.person_title);

		bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadingImage(R.drawable.loading);
		bitmapUtils.configDefaultLoadingImage(R.drawable.loading);

		spPrefrencesUtils = new SharePrefrencesUtils(getActivity());
		userInfo = spPrefrencesUtils.getLoginedUserInfo();

		if (userInfo != null) {
			bitmapUtils.display(container, userInfo.getAvatar());
			tvNickName.setText(userInfo.getNickname());
		}

		setImagenTextHeaderRightButton(view, R.drawable.log_out,
				getString(R.string.logout), new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});

		try {
			PackageInfo pi;
			pi = getActivity().getPackageManager().getPackageInfo(
					getActivity().getPackageName(), 0);
			version = pi.versionCode;

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httpUtils = new HttpUtils();

		return view;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		if (v == llAccount) {
			// 帐号与安全
		} else if (v == llArround) {
			// 附近的人
		} else if (v == llMyAttention) {
			// 我的关注
			intent = new Intent(); 
			intent.setClass(getActivity(), MyFocusActivity.class);
			startActivity(intent);
		}
		else if (v == llRefuseQuery) {
			intent = new Intent();
			intent.setClass(getActivity(), RefundListActivity.class);
			startActivity(intent);
			// 退款查询
		} else if (v == llCoupon) {
			// 优惠券
			intent = new Intent();
			intent.setClass(getActivity(), VoucherListActivity.class);
			startActivity(intent);
		} else if (v == llOrderQuery) {
			// 订单查询
			intent = new Intent();
			intent.setClass(getActivity(), OrderListActivity.class);
			startActivity(intent);
		} else if (v == llMyAttention) {
			// 我的关注
		} else if (v == llAbount) {
			// 关于我们
			intent = new Intent();
			intent.setClass(getActivity(), AboutUsActivity.class);
			startActivity(intent);
		} else if (v == llFeedback) {
			// 意见建议
			intent = new Intent();
			intent.setClass(getActivity(), FeedBackActivity.class);
			startActivity(intent);
		} else if (v == llUpdate) {
			// 检查更新
			Animation animation = AnimationUtils.loadAnimation(getActivity(),
					R.anim.update_loading_progressbar_anim);
			animation.setRepeatCount(-1);
			animation.setInterpolator(new LinearInterpolator());
			ivUpdateIcon.startAnimation(animation);

			HttpComm.checkUpdate(version, this,
					Constants.CALLBACK_FLAG_CHECK_UPDATE, getActivity());

		} else if (v == llMyReview) {
			// 我的评价
		}

	}

	@Override
	protected void isShowtoUser(String latest) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		if (requestId == Constants.CALLBACK_FLAG_CHECK_UPDATE) {
			ivUpdateIcon.getAnimation().cancel();
			Log.i("test", "update_result=" + httpResult.getResult());
			try {
				UpdateInfo updateInfo = JsonToBean.json2UpdateInfo(httpResult
						.getResult());
				if (updateInfo.getVersion_code() > version) {
					// 有最新版本
					nofityUpdate(updateInfo);
				} else {
					showMessageToast(getString(R.string.is_newest_version));
				}
			} catch (Exception e) {
				showMessageToast(httpResult.getResult());

			}
		}

	}

	private void nofityUpdate(final UpdateInfo updateInfo) {
		if (alertDialog == null) {
			alertDialog = new AlertDialog.Builder(getActivity())
					.setTitle(R.string.nofity)
					.setMessage(
							getString(R.string.find_newest,
									updateInfo.getContent()))
					.setPositiveButton(R.string.update,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										final DialogInterface dialog, int which) {
									String url = updateInfo.getUrl();
									if (url == null) {
										url = updateInfo.getMarket_url();
									}
									httpUtils.download(url,
											"/mnt/sdcard/market_new.apk",
											new RequestCallBack<File>() {

												@Override
												public void onSuccess(
														ResponseInfo<File> arg0) {
													Intent intent = new Intent(
															Intent.ACTION_VIEW);
													intent.setDataAndType(
															Uri.fromFile(new File(
																	"/mnt/sdcard/market_new.apk")),
															"application/vnd.android.package-archive");
													getActivity()
															.startActivity(
																	intent);
													dialog.cancel();
												}

												@Override
												public void onFailure(
														HttpException arg0,
														String arg1) {

													showMessageToast(getString(R.string.update_fail));
													dialog.cancel();
												}
											});

								}
							})
					.setNegativeButton(R.string.cancle,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							}).create();
		} else {
			alertDialog.dismiss();
		}
		alertDialog.show();
	}
}
