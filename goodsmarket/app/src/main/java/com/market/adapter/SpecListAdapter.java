package com.market.adapter;

import com.haoyikuai.shop.android.R;
import com.market.bean.SkuBean;
import com.market.bean.Sku_list;
import com.market.bean.Spec;
import com.market.bean.ValueBean;
import com.market.utils.Log;
import com.market.view.GoodsSpecChoosedLayout;
import com.market.view.GoodsSpecChoosedLayout.OnSpecItemSelcetListener;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpecListAdapter extends BaseAdapter{
	private Context context;
	private Spec[] specarr;
	private Sku_list skulist;
	private float margin;
	private OnSpecItemSelcetListener onSpecItemSelcetListener;
	private String keys;
	
	public SpecListAdapter(Context context, Sku_list skulist, Spec[] specarr,OnSpecItemSelcetListener onSpecItemSelcetListener)
	{
		this.context = context;
		this.specarr = specarr;
		this.margin = context.getResources().getDimension(R.dimen.common_margin);
		this.onSpecItemSelcetListener = onSpecItemSelcetListener;
		this.skulist = skulist;
	}
	
	public void setSelectedSpec(String keys)
	{
		if(keys.length() > 2)
			this.keys = keys.substring(1, keys.length()-1);
		else
			this.keys = "";
	}
	
	@Override
	public int getCount() {
		if(specarr != null)
			return specarr.length;
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(specarr != null && position > specarr.length)
			return specarr[position];
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		if(context == null)
			return null;
		View contentView = view;
		ViewHoler viewHoler;
		
		if(contentView == null)
		{
			viewHoler = new ViewHoler();
			LayoutInflater lf = (LayoutInflater)context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			contentView = lf.inflate(R.layout.view_spec_showlist, null);
			GoodsSpecChoosedLayout ll_dialog_dimens = (GoodsSpecChoosedLayout)contentView.findViewById(R.id.ll_dialog_dimens);
			TextView textView = (TextView)contentView.findViewById(R.id.tv_specItem);
			viewHoler.ll_dialog_dimens = ll_dialog_dimens;
			viewHoler.textView = textView;
			contentView.setTag(viewHoler);
		}
		viewHoler = (ViewHoler)contentView.getTag();
		viewHoler.textView.setText(specarr[index].getName());
		viewHoler.ll_dialog_dimens.setOnSpecItemSelcetListener(onSpecItemSelcetListener);
		viewHoler.ll_dialog_dimens.setViewPosition(index);
		viewHoler.ll_dialog_dimens.setTotalArryLength(specarr.length);
		viewHoler.ll_dialog_dimens.setItemPadding((int)margin, (int)margin/2, (int)margin, (int)margin/2);
		viewHoler.ll_dialog_dimens.setItemShell((int)margin, (int)margin);
		viewHoler.ll_dialog_dimens.setDatas(specarr[index], keys, skulist, onSpecItemSelcetListener);
		
		return contentView;
	}
   class ViewHoler
   {
	   GoodsSpecChoosedLayout ll_dialog_dimens;
	   TextView textView;
   }
}
