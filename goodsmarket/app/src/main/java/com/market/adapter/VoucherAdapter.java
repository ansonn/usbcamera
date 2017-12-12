package com.market.adapter;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.Voucher;
import com.market.utils.UnitTools;

public class VoucherAdapter extends BaseAdapter {
	private Context context;
	private Voucher[] vercherList;

	public VoucherAdapter(Context context, Voucher[] vercherList) {
		this.context = context;
		this.vercherList = vercherList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (vercherList != null)
			return vercherList.length;
		return 0;
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		if (vercherList != null && vercherList.length > index)
			return vercherList[index];
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int index, View contantView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = contantView;

		if (vercherList[index] == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append("有效期：");
		sb.append(UnitTools.long2DateByDATEYMD(vercherList[index]
				.getStart_time()));
		sb.append(" 至　");
		sb.append(UnitTools.long2DateByDATEYMD(vercherList[index].getEnd_time()));

		String s2e = sb.toString();

		if (view == null) {
			LayoutInflater lf = (LayoutInflater) context
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			view = lf.inflate(R.layout.item_vercher, null);
			
		}
		
		TextView tv_vouchername = (TextView) view
				.findViewById(R.id.tv_vouchername);
		// tv_starttimetoendtime tv_par_value tv_need_money
		TextView tv_starttimetoendtime = (TextView) view
				.findViewById(R.id.tv_starttimetoendtime);
		TextView tv_par_value = (TextView) view.findViewById(R.id.tv_par_value);
		TextView tv_need_money = (TextView) view
				.findViewById(R.id.tv_need_money);
		tv_vouchername.setText(vercherList[index].getName());
		tv_starttimetoendtime.setText(s2e);
		tv_par_value.setText("￥"+vercherList[index].getPar_value());
		tv_need_money.setText("满 "+vercherList[index].getNeed_money() + "元时使用");
		return view;
	}

}
