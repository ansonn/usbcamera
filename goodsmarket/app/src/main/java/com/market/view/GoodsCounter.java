package com.market.view;

import org.json.JSONException;
import org.json.JSONObject;

import com.haoyikuai.shop.android.R;
import com.market.BaseActivity;
import com.market.bean.SkuBean;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.CartHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.UnitTools;

import android.app.Service;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

;

/**
 * Goods' quantity counter
 * 
 * @author pumkid
 *
 */
public class GoodsCounter extends LinearLayout implements OnClickListener,
		OnHttpCallBack {
	private Context context;
	private int maxCount = 99999, minCount = 1;
	private ImageButton minutesBtn;
	private ImageButton plusBtn;
	private TextView tv_cartgoodsmpnum;
	private TextView tv_cartgoodsprice;
	private int counter;
	private int percounter;
	private int initCounter = 1;
	private SkuBean skubean;
	private TextView tv_cartgoodsinputTips;
	private OnCounterChangeListener onCounterChangeListener;
	private CounterType counterType;

	public enum CounterType {
		type_cart, type_dialog
	}

	public void setCounterType(CounterType counterType) {
		this.counterType = counterType;
	}

	public GoodsCounter(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		LayoutInflater layoutinflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		View view = layoutinflater.inflate(R.layout.common_counter, null);
		initView(view);
		this.addView(view);
	}

	private void initView(View view) {
		minutesBtn = (ImageButton) view.findViewById(R.id.btn_cartgoodsminutes);
		tv_cartgoodsmpnum = (TextView) view
				.findViewById(R.id.tv_cartgoodsmpnum);
		plusBtn = (ImageButton) view.findViewById(R.id.btn_cartgoodsplus);
		tv_cartgoodsinputTips = (TextView) view
				.findViewById(R.id.tv_cartgoodsinputTips);
		tv_cartgoodsprice = (TextView)view.findViewById(R.id.tv_cartgoodsprice);
		plusBtn.setOnClickListener(this);
		minutesBtn.setOnClickListener(this);
		plusBtn.setOnClickListener(this);

		
	}

	public int getCount() {
		return counter;
	}

	public void setMax(int maxCount) {
		this.maxCount = maxCount;
		if (maxCount == 0) {
			this.minCount = maxCount;
			this.setEnable(false);
		} else {
			this.minCount = 1;
			this.setEnable(true);
		}
	}

	public void setMaxAndMin(int maxCount, int minCount) {
		this.maxCount = maxCount;
		this.minCount = minCount;
		if (minCount <= 0 || maxCount <= 0 || maxCount <= minCount) {
			this.setEnable(false);
		}

	}

	public void setInitCount(int initCount) {
		this.initCounter = initCount;
		counter = initCounter;
		tv_cartgoodsmpnum.setText(this.initCounter + "");
	}

	public void setInitSkuBean(SkuBean skubean) {
		if (skubean != null) {
			this.skubean = skubean;
			setInitCount(skubean.getNumber() == null ? 1 : skubean.getNumber());
			setMax(skubean.getStore_num());
			setSotre(skubean.getStore_num());
			if(counterType == CounterType.type_cart)
			{
				tv_cartgoodsprice.setText("гд"+ UnitTools.formatDoubleTagTail2(skubean.getReal_price()));
			}
			
		}
	}
	
	public void setGoodspriceVisibility(int visibility) {
				tv_cartgoodsprice.setVisibility(visibility);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_cartgoodsminutes:
			counterMinutes();
			break;
		case R.id.tv_cartgoodsmpnum:
			break;
		case R.id.btn_cartgoodsplus:
			counterPlus();
			break;
		}
	}

	private void counterPlus() {
		if (onCounterChangeListener != null)
			onCounterChangeListener.onCounterPreChange();
		if (this.counterType == CounterType.type_cart) {
			if (skubean == null)
				return;
			percounter = counter;
			++counter;
			this.setEnable(false);
			CartHttpOperation.public_changeNumberInCart(skubean.getSku_id(),
					counter, this,
					Constants.CALLBACK_FLAG_GOODSINCARTAMOUNTCHANGE, context);
		}
		if (this.counterType == CounterType.type_dialog) {
			if (counter >= maxCount)
				return;
			counter = Integer.parseInt(tv_cartgoodsmpnum.getText().toString());
			counter++;
			tv_cartgoodsmpnum.setText(counter + "");
			if (onCounterChangeListener != null)
				onCounterChangeListener.onCounterChange(counter, 1);
		}
	}

	private void counterMinutes() {
		if (onCounterChangeListener != null)
			onCounterChangeListener.onCounterPreChange();
		if (this.counterType == CounterType.type_cart) {
			if (skubean == null)
				return;
			percounter = counter;
			counter--;
			this.setEnable(false);
			CartHttpOperation.public_changeNumberInCart(skubean.getSku_id(),
					counter, this,
					Constants.CALLBACK_FLAG_GOODSINCARTAMOUNTCHANGE, context);
		}
		if (this.counterType == CounterType.type_dialog) {
			if (counter <= minCount)
				return;
			counter = Integer.parseInt(tv_cartgoodsmpnum.getText().toString());
			counter--;
			tv_cartgoodsmpnum.setText(counter + "");
			if (onCounterChangeListener != null)
				onCounterChangeListener.onCounterChange(counter, -1);
			skubean.setNumber(counter);
		}

	}

	public void setSotre(int sore) {
		if (sore > 0)
			tv_cartgoodsinputTips.setText(context.getString(
					R.string.counter_store, sore));
		else
			tv_cartgoodsinputTips.setVisibility(View.GONE);
	}

	public interface OnCounterChangeListener {
		public void onCounterChange(int count, int operator);

		public void onCounterPreChange();

		public void onCounterChanged(HttpResult httpResult);
	}

	public OnCounterChangeListener getOnCounterChangeListener() {
		return onCounterChangeListener;
	}

	public void setOnCounterChangeListener(
			OnCounterChangeListener onCounterChangeListener) {
		this.onCounterChangeListener = onCounterChangeListener;
	}

	public void setEnable(boolean enabled) {
		minutesBtn.setEnabled(enabled);
		tv_cartgoodsmpnum.setEnabled(enabled);
		plusBtn.setEnabled(enabled);
		if (enabled) {
			plusBtn.setImageResource(R.drawable.add);
			minutesBtn.setImageResource(R.drawable.minus_nomal);
		} else {
			plusBtn.setImageResource(R.drawable.add);
			minutesBtn.setImageResource(R.drawable.minus_unnomal);
		}
		minutesBtn.setClickable(enabled);
		plusBtn.setClickable(enabled);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (requestId == Constants.CALLBACK_FLAG_GOODSINCARTAMOUNTCHANGE) {
			this.setEnable(true);
			if (httpResult.isSuccess()) {
				try {
					JSONObject jsonObject = new JSONObject(
							httpResult.getResult());
					if (jsonObject.has("is_over_than")) {
						Boolean isoverthan = jsonObject
								.getBoolean("is_over_than");
						if (!isoverthan) {
							if (onCounterChangeListener != null) {
								onCounterChangeListener.onCounterChange(
										counter, 1);
								
								
								onCounterChangeListener.onCounterChanged(httpResult);
							}

						} else
							counter = percounter;
						tv_cartgoodsmpnum.setText(counter + "");
						if(skubean != null)
						skubean.setNumber(counter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
