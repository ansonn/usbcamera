package com.market.adapter;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.OrderGoodsCommentActivity;
import com.market.bean.OrderGoods;
import com.market.bean.Spec;
import com.market.utils.Constants;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderCommenListAdapter extends BaseAdapter implements OnClickListener{
	Context context;
	private long order_id = -1;
	private OrderGoods[] orderGoods;
	private BitmapUtils bitmapUtils;
	public OrderCommenListAdapter(Context context, long order_id)
	{
		this.context = context;
		this.order_id = order_id;
		bitmapUtils = new BitmapUtils(context);
	}
	
	@Override
	public int getCount() {
		if(orderGoods != null)
			return orderGoods.length;
		
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(orderGoods != null && orderGoods.length > index)
			return orderGoods[index];
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		View contentView = view;
		OrderGoods orderGoodsitem = orderGoods[index];
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.item_commetlist, null);
		}
		ImageView img_commentview = (ImageView)contentView.findViewById(R.id.img_commentview);
		TextView tv_goodstitle = (TextView)contentView.findViewById(R.id.tv_goodstitle);
		TextView tv_goodssize = (TextView)contentView.findViewById(R.id.tv_goodssize);
		Button btn_gotocommentitself = (Button)contentView.findViewById(R.id.btn_gotocommentitself);
		bitmapUtils.display(img_commentview, orderGoodsitem.getThumb());
		tv_goodstitle.setText(orderGoodsitem.getTitle());
		tv_goodssize.setText(getSpecStr(orderGoodsitem.getSpecBean()));
		btn_gotocommentitself.setTag(orderGoodsitem);
		btn_gotocommentitself.setOnClickListener(this);
		return contentView;
	}

	
	public void setOrderGoods(OrderGoods[] ordergoods)
	{
		this.orderGoods = ordergoods;
		
	}

	@Override
	public void onClick(View view) {
		OrderGoods orderGoodsitem = (OrderGoods)view.getTag();
		Intent intent = new Intent();
		intent.setClass(context, OrderGoodsCommentActivity.class);
		intent.putExtra(Constants.OBJECTEXTRA, orderGoodsitem);
		intent.putExtra(Constants.LONGEXTRA, this.order_id);
		context.startActivity(intent);
	}
	
	public void deleteOrderGoods(long skuid)
	{
		if(orderGoods == null)
			return;
		if(orderGoods.length < 1)
			setOrderGoods(null);
		OrderGoods[] temp = new OrderGoods[orderGoods.length-1];
		int indexstep = 0;
		for(int i = 0 ; i < orderGoods.length ; i++)
		{
			if(orderGoods[i].getSku_id().longValue() != skuid)
				temp[indexstep++] = orderGoods[i];
		}
		orderGoods = temp;
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
