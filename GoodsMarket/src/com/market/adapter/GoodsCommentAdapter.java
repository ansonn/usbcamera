package com.market.adapter;

import java.util.ArrayList;

import com.haoyikuai.shop.android.R;
import com.market.bean.CommentBean;
import com.market.bean.SimpleSpec;
import com.market.utils.Log;
import com.market.utils.UnitTools;
import com.market.view.StarsComment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsCommentAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<CommentBean> commentList;

	public GoodsCommentAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		if(commentList != null )
			return commentList.size();
		return 0;
	}

	@Override
	public Object getItem(int index) {
		if(commentList != null && commentList.size() > index)
			return commentList.get(index);
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
		if(context == null)
			return null;
		CommentBean commentBean = this.commentList.get(index);
		if(contentView == null)
		{
			contentView = LayoutInflater.from(context).inflate(R.layout.item_goodscomment, null);
		}
		
		TextView tv_itemcommentusername = (TextView)contentView.findViewById(R.id.tv_itemcommentusername);
		TextView tv_itemcommenttime = (TextView)contentView.findViewById(R.id.tv_itemcommenttime);
		TextView tv_usercomment = (TextView)contentView.findViewById(R.id.tv_usercomment);
		TextView tv_goodscommentsize = (TextView)contentView.findViewById(R.id.tv_goodscommentsize);
		TextView tv_goodscommentpurchasedtime = (TextView)contentView.findViewById(R.id.tv_goodscommentpurchasedtime);
		StarsComment starsc_score = (StarsComment)contentView.findViewById(R.id.starsc_score);
		
		tv_itemcommentusername.setText(commentBean.getUser_name());
		String date = "";
		if(commentBean.getDate() != null)
			date = UnitTools.long2DateByDefault(commentBean.getDate());
		
		tv_itemcommenttime.setText(date);
		tv_usercomment.setText(commentBean.getContent());
		tv_goodscommentsize.setText(SimpleSpec2String(commentBean.getSpec()));
		starsc_score.setStarCount(commentBean.getScore().intValue());
		return contentView;
	}

	public void addCommentList(CommentBean[] commentarr) {
		if (commentList == null)
			commentList = new ArrayList<CommentBean>();

		for (CommentBean cb : commentarr)
			commentList.add(cb);
	}

	public void setEmptyList() {
		commentList = new ArrayList<CommentBean>();
	}

	public ArrayList<CommentBean> getCommentList() {
		return this.commentList;
	}
	
	private String SimpleSpec2String(SimpleSpec[] specarr)
	{
		StringBuilder sb = new StringBuilder();
		if(specarr != null)
		{
			for(SimpleSpec sc : specarr)
			{
				sb.append(sc.getName());
				sb.append("-");
				sb.append(sc.getValue());
				sb.append(" ");
			}
		}
		if(sb.length() < 1)
			sb.append("нч");
		return sb.toString();
	}
}
