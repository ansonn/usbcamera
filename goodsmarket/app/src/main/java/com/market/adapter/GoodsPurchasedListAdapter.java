package com.market.adapter;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.bean.SkuBean;
import com.market.bean.Spec;
import com.market.utils.CartOperation;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 清单的适配器
 * （本来和购物车的差不多，不过考虑维护不稳定的改变，就分开两个）
 * @author pumkid
 *
 */
public class GoodsPurchasedListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<SkuBean> skuBean_list;
	private BitmapUtils bitmapUtils;
	public GoodsPurchasedListAdapter(Context context)
	{
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
	}
	
	public ArrayList<SkuBean> getSkuBean_list()
	{
		return this.skuBean_list;
	}
	
	public void setSkuBean_list(ArrayList<SkuBean> skuBean_list)
	{
		this.skuBean_list = skuBean_list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(skuBean_list != null)
			return skuBean_list.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if(skuBean_list != null && skuBean_list.size() > index)
			return skuBean_list.get(index);
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
		View contentView = view;
		SkuBean sb = skuBean_list.get(index);
		if(sb == null)
			return null;
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(
						R.layout.item_goodspurchasedlist, null);
		}
		TextView tv_cartgoodsname = (TextView)contentView.findViewById(R.id.tv_cartgoodsname);
		TextView tv_countofgoods = (TextView)contentView.findViewById(R.id.tv_countofgoods);
		TextView tv_amountofgoods = (TextView)contentView.findViewById(R.id.tv_amountofgoods);
		TextView tv_cartgoodsodds = (TextView)contentView.findViewById(R.id.tv_cartgoodsodds);
		TextView tv_cartgoodsSize = (TextView)contentView.findViewById(R.id.tv_cartgoodsSize);
		ImageView img_cartgoodsimg = (ImageView)contentView.findViewById(R.id.img_cartgoodsimg);
		tv_cartgoodsname.setText(sb.getTitle());
		tv_countofgoods.setText(context.getResources().getString(R.string.order_goodspurchasedcountofitself, sb.getNumber()));
		tv_amountofgoods.setText(context.getResources().getString(R.string.pricetag, sb.getMarket_price()));
		if(TextUtils.isEmpty(sb.getProm_name()))
			tv_cartgoodsodds.setVisibility(View.GONE);
		tv_cartgoodsodds.setText(sb.getProm_name());
		tv_cartgoodsSize.setText(Html.fromHtml(CartOperation.getSpecStr(sb.getSpecbean())));
		bitmapUtils.display(img_cartgoodsimg, sb.getImage());
		return contentView;
	}
	
	

}
