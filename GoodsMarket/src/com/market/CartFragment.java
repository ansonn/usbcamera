package com.market;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.haoyikuai.shop.android.R;
import com.market.adapter.CartListAdapter;
import com.market.bean.SkuBean;
import com.market.bean.SkuListInfo;
import com.market.bean.Sku_list;
import com.market.dbmanage.DBFileManage;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.CartHttpOperation;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.CartOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.MyLog;
import com.market.utils.UnitTools;
import com.market.utils.CartOperation.OnOperateCartListener;
import com.market.utils.ResourceLocation;
import com.market.utils.UserStatus;
import com.market.view.PullToRefreshBase;
import com.market.view.PullToRefreshBase.OnRefreshListener;
import com.market.view.PullToRefreshListView;

import de.greenrobot.event.EventBus;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CartFragment extends BaseFragment implements OnHttpCallBack,
		OnClickListener, OnItemClickListener{
	private Button btnTitle;
	private PullToRefreshListView lv_cartgoodslist;
	private CartListAdapter cartListAdapter;
	private ListView orderlist;
	private Button btn_cartcheckout;
	private DBFileManage dbFileManage;
	private TextView tv_cart_totalamount;
	private View contentView;
	private RelativeLayout rl_cartbackground;
	private LinearLayout ll_templayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		contentView = inflater.inflate(R.layout.cart_fragment, container);
		initTitle(contentView);
		initView(contentView);
		initData();
		CartHttpOperation.public_getCart(this, Constants.CALLBACK_FLAG_GET_CARTLIST, this.getActivity());
		this.showProcessDialog();
		return contentView;
	}

	public void isShowtoUser(String latest)  
	{  
		if(contentView != null && latest.equals(Constants.TAB_CART))
		{	
			controlUserInteface(contentView);
			Log.d("CartFragment "+latest);
		}
	}  
	
	private void initTitle(View contentView) {
		btnTitle = (Button) contentView
				.findViewById(R.id.common_header_btn_center);

		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.cart_fragment_title);
		this.setImagenTextHeaderRightButton(contentView,
				R.drawable.delete_grey,
				this.getResources().getString(R.string.delete), this);
	}

	private void initData() {
		dbFileManage = new DBFileManage(getActivity());
	}

	private void initView(View contentView) {
		tv_cart_totalamount = (TextView)contentView.findViewById(R.id.tv_cart_totalamount);
		rl_cartbackground = (RelativeLayout)contentView.findViewById(R.id.rl_cartbackground);
		ll_templayout  = (LinearLayout)contentView.findViewById(R.id.ll_templayout);
		lv_cartgoodslist = (PullToRefreshListView) contentView
				.findViewById(R.id.lv_cartgoodslist);
		btn_cartcheckout = (Button) contentView
				.findViewById(R.id.btn_cartcheckout);
		btn_cartcheckout.setOnClickListener(this);
		lv_cartgoodslist.setPullRefreshEnabled(true);
		lv_cartgoodslist.setPullLoadEnabled(true);
		lv_cartgoodslist.setScrollLoadEnabled(false);
		lv_cartgoodslist.setScrollbarFadingEnabled(false);
		cartListAdapter = new CartListAdapter(this.getActivity(),dbFileManage);
		cartListAdapter.setPricetage(tv_cart_totalamount);
		orderlist = lv_cartgoodslist.getRefreshableView();
		orderlist.setOnItemClickListener(this);
		orderlist.setAdapter(cartListAdapter);
		lv_cartgoodslist
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						CartHttpOperation.public_getCart(CartFragment.this,
								Constants.CALLBACK_FLAG_GET_CARTLIST,
								getActivity());
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

					}
				});
		
		
	}

	
	
	private void controlUserInteface(View contentView)
	{
		if(contentView == null)
			return;
		if(!UserStatus.isLogin(getActivity()))
		{
			contentView.findViewById(R.id.rl_carttipsheader).setVisibility(View.VISIBLE);
			TextView tv_carttips = (TextView)contentView.findViewById(R.id.tv_carttips);
			String tips = getResources().getString(R.string.cart_logintips);
			tv_carttips.setText(getResources().getString(R.string.friendlyTips,tips));
		}
		else
			contentView.findViewById(R.id.rl_carttipsheader).setVisibility(View.GONE);
		
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.btn_cartcheckout:
				long[] ids = CartOperation.getLinkSkuIDSToPurchase(cartListAdapter.getSkuBeanArr());
				if(ids == null)
					return ;
				Intent intent = new Intent();
				intent.putExtra(Constants.LONGARREXTRA, ids);
				intent.setClass(getActivity(), OrderEdittingActivity.class);
				getActivity().startActivity(intent);
				break;
			case ResourceLocation.BUTTON_HEADER_RIGHT:
				long[] idsdelete = CartOperation.getLinkSkuIDSToPurchase(cartListAdapter.getSkuBeanArr());
				Log.d("idsdelete "+idsdelete);
				if(idsdelete == null)
					return ;
				Log.d("ResourceLocation.BUTTON_HEADER_RIGHT");
				this.showProcessDialog();
				CartHttpOperation.public_deleteFromCart(idsdelete, this, Constants.CALLBACK_FLAG_REMOVEITEMFROMCART, this.getActivity());
		}
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		
		if (requestId == Constants.CALLBACK_FLAG_GET_CARTLIST) {
			if (httpResult.isSuccess()) {
				{
					Log.d("pumkid   " + httpResult.getResult());
					if (cartListAdapter == null)
						return;
					Sku_list sl = JsonToBean.json2Sku_list(httpResult.getResult());
					SkuListInfo si = CartOperation.getInfoFromSkuList(sl);
					setInfo(si);
					cartListAdapter.setSkuBeanArr(sl.getSku_list());
					cartListAdapter.notifyDataSetChanged();
				}
			} else {
				Log.d("pumkid   " + httpResult.getResult());
			}
			lv_cartgoodslist.cancelLongPress();
			lv_cartgoodslist.onPullDownRefreshComplete();
		}
		else if(requestId ==Constants.CALLBACK_FLAG_CARTCHECKOUTBEFOREPURCHASED)
		{
			if (httpResult.isSuccess()) {
				{
				}
			} else {
				Log.d("pumkid   " + httpResult.getResult());
			}
		}
		else if(requestId == Constants.CALLBACK_FLAG_REMOVEITEMFROMCART)
		{
			if (httpResult.isSuccess()) {
				{
					Log.d("pumkid   " + httpResult.getResult());
					if (cartListAdapter == null)
						return;
					Sku_list sl = JsonToBean.json2Sku_list(httpResult.getResult());
					SkuListInfo si = CartOperation.getInfoFromSkuList(sl);
					setInfo(si);
					cartListAdapter.setSkuBeanArr(sl.getSku_list());
					cartListAdapter.notifyDataSetChanged();
				}
			} else {
			}
		}
		if(cartListAdapter == null || cartListAdapter.isEmpty())
		{
			rl_cartbackground.setVisibility(View.VISIBLE);
			ll_templayout.setVisibility(View.GONE);
		}
		else
		{
			rl_cartbackground.setVisibility(View.GONE);
			ll_templayout.setVisibility(View.VISIBLE);
		}
		this.closeProcessDialog();
	}

	private void setInfo(SkuListInfo si)
	{
		if(si != null)
		{
			tv_cart_totalamount.setText("гд"+ UnitTools.formatDoubleTagTail2(si.amount));
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int index, long position) {
		// TODO Auto-generated method stub
			if(cartListAdapter != null)
			{
				SkuBean lskuBean = (SkuBean)cartListAdapter.getItem(index);
				if(lskuBean != null)
				{
					Intent intent = new Intent();
					intent.putExtra(Constants.GOODS_ID, lskuBean.getGoods_id());
					intent.setClass(getActivity(), GoodsOverviewActivity.class);
					this.getActivity().startActivity(intent);
				}
			}
	}

	
}
