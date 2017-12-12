package com.market.adapter;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.market.BaseActivity;
import com.market.bean.SkuBean;
import com.market.bean.SkuListInfo;
import com.market.bean.Sku_list;
import com.market.dbmanage.DBFileManage;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.utils.CartOperation;
import com.market.utils.Log;
import com.market.utils.UnitTools;
import com.market.view.GoodsCounter;
import com.market.view.GoodsCounter.CounterType;
import com.market.view.GoodsCounter.OnCounterChangeListener;

public class CartListAdapter extends BaseAdapter implements OnCounterChangeListener, OnCheckedChangeListener {
	private SkuBean[] skuBeanArr;
	private Context context;
	private BitmapUtils bitmapUtils;
	private TextView pricetage;
	private DBFileManage dbFileManage;

	public CartListAdapter(Context context, SkuBean[] skuBeanArr
			,DBFileManage dbFileManage
			, TextView pricetage) {
		this.context = context;
		this.skuBeanArr = skuBeanArr;
		this.bitmapUtils = new BitmapUtils(context);
		this.pricetage = pricetage;
		this.dbFileManage = dbFileManage;
	}
	public CartListAdapter(Context context
			,DBFileManage dbFileManage
			) {
		this.context = context;
		this.bitmapUtils = new BitmapUtils(context);
		this.dbFileManage = dbFileManage;
	}

	public CartListAdapter(Context context) {
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (skuBeanArr != null)
			return skuBeanArr.length;
		return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if (skuBeanArr != null)
			return skuBeanArr[index];
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		SkuBean sb = skuBeanArr[index];
		if (sb == null)
			return null;
		View contentView = view;
		if (contentView == null) {
			LayoutInflater lf = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			contentView = lf.inflate(R.layout.item_cart, null);
		}
		ImageView img_cartgoodsimg = (ImageView) contentView
				.findViewById(R.id.img_cartgoodsimg);
		TextView tv_cartgoodsname = (TextView) contentView
				.findViewById(R.id.tv_cartgoodsname);
		TextView tv_cartgoodsid = (TextView) contentView
				.findViewById(R.id.tv_cartgoodsid);
		final GoodsCounter btn_cartgoodsmpnum = (GoodsCounter) contentView
				.findViewById(R.id.gc_itemcart);
		CheckBox cb_cartgoodsitemselector = (CheckBox)contentView.findViewById(R.id.cb_cartgoodsitemselector);
		
		cb_cartgoodsitemselector.setTag(sb);
		cb_cartgoodsitemselector.setOnCheckedChangeListener(this);
		
		cb_cartgoodsitemselector.setChecked(sb.getIsPurchased());
		
		btn_cartgoodsmpnum.setCounterType(CounterType.type_cart);
		btn_cartgoodsmpnum.setInitSkuBean(sb);
		btn_cartgoodsmpnum.setOnCounterChangeListener(this);
		TextView tv_cartgoodsodds = (TextView) contentView
				.findViewById(R.id.tv_cartgoodsodds);
		bitmapUtils.display(img_cartgoodsimg, sb.getImage(),
				new BitmapLoadCallBack<View>() {

					@Override
					public void onLoadCompleted(View arg0, String arg1,
							Bitmap arg2, BitmapDisplayConfig arg3,
							BitmapLoadFrom arg4) {
						// TODO Auto-generated method stub
						((ImageView) arg0).setImageBitmap(arg2);
					}

					@Override
					public void onLoadFailed(View arg0, String arg1,
							Drawable arg2) {
						// TODO Auto-generated method stub
						((ImageView) arg0).setImageResource(R.drawable.loading);
					}

				});
		tv_cartgoodsname.setText(sb.getTitle());
		tv_cartgoodsid.setText(CartOperation.getSpecStr(sb.getSpecbean()));
		if(sb.getProm_name() != null && !TextUtils.isEmpty(sb.getProm_name()))
			tv_cartgoodsodds.setText(sb.getProm_name());
		return contentView;
	}

	public SkuBean[] getSkuBeanArr() {
		return skuBeanArr;
	}

	public void setSkuBeanArr(SkuBean[] skuBeanArr) {
		this.skuBeanArr = skuBeanArr;
	}

	@Override
	public void onCounterChange(int count, int operator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCounterPreChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCounterChanged(HttpResult httpResult) {
		// TODO Auto-generated method stub
		Sku_list sl = JsonToBean.json2Sku_list(httpResult.getResult());
		SkuListInfo si = CartOperation.getInfoFromSkuList(sl);
		setSkuBeanArr(sl.getSku_list());
		setInfo(si);
		this.notifyDataSetChanged();
	}

	@Override
	public void onCheckedChanged(CompoundButton view, boolean ischecked) {
		SkuBean sb = (SkuBean)view.getTag();
		Log.d(sb.getTitle()+" ischecked "+ischecked);
		sb.setIsPurchased(ischecked);
		
		Long[] ids = CartOperation.getLinkSkuIDSToPurchaseL(getSkuBeanArr());
		SkuListInfo si = CartOperation.getInfoFromSkuListByBeanArr(getSkuBeanArr(),ids);
		setInfo(si);
	}
	
	private void setInfo(SkuListInfo si)
	{
		if(si != null)
		{
			pricetage.setText("гд"+ UnitTools.formatDoubleTagTail2(si.amount));
		}
	}
	
	public TextView getPricetage() {
		return pricetage;
	}
	public void setPricetage(TextView pricetage) {
		this.pricetage = pricetage;
	}

	public boolean isEmpty()
	{
		if(skuBeanArr == null || skuBeanArr.length < 1)
			return true;
		return false;
	}
	
	
}
