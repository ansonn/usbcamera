package com.market.adapter;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.market.bean.RefundBean;
import com.market.utils.UnitTools;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RefundAdapter extends BaseAdapter{
	private ArrayList<RefundBean> refundBeanList;
	private Context context;
	private Resources resources;
	public RefundAdapter(Context context)
	{
		this.context = context;
		resources = this.context.getResources();
	}
	
	@Override
	public int getCount() {
		if(refundBeanList != null)
			return refundBeanList.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(refundBeanList != null && refundBeanList.size() > index)
			return refundBeanList.get(index);
		
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	@Override
	public View getView(int index, View view, ViewGroup parent) {
		View contentView = view;
		
		if(context == null || refundBeanList ==null)
			return null;
		
		RefundBean refundBean = refundBeanList.get(index);
		
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.item_refundbrief, null);
		}
		TextView tv_refundorderid = (TextView)contentView.findViewById(R.id.tv_refundorderid);
		TextView tv_refundway = (TextView)contentView.findViewById(R.id.tv_refundway);
		TextView tv_refundapplytime = (TextView)contentView.findViewById(R.id.tv_refundapplytime);
		TextView tv_refundstatus = (TextView)contentView.findViewById(R.id.tv_refundstatus);
		
		if(resources != null)
		{
			tv_refundorderid.setText(resources.getString(R.string.refund_orderid,refundBean.getOrder_id()));
			tv_refundway.setText(resources.getString(R.string.refund_way,refundBean.getRefund_type_name()));
			tv_refundapplytime.setText(resources.getString(R.string.refund_createtme,UnitTools.long2DateByDefault(refundBean.getCreate_time())));
			tv_refundstatus.setText(resources.getString(R.string.refund_dealstatus,refundBean.getStatus_name()));
		}
		
		return contentView;
	}

	public void addToRefundBeanList(RefundBean[] refundBeanArr)
	{
		if(refundBeanList == null)
			refundBeanList = new ArrayList<RefundBean>();
		if(refundBeanArr != null)
			for(RefundBean reb : refundBeanArr)
				refundBeanList.add(reb);
	}
	
	public void setRefundBeanListEmpty()
	{
		refundBeanList = new ArrayList<RefundBean>();
	}
	
	public RefundBean getRefunBean(int index)
	{
		if(refundBeanList != null && refundBeanList.size() > index)
			return refundBeanList.get(index);
		return null;
	}
}
