package com.market.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;

import com.haoyikuai.shop.android.R;

public class GoodsPurchasedMenu implements OnClickListener {
	private Context context;
	private OnPurchasingClick onPurchasingClick;
	public GoodsPurchasedMenu(Context context) {
		this.context = context;
	}

	public View createView(LayoutParams lp,OnClickListener onClickListener) {
		if(context == null)
			return null;
		LayoutInflater lf = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = lf.inflate(R.layout.view_goodspurchasedmenu, null);
		view.setLayoutParams(lp);
		if(onClickListener == null)
			onClickListener = this;
		view.findViewById(R.id.btn_goodspmadd).setOnClickListener(onClickListener);
		view.findViewById(R.id.btn_goodspmtake).setOnClickListener(onClickListener);
		view.findViewById(R.id.btn_goodspmcheckout).setOnClickListener(onClickListener);
		return view;
	}

	public interface OnPurchasingClick {
		/**
		 * 加入购物车
		 */
		public void onAddtoCart();
		/**
		 * 关注
		 */
		public void onTakeCare();

		/**
		 * 前往购物车
		 */
		public void onCheckout();

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId())
		{
		case R.id.btn_goodspmadd:
			if(onPurchasingClick!=null)
				onPurchasingClick.onAddtoCart();
			break;
		case R.id.btn_goodspmtake:
			if(onPurchasingClick!=null)
				onPurchasingClick.onTakeCare();
			break;
		case R.id.btn_goodspmcheckout:
			if(onPurchasingClick!=null)
				onPurchasingClick.onCheckout();
			break;
		}
	}

	public OnPurchasingClick getOnPurchasingClick() {
		return onPurchasingClick;
	}

	public void setOnPurchasingClick(OnPurchasingClick onPurchasingClick) {
		this.onPurchasingClick = onPurchasingClick;
	}

	
}
