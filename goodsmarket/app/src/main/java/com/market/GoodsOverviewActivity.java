package com.market;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.GoodsInfo;
import com.market.bean.GoodsInfoBean;
import com.market.bean.GoodsoverviewInfo;
import com.market.bean.ImageURLBean;
import com.market.bean.SkuBean;
import com.market.bean.Spec;
import com.market.bean.ValueBean;
import com.market.dbmanage.DBFileManage;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.FavoriteHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.Util;
import com.market.view.DimenChooseDialog;
import com.market.view.DimenChooseDialog.OnSpecSelectedListener;
import com.market.view.GoodsImageView;
import com.market.view.GoodsPurchasedMenu;
import com.market.view.GoodsPurchasedMenu.OnPurchasingClick;
import com.market.view.SlidingMenu;
import com.market.view.SlidingMenu.OnMenuBtnClickListener;

public class GoodsOverviewActivity extends BaseActivity implements
		OnHttpCallBack, OnClickListener, OnSpecSelectedListener,
		OnMenuBtnClickListener {
	private TextView tv_oldprice;
	private TextView tv_goodsov_info_name;
	private TextView tv_goodsov_info_dec;
	private TextView tv_newprice;
	private TextView tv_goodspromotion;
	private TextView tv_goodssize;
	private TextView tv_goodsov_comment;
	private ScrollView sc;
	private GoodsInfo goodsInfo;
	private GoodsInfoBean gf;
	private GoodsImageView giv_goodsView;
	private GoodsoverviewInfo gof;
	private DimenChooseDialog mdimenChooseDialog;
	private SkuBean currentskuBean;
	private DBFileManage dbFileManage;
	private int currentCount;

	private SlidingMenu slidingMenu;

	private long goodId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LayoutInflater lf = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout mainview = (RelativeLayout) lf.inflate(
				R.layout.activity_goodsoverview, null);
		this.setContentView(mainview);
		dbFileManage = new DBFileManage(this);
		super.onCreate(savedInstanceState);

		initView(mainview);
		initTitle();

		goodsInfo = (GoodsInfo) getIntent().getSerializableExtra(
				Constants.OBJECTEXTRA);

		this.showProcessDialog();
		if (goodsInfo == null) {
			goodId = getIntent().getLongExtra(Constants.GOODS_ID, 44);
		} else {
			goodId = goodsInfo.getId();
		}
		HttpComm.getGoodsInfo(goodId + "", this,
				Constants.CALLBACK_FLAG_GET_GOODSINOF, this);
	}

	private void initView(RelativeLayout rl_main) {

		tv_goodsov_info_name = (TextView) this
				.findViewById(R.id.tv_goodsov_info_name);
		tv_goodsov_info_dec = (TextView) this
				.findViewById(R.id.tv_goodsov_info_dec);
		tv_newprice = (TextView) this.findViewById(R.id.tv_newprice);
		tv_goodspromotion = (TextView) this
				.findViewById(R.id.tv_goodspromotion);
		tv_goodssize = (TextView) this.findViewById(R.id.tv_goodssize);
		tv_goodsov_comment = (TextView) this
				.findViewById(R.id.tv_goodsov_comment);
		tv_oldprice = (TextView) this.findViewById(R.id.tv_oldprice);
		giv_goodsView = (GoodsImageView) this.findViewById(R.id.giv_goodsView);
		this.findViewById(R.id.ll_goodsvo_comment).setOnClickListener(this);
		tv_oldprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);

		slidingMenu = (SlidingMenu) findViewById(R.id.slideMenu);
		slidingMenu.setOnMenuBtnClickListener(this);
		initDialog(rl_main);

	}

	private void initDialog(RelativeLayout rl_main) {

		int windowHeight = this.getWindowManager().getDefaultDisplay()
				.getHeight();
		int menuHeight = windowHeight / 12;
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, menuHeight);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		GoodsPurchasedMenu gm = new GoodsPurchasedMenu(this);

		gm.setOnPurchasingClick(new OnPurchasingClick() {

			@Override
			public void onAddtoCart() {
				// TODO Auto-generated method stub
				if (gof != null) {
					if (mdimenChooseDialog == null)
						mdimenChooseDialog = new DimenChooseDialog(
								GoodsOverviewActivity.this, gof, dbFileManage);
					mdimenChooseDialog.setCurrentSkuBean(currentskuBean);
					mdimenChooseDialog
							.setOnSpecSelectedListener(GoodsOverviewActivity.this);
					mdimenChooseDialog.show();
				}
			}

			@Override
			public void onTakeCare() {
				if (gof != null) {
					FavoriteHttpOperation.addFavorite(gof.getId().longValue(),
							Constants.CALLBACK_FLAG_ADDTOMYFAVORITE,
							GoodsOverviewActivity.this,
							GoodsOverviewActivity.this);
					GoodsOverviewActivity.this.showProcessDialog();
				}
			}

			@Override
			public void onCheckout() {
				// TODO Auto-generated method stub
				showMessageToast("onCheckout");
			}
		});
		View view = gm.createView(rllp, null);
		rl_main.addView(view);
		this.findViewById(R.id.ll_goodsovdimenchoosed).setOnClickListener(this);

	}

	private void initData(GoodsInfoBean goodsInfoBean) {
		gof = goodsInfoBean.getGoods_info();
		if (gof == null)
			return;
		initGoodsImageHeader(gof.getImages());
		tv_goodsov_info_name.setText(gof.getTitle());
		tv_goodsov_info_dec.setText(gof.getDescription());
		tv_newprice.setText("￥" + gof.getSell_price());
		tv_goodspromotion.setText(gof.getProm() == null ? getResources()
				.getString(R.string.none) : gof.getProm().getName());
		tv_oldprice.setText("￥" + gof.getMarket_price());
		tv_goodsov_comment.setText(getResources().getString(
				R.string.goodsov_comment, gof.getComment_count()));
		if (gof.getSku_list() != null
				&& gof.getSku_list().getSku_list() != null
				&& gof.getSku_list().getSku_list().length > 0) {
			this.currentskuBean = gof.getSku_list().getSku_list()[0];
			tv_goodssize.setText(skuBeannCount2String(currentskuBean,
					gof.getSpec(), 1));
			currentCount = 1;
		}
	}

	private void initGoodsImageHeader(ImageURLBean[] imbs) {
		if (imbs == null)
			return;
		String[] adPciture = new String[imbs.length];
		for (int i = 0; i < adPciture.length; i++) {
			adPciture[i] = imbs[i].getUrl();
		}
		giv_goodsView.setPageImages(adPciture);
	}

	private void initTitle() {
		super.setActivityTitle(this.getResources().getString(
				R.string.goodsov_title));
		super.setDeftualtHeaderLeftButton();
		super.setImagenTextHeaderRightButton(R.drawable.share, "分享", this);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (requestId == Constants.CALLBACK_FLAG_GET_GOODSINOF) {
			if (httpResult.isSuccess()) {
				gf = JsonToBean.json2GoodsInfoBean(httpResult.getResult());
				initData(gf);
			}
		} else if (requestId == Constants.CALLBACK_FLAG_ADDTOMYFAVORITE) {
			if (httpResult.isSuccess()) {
				ShowToastImageCorrect("已关注");
			}
		}

		this.closeProcessDialog();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.ll_goodsovdimenchoosed:
			if (gof != null) {
				if (mdimenChooseDialog == null)
					mdimenChooseDialog = new DimenChooseDialog(this, gof,
							dbFileManage);
				mdimenChooseDialog.setCurrentSkuBean(currentskuBean);
				mdimenChooseDialog.setOnSpecSelectedListener(this);
				mdimenChooseDialog.show();
			}
			break;
		case R.id.ll_goodsvo_comment:
			if (gof != null && gof.getComment_count().intValue() < 1)
				return;
			Intent intent = new Intent();
			intent.setClass(this, GoodsCommentActivity.class);
			intent.putExtra(Constants.LONGEXTRA, gof.getId());
			this.startActivity(intent);
			break;
		}
	}

	@Override
	public void onSpecSelected(SkuBean skubean, Spec[] spec, int count) {
		// TODO Auto-generated method stub
		if (skubean != null && spec != null) {
			tv_goodssize.setText(skuBeannCount2String(skubean, spec, count));
			currentskuBean = skubean;
			currentCount = count;
		} else {
			tv_goodssize.setText("");
			currentskuBean = null;
			currentCount = 0;
		}
	}

	private String skuBean2String(SkuBean skubean, Spec[] spec) {
		int accumulater = 0;
		Long tempInt = 0l;
		ArrayList<Long> arrInt = new ArrayList<Long>();
		StringBuilder sb = new StringBuilder();
		String keystr = skubean.getSpec_key();
		if (keystr != null)
			for (int i = 0; i < keystr.length(); i++) {
				char character = keystr.charAt(i);
				if (Util.isNumber(character)) {
					tempInt = (long) (tempInt * Math.pow(10, accumulater))
							+ character - '0';
					accumulater++;
				} else {
					if (accumulater > 0)
						arrInt.add(tempInt);
					tempInt = 0l;
					accumulater = 0;
				}
			}
		for (int i = 0; i < arrInt.size(); i++) {
			if (i % 2 == 0) {
				for (Spec specbean : spec) {
					if (specbean.getSpec_id().equals(arrInt.get(i))) {
						ValueBean[] vbs = specbean.getValue();
						for (ValueBean vb : vbs) {
							if (arrInt.size() > i + 1
									&& vb.getSpec_value_id().equals(
											(arrInt.get(i + 1)))) {
								Log.d(vb.getName());
								sb.append(vb.getName() + " ");
								i++;
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}

	private String skuBeannCount2String(SkuBean skubean, Spec[] spec, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(skuBean2String(skubean, spec));
		sb.append(" ");
		sb.append(count);
		sb.append("件");
		return sb.toString();
	}

	@Override
	public void onButton1Click() {
		// 好友

	}

	@Override
	public void onButton2Click() {
		// 客服

	}

}
