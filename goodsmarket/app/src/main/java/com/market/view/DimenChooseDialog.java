package com.market.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.adapter.SpecListAdapter;
import com.market.bean.GoodsoverviewInfo;
import com.market.bean.SkuBean;
import com.market.bean.Sku_list;
import com.market.bean.Spec;
import com.market.bean.ValueBean;
import com.market.dbmanage.DBFileManage;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.CartHttpOperation;
import com.market.utils.CartOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.CartOperation.OnOperateCartListener;
import com.market.utils.UnitTools;
import com.market.view.GoodsCounter.CounterType;
import com.market.view.GoodsCounter.OnCounterChangeListener;
import com.market.view.GoodsSpecChoosedLayout.OnSpecItemSelcetListener;

public class DimenChooseDialog extends Dialog implements
		android.view.View.OnClickListener, OnSpecItemSelcetListener,
		OnCounterChangeListener, OnHttpCallBack {
	private DBFileManage dBFileManage;
	private WindowManager mwindowmanager;
	private GoodsoverviewInfo goodsoverviewInfo;
	private Context context;
	private Resources resource;
	private Paint paint;
	private Paint paintArc;
	private Paint paintbg;
	private int goodsov_dialog_border;
	private SpecListAdapter mSpecListAdapter;
	private Button btn_goodsaddtocart;
	private ImageView iv_dialogGoodsImage;
	private TextView tv_dialognewprice;
	private TextView tv_dialogGoodsTitle;
	private BitmapUtils bitmapUtils;
	private GoodsCounter gsc_counter;
	private int currentCount = 1;
	private Double currentPrice;
	private Long currentSkuid;
	private SkuBean currentSkuBean;
	private SkuBean showSkuBean;

	private SkuBean[] skbarr;
	private OnSpecSelectedListener onSpecSelectedListener;

	private OnDimenSelcetedListener onDimenSelcetedListener;
	private ValueBean[] skuidArr;

	public DimenChooseDialog(Context context,
			GoodsoverviewInfo goodsoverviewInfo, DBFileManage dBFileManage) {
		super(context, R.style.FullHeightDialog);
		this.goodsoverviewInfo = goodsoverviewInfo;
		this.context = context;
		this.dBFileManage = dBFileManage;
		init();
	}

	public DimenChooseDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		init();
	}

	private void init() {
		resource = context.getResources();
		goodsov_dialog_border = (int) this.resource
				.getDimension(R.dimen.goodsov_dialog_border);
		if (paint == null) {
			paint = new Paint();
			paint.setColor(this.resource.getColor(R.color.app_theme_red));
			paint.setFlags(Paint.ANTI_ALIAS_FLAG);
			paint.setStyle(Style.STROKE);
			paint.setStrokeWidth(goodsov_dialog_border);
		}
		if (paintArc == null) {
			paintArc = new Paint();
			paintArc.setColor(this.resource.getColor(R.color.app_theme_red));
			paintArc.setFlags(Paint.ANTI_ALIAS_FLAG);
			paintArc.setStyle(Style.FILL);
		}
		if (paintbg == null) {
			paintbg = new Paint();
			paintbg.setColor(Color.WHITE);
			paintbg.setFlags(Paint.ANTI_ALIAS_FLAG);
			paintbg.setStyle(Style.FILL);
		}
		bitmapUtils = new BitmapUtils(context);
		mwindowmanager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_goodsovdimenchoosed);
		ListView ll_dialog_dimens = (ListView) this
				.findViewById(R.id.scv_dialog_dimenslist);
		if (goodsoverviewInfo != null) {
			initWindow();
	
			ll_dialog_dimens.setAdapter(mSpecListAdapter);
		}
		/**
		 * 设置dialog为整个屏幕的2/3
		 */
		if (this.mwindowmanager != null) {
			Display display = mwindowmanager.getDefaultDisplay();
			WindowManager.LayoutParams lp = this.getWindow().getAttributes();
			lp.width = (int) (display.getWidth());
			lp.height = (display.getHeight() / 3) << 1;
			getWindow().setAttributes(lp);
			getWindow().setGravity(Gravity.BOTTOM);
			getWindow().setWindowAnimations(R.style.Dialog_innout);
		}
	}

	private void initWindow() {

		btn_goodsaddtocart = (Button) this
				.findViewById(R.id.btn_goodsaddtocart);
		btn_goodsaddtocart.setOnClickListener(this);
		iv_dialogGoodsImage = (ImageView) this
				.findViewById(R.id.iv_dialogGoodsImage);
		tv_dialognewprice = (TextView) this
				.findViewById(R.id.tv_dialognewprice);
		tv_dialogGoodsTitle = (TextView) this
				.findViewById(R.id.tv_dialogGoodsTitle);
		btn_goodsaddtocart.setOnClickListener(this);
		gsc_counter = (GoodsCounter) this.findViewById(R.id.gsc_counter);
		gsc_counter.setCounterType(CounterType.type_dialog);
		gsc_counter.setGoodspriceVisibility(View.GONE);
		btn_goodsaddtocart.setEnabled(false);
		skbarr = getSkuBean();
		mSpecListAdapter = new SpecListAdapter(context,goodsoverviewInfo.getSku_list(),
				goodsoverviewInfo.getSpec(), this);
		// 默认选中第一个
		if (skbarr != null) {
			this.currentPrice = skbarr[0].getMarket_price();
			this.currentSkuid = skbarr[0].getSku_id();
			this.currentSkuBean = skbarr[0];
			bitmapUtils.display(iv_dialogGoodsImage, skbarr[0].getImage());
			gsc_counter.setInitSkuBean(skbarr[0]);
			if (onSpecSelectedListener != null)
				onSpecSelectedListener.onSpecSelected(currentSkuBean,
						goodsoverviewInfo.getSpec(), gsc_counter.getCount());
			setPrice(tv_dialognewprice, currentPrice);
			setAbletoAddingtoCart(true);
			mSpecListAdapter.setSelectedSpec(currentSkuBean.getSpec_key());
		}
		 else {
			if (goodsoverviewInfo.getImages() != null
					&& goodsoverviewInfo.getImages().length > 0)
				bitmapUtils.display(iv_dialogGoodsImage,
						goodsoverviewInfo.getImages()[0].getUrl());
			setAbletoAddingtoCart(false);
		}
		gsc_counter.setInitCount(this.currentCount);

		gsc_counter.setSotre(goodsoverviewInfo.getStore_num());
		gsc_counter.setOnCounterChangeListener(this);
		tv_dialogGoodsTitle.setText(goodsoverviewInfo.getTitle());

	}

	private void setAbletoAddingtoCart(boolean able) {

		gsc_counter.setEnable(able);
		btn_goodsaddtocart.setEnabled(able);
	}

	private SkuBean[] getSkuBean() {
		if (goodsoverviewInfo == null)
			return null;
		if (goodsoverviewInfo.getSku_list() != null) {
			if (goodsoverviewInfo.getSku_list().getSku_list() != null)
				return goodsoverviewInfo.getSku_list().getSku_list();
		}
		return null;
	}

	public interface OnDimenSelcetedListener {
		public void onSelected(Object tag);
	}

	public void setOnDimenSelcetedListener(
			OnDimenSelcetedListener onDimenSelcetedListener) {
		this.onDimenSelcetedListener = onDimenSelcetedListener;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_goodsaddtocart:
			if (currentSkuBean != null)
				// CartOperation.addToCart(dBFileManage, currentSkuBean,
				// this.currentCount , this, Constants.CALLBACK_FLAG_ADDTOCART,
				// context, this);
				CartHttpOperation.public_addToCart(currentSkuBean.getSku_id(),
						currentCount, this, Constants.CALLBACK_FLAG_ADDTOCART,
						context);
			break;
		}
	}

	
	@Override
	public void OnSpecItemInit(View view, Spec spec, ValueBean valueposition,
			int position, int totalArryLength, int viewPosition) {
		// TODO Auto-generated method stub
		if (skuidArr == null) {
			skuidArr = new ValueBean[totalArryLength];
			for (int i = 0; i < skuidArr.length; i++)
				skuidArr[i] = null;
		}
		if (position >= 0)
			skuidArr[viewPosition] = valueposition;
		else
			skuidArr[viewPosition] = null;
		String skuidlist = Long2SkuIdList(skuidArr);
		SkuBean[] skb = skbarr;
		matchSkuBean(skuidlist, skb);
	}
	Toast toast;

	@Override
	public void OnSpecItemSelceted(View view, Spec spec, ValueBean valueposition,
			int position, int totalArryLength, int viewPosition) {
		// TODO Auto-generated method stub
		if (skuidArr == null) {
			skuidArr = new ValueBean[totalArryLength];
			for (int i = 0; i < skuidArr.length; i++)
				skuidArr[i] = null;
		}
		if (position >= 0)
			skuidArr[viewPosition] = valueposition;
		else
			skuidArr[viewPosition] = null;
		
		
		String skuidlist = Long2SkuIdList(skuidArr);
		mSpecListAdapter.setSelectedSpec(skuidlist);
		mSpecListAdapter.notifyDataSetChanged();
		SkuBean[] skb = skbarr;
		matchSkuBean(skuidlist, skb);
	}
	
	private void matchSkuBean(String skuidlist, SkuBean[] skb)
	{
		if (skb != null) {
			setAbletoAddingtoCart(false);
			for (int i = 0; i < skb.length; i++) {
				this.currentPrice = null;
				this.currentSkuid = null;
				this.currentSkuBean = null;
				if (skb[i].getSpec_key().equals(skuidlist)) {
					this.currentPrice = skb[i].getMarket_price();
					this.currentSkuid = skb[i].getSku_id();
					this.currentSkuBean = skbarr[i];
					bitmapUtils.display(iv_dialogGoodsImage, skb[i].getImage());
					gsc_counter.setInitSkuBean(skb[i]);
					setPrice(tv_dialognewprice, currentPrice);
					setAbletoAddingtoCart(true);
					break;
				}
			}
			if (onSpecSelectedListener != null)
				onSpecSelectedListener.onSpecSelected(currentSkuBean,
						goodsoverviewInfo.getSpec(), gsc_counter.getCount());
		}
	}
	
	

	private String Long2SkuIdList(ValueBean[] positions) {
		if (positions != null) {
			StringBuilder skuIdsb = new StringBuilder();
			skuIdsb.append(";");
			for (int i = 0; i < positions.length; i++) {
				if (positions[i] != null) {
					skuIdsb.append(positions[i].getSpec_id());
					skuIdsb.append(":");
					skuIdsb.append(positions[i].getSpec_value_id());
				}
				skuIdsb.append(";");
			}
			return skuIdsb.toString();
		}
		return null;
	}
	

	@Override
	public void onCounterChange(int count, int operator) {
		this.currentCount = count;
		if (onSpecSelectedListener != null)
			onSpecSelectedListener.onSpecSelected(currentSkuBean,
					goodsoverviewInfo.getSpec(), gsc_counter.getCount());
	}

	public void setPrice(TextView tv_dialognewprice, double currentPrice) {
		tv_dialognewprice.setText("￥" + currentPrice);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (httpResult.isSuccess()) {
			if (requestId == Constants.CALLBACK_FLAG_ADDTOCART) {
				// 成功时回调
				Toast toast = UnitTools.createMassgeToast(context, context
						.getResources()
						.getString(R.string.cart_addgoodssuccess), 0);
				toast.show();
			}
		} else {
			Toast toast = UnitTools.createMassgeToast(context, context
					.getResources().getString(R.string.cart_addgoodsfail), 0);
			toast.show();
		}
	}

	@Override
	public void onCounterPreChange() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCounterChanged(HttpResult httpResult) {
		// TODO Auto-generated method stub

	}

	public void setOnSpecSelectedListener(
			OnSpecSelectedListener onSpecSelectedListener) {
		this.onSpecSelectedListener = onSpecSelectedListener;
	}

	public interface OnSpecSelectedListener {
		public void onSpecSelected(SkuBean skubean, Spec[] spec, int count);
	}

	public SkuBean getCurrentSkuBean() {
		return currentSkuBean;
	}

	public void setCurrentSkuBean(SkuBean currentSkuBean) {
		this.currentSkuBean = currentSkuBean;
	}

	

}
