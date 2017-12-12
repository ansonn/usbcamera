package com.market.adapter;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.bean.GoodsInfoBean;
import com.market.bean.GoodsoverviewInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFavoriteAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<GoodsoverviewInfo> goodsList;
	private BitmapUtils bitmapUtils;
	
	public MyFavoriteAdapter(Context context)
	{
		this.context = context;
		bitmapUtils = new BitmapUtils(context);
	}
	
	@Override
	public int getCount() {
		if(goodsList != null)
			return goodsList.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(goodsList != null && goodsList.size() > index)
			return goodsList.get(index);
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		View contentView = view;
		GoodsoverviewInfo goodsInfoBean= goodsList.get(index);
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.item_focus, null);
		}
		ImageView img_cartgoodsimg = (ImageView)contentView.findViewById(R.id.img_cartgoodsimg);
		TextView tv_cartgoodsname = (TextView)contentView.findViewById(R.id.tv_cartgoodsname);
		TextView tv_cartgoodsPrice = (TextView)contentView.findViewById(R.id.tv_cartgoodsPrice);
		TextView tv_amountofgoods = (TextView)contentView.findViewById(R.id.tv_amountofgoods);
		TextView tv_commnet = (TextView)contentView.findViewById(R.id.tv_commnet);
		
		bitmapUtils.display(img_cartgoodsimg, goodsInfoBean.getThumb());
		tv_cartgoodsname.setText(goodsInfoBean.getTitle());
		tv_commnet.setText(context.getResources().getString(R.string.my_favoritecomment,goodsInfoBean.getComment_count()));
		tv_cartgoodsPrice.setText(context.getResources().getString(R.string.pricetag,goodsInfoBean.getMarket_price()));
		return contentView;
	}

	public ArrayList<GoodsoverviewInfo> getGoodsList() {
		return goodsList;
	}

	
	public void setGoodsList(GoodsoverviewInfo[] goodsList) {
		if(this.goodsList == null)
			this.goodsList = new ArrayList<GoodsoverviewInfo>();
		if(goodsList != null)
		for(int i = 0 ; i < goodsList.length ; i++)
			this.goodsList.add(goodsList[i]);
 	}

}
