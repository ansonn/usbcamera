package com.market.adapter;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.bean.OrderGoods;
import com.market.bean.Spec;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsinOrderAdapter extends BaseAdapter{
	private Context context;
	private OrderGoods[] orderGoods_list;
	private BitmapUtils bitmapUtils;
	public GoodsinOrderAdapter(Context context)
	{
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
	}
	
	public OrderGoods[] getSkuBean_list()
	{
		return this.orderGoods_list;
	}
	
	public void setOrderGoods_list(OrderGoods[] orderGoods_list)
	{
		this.orderGoods_list = orderGoods_list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(orderGoods_list != null)
			return orderGoods_list.length;
		return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if(orderGoods_list != null && orderGoods_list.length > index)
			return orderGoods_list[index];
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
		OrderGoods sb = orderGoods_list[index];
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
		tv_countofgoods.setText(context.getResources().getString(R.string.order_goodspurchasedcountofitself, sb.getGoods_number()));
		tv_amountofgoods.setText(context.getResources().getString(R.string.pricetag, sb.getReal_price()));
		if(TextUtils.isEmpty(sb.getProm_name()))
			tv_cartgoodsodds.setVisibility(View.GONE);
		tv_cartgoodsodds.setText(sb.getProm_name());
		tv_cartgoodsSize.setText(Html.fromHtml(getSpecStr(sb.getSpecBean())));
		bitmapUtils.display(img_cartgoodsimg, sb.getThumb());
		return contentView;
	}
	
	private String getSpecStr(Spec[] spec)
	{
		if(spec == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for(Spec item : spec)
		{	
			if(item.getName() != null)
			{
				sb.append(item.getName());
				sb.append("-");
			}
			if(item.getValueStr() != null && item.getValueStr().length > 2)
			{
				sb.append(item.getValueStr()[2]);
				sb.append(" ");
			}
		}
		return sb.toString();
	}


}
