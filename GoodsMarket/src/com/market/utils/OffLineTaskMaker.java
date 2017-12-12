package com.market.utils;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.market.bean.SkuBean;
import com.market.bean.Sku_list;
import com.market.dbmanage.DBFileManage;

public class OffLineTaskMaker {
	/**
	 * mslist is a memory resider that used to load so fast that you can do any EDITION as fast as it can.
	 * mdbManager deal a list of cart in local way.
	 * Both of above will doing the same thing when mslist being any ADRE operations.
	 * 
	 */
	private Sku_list mskulist;
	private ArrayList<SkuBean> mslist;
	private Context context;
	private DBFileManage mdbManager;
	
	public OffLineTaskMaker(Context context)
	{
		this.context = context;
		mdbManager = new DBFileManage(context);
	}
	
	public ArrayList<SkuBean> getLocalCart()
	{
		if(mslist == null)
			return getLocalCacheCart(context);
		else 
			return mslist;
	}
	
	public ArrayList<SkuBean> getLocalCacheCart(Context context)
	{
		if(context == null)
			return null;
		
		if(mskulist == null)
		{
			mskulist = new Sku_list();
			mslist = (ArrayList<SkuBean>) Arrays.asList(mskulist.getSku_list());
			return mslist;
		}
		else 
		{
			mskulist = mdbManager.getSkulistByDB();
			if(mskulist != null && mskulist.getSku_list() != null)
				mslist = (ArrayList<SkuBean>) Arrays.asList(mskulist.getSku_list());
			else
				mskulist = new Sku_list();
			return mslist;
		}
	}
	
	/**
	 * remove the goods that in cart
	 * @param index while 'skuid' is less then 0, All Items of Cart will be remove.Else removes the specified one
	 */
	public void removeCartGoods(long skuid)
	{
		if(skuid < 0)
			mslist = new ArrayList<SkuBean>();
		else
		{
			mslist = getLocalCart();
			for(int i = 0 ; i < mslist.size() ; i++)
			{
				if(mslist.get(i).getSku_id() == skuid)
					mslist.remove(i);
			}
		}
		saveDB();
		
	}

	/**
	 * remove the goods that in cart
	 * @param index while 'index' is less then 0, All Items of Cart will be remove.Else removes the specified one
	 */
	public void addCartGoods(SkuBean sb)
	{
		if(sb != null)
		{
			mslist = getLocalCart();
			int index = hasCartGoods(sb.getSku_id());
			
			if(index > 0)
			{
				SkuBean sbtemp = mslist.get(index);
				sbtemp.setNumber(sbtemp.getNumber()+sb.getNumber());
			}
			else
				mslist.add(sb);
		}
		saveDB();
	}
	
	
	public int hasCartGoods(long skuid)
	{
		mslist = getLocalCart();
		if(skuid < 0)
			return -1;
		
		if(mslist.size() < skuid)
			return -1;
		
		for(int i = 0 ; i < mslist.size() ; i++)
		{
			SkuBean sb = mslist.get(i);
			if(sb != null)
			if(mslist.get(i).getSku_id() == skuid)
			{
				return i;
			}
		}
		return -1;
	}
	
	
	private void saveDB()
	{
		mskulist = new Sku_list();
		mskulist.setSku_list((SkuBean[])mslist.toArray());
		mdbManager.putSkulistToDB(mskulist);
	}
}
