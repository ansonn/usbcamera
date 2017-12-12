package com.market.utils;

import android.content.Context;

import com.market.bean.SkuBean;
import com.market.bean.SkuListInfo;
import com.market.bean.Sku_list;
import com.market.bean.Spec;
import com.market.dbmanage.DBFileManage;
import com.market.http.HttpResult;
import com.market.http.JsonToBean;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.CartHttpOperation;

public class CartOperation {
//	private static Sku_list link_skuBean;
//	private static int LINKCOUNT = 20;
//
//	public static void  addToCart(DBFileManage dbFileManage,
//			SkuBean skubean, int number, OnHttpCallBack callBack,
//			int requestId, Context context,OnOperateCartListener onOperateCartListener) {
//		if (!UserStatus.isLogin()) {
//
//			if (link_skuBean == null && dbFileManage != null)
//				link_skuBean = getLinkSkuBeanFromDB(dbFileManage);
//			if (link_skuBean == null)
//				link_skuBean = new Sku_list();
//
//			SkuBean[] sbarr = link_skuBean.getSku_list();
//			if (sbarr == null) {
//				link_skuBean.setSku_list(new SkuBean[LINKCOUNT]);
//				sbarr = link_skuBean.getSku_list();
//				sbarr[0] = skubean;
//				if(onOperateCartListener != null)
//					onOperateCartListener.OnOperateCart(link_skuBean); 
//			}
//
//			boolean isAdded = false;
//			/**
//			 * 如果已加到购物车，则只需要在数量上设置就可以了
//			 */
//			for (int i = 0; i < sbarr.length; i++) {
//				if (sbarr[i] != null && sbarr[i] == skubean) {
//					if(sbarr[i].getNumber()+number <= sbarr[i].getStore_num())/** 必需增加后的数量必需小于库存 **/
//						sbarr[i].setNumber(sbarr[i].getNumber()+number);
//					isAdded = true;
//				}
//			}
//			
//			/**
//			 * 如果是新的商品，只要加入购物车就行了
//			 */
//			if(!isAdded)
//			{
//				for (int i = 0; i < sbarr.length; i++) {
//					if (sbarr[i] == null) {			/**有空位的原因是有些商品被删除过**/
//							sbarr[i] = skubean;
//							isAdded = true;
//					}
//				}
//			}
//
//			/**
//			 * 商品数组满了，再开设一个新的空间
//			 */
//			if (!isAdded) {
//				int lengthcount = sbarr.length;
//				SkuBean[] newarr = new SkuBean[LINKCOUNT + lengthcount];
//				for (int i = 0; i < newarr.length; i++) {
//					newarr[i] = newarr[i];
//				}
//				newarr[sbarr.length] = skubean;
//				link_skuBean.setSku_list(newarr);
//			}
//
//			if (dbFileManage != null)
//				saveLinkSkuBean(dbFileManage, link_skuBean);
//			if(onOperateCartListener != null)
//				onOperateCartListener.OnOperateCart(link_skuBean); 
//		} else {
//			CartHttpOperation.public_addToCart(skubean.getSku_id(), number,
//					callBack, requestId, context);
//		}
//		
//	}
//
//	public static void saveLinkSkuBean(DBFileManage dbFileManage,
//			Sku_list link_skuBean) {
//		if (link_skuBean != null && dbFileManage != null)
//			dbFileManage.putSkulistToDB(link_skuBean);
//	}
//
//	public static Sku_list getLinkSkuBeanFromDB(DBFileManage dbFileManage) {
//		link_skuBean = dbFileManage.getSkulistByDB();
//		return null;
//	}
//
//	public static void getLinkSkuBeanList(OnHttpCallBack callBack,
//			DBFileManage dbFileManage, final int requestId, Context context, OnOperateCartListener onOperateCartListener) {
//		if(UserStatus.isLogin())
//		{
//			CartHttpOperation.public_getCart(callBack, requestId, context);
//		}
//		else if (CartOperation.link_skuBean == null && dbFileManage != null) {
//			link_skuBean = CartOperation.getLinkSkuBeanFromDB(dbFileManage);
//			if(onOperateCartListener != null)
//				onOperateCartListener.OnOperateCart(link_skuBean); 
//		}
//	}
//
//	public static void removeAllLink() {
//
//	}
//
//	public static void postAllGoodsToServer(DBFileManage dbFileManage) {
//		link_skuBean = CartOperation.getLinkSkuBeanFromDB(dbFileManage);
//	}
//
//	public static Sku_list removeSkuBeanBy(SkuBean skubean) {
//		if (!UserStatus.isLogin()) {
//			if (link_skuBean == null || link_skuBean.getSku_list() == null)
//				return null;
//			else
//				for (int i = 0; i < link_skuBean.getSku_list().length; i++) {
//					if (link_skuBean.getSku_list()[i].getSku_id().equals(
//							skubean.getSku_id())) {
//						link_skuBean.getSku_list()[i] = null;
//					}
//				}
//		}
//		return link_skuBean;
//	}
//	
//	public static void removeSkuBeansByids(long[] ids, DBFileManage dbFileManage, OnHttpCallBack callBack, final int requestId, Context context, OnOperateCartListener onOperateCartListener)
//	{
//		if (!UserStatus.isLogin()) {
//			if (link_skuBean == null || link_skuBean.getSku_list() == null)
//				if(onOperateCartListener != null)
//					onOperateCartListener.OnOperateCart(null); 
//			else
//				for (int i = 0; i < link_skuBean.getSku_list().length; i++) {
//					for(int j = 0 ; j < ids.length ; j++)
//					{
//						if(link_skuBean.getSku_list()[i].equals(j))
//							link_skuBean.getSku_list()[i] = null;
//					}
//				}
//		}
//		else
//		{
//			CartHttpOperation.public_deleteFromCart(ids, callBack, requestId, context);
//		}
//		if (dbFileManage != null)
//			saveLinkSkuBean(dbFileManage, link_skuBean);
//		if(onOperateCartListener != null)
//			onOperateCartListener.OnOperateCart(link_skuBean); 
//	}
//
//	public static Sku_list getlink_skuBean() {
//		return link_skuBean;
//	}

	/**
	 * 以下函数关系乱，以后优化
	 */

	/**
	 * 返回所有SkuID
	 * 
	 * @param skbarr
	 * @return
	 */
	public static Long[] getLinkSkuIDS(SkuBean[] skbarr) {
		if (skbarr != null) {
			Long[] skuids = new Long[skbarr.length];
			for (int i = 0; i < skbarr.length; i++) {
				skuids[i] = skbarr[i].getSku_id();
			}
			return skuids;
		}
		return null;
	}

	/**
	 * 返回SkuID的字符串表示
	 * 
	 * @param skbarr
	 * @return
	 */
	public static String[] getLinkSkuIDSS(SkuBean[] skbarr) {
		if (skbarr != null) {
			String[] skuids = new String[skbarr.length];
			for (int i = 0; i < skbarr.length; i++) {
				skuids[i] = skbarr[i].getSku_id() + "";
			}
			return skuids;
		}
		return null;
	}

	/**
	 * 返回SkuID已选的id数组 (long类型)
	 * 
	 * @param skbarr
	 * @return
	 */
	public static long[] getLinkSkuIDSToPurchase(SkuBean[] skbarr) {
		if (skbarr != null) {
			long[] skuids = new long[skbarr.length];
			for (int i = 0; i < skbarr.length; i++) {
				if (skbarr[i].getIsPurchased())
					skuids[i] = skbarr[i].getSku_id();
			}
			return skuids;
		}
		return null;
	}

	/**
	 * 返回SkuID已选的id数组 (Long类)
	 * 
	 * @param skbarr
	 * @return
	 */
	public static Long[] getLinkSkuIDSToPurchaseL(SkuBean[] skbarr) {
		if (skbarr != null) {
			Long[] skuids = new Long[skbarr.length];
			for (int i = 0; i < skbarr.length; i++) {
				if (skbarr[i].getIsPurchased())
					skuids[i] = skbarr[i].getSku_id();
			}
			return skuids;
		}
		return null;
	}

	/**
	 * 使用整合类(可序列化的类，用于Activity间的值传递) 从订单列表得到一些有关的信息
	 * 
	 * @param skuarr
	 * @param ids
	 *            long 类型
	 * @return
	 */
	public static SkuListInfo getInfoFromSkuList(Sku_list sl) {
		SkuBean[] skuarr = sl.getSku_list();
		return getInfoFromSkuListByBeanArr(skuarr);
	}

	/**
	 * 使用数组 从订单列表得到一些有关的信息
	 * 
	 * @param skuarr
	 * @param ids
	 *            long 类型
	 * @return
	 */
	public static SkuListInfo getInfoFromSkuListByBeanArr(SkuBean[] skuarr) {
		if (skuarr != null) {
			SkuListInfo si = new SkuListInfo();

			for (int i = 0; i < skuarr.length; i++) {
				if (skuarr[i] != null) {
					si.amount += skuarr[i].getReal_price()
							* skuarr[i].getNumber(); // 得到总价
				}
			}
			return si;
		}
		return null;
	}

	/**
	 * 使用整合类(可序列化的类，用于Activity间的值传递) 从订单列表得到一些有关的信息
	 * 
	 * @param skuarr
	 * @param ids
	 *            long 类型
	 * @return
	 */
	public static SkuListInfo getInfoFromSkuList(Sku_list sl, Long[] ids) {
		SkuBean[] skuarr = sl.getSku_list();
		return getInfoFromSkuListByBeanArr(skuarr);
	}

	/**
	 * 使用数组 从订单列表得到一些有关的信息
	 * 
	 * @param skuarr
	 * @param ids
	 * @return
	 */
	public static SkuListInfo getInfoFromSkuListByBeanArr(SkuBean[] skuarr,
			Long[] ids) {

		if (skuarr != null) {
			SkuListInfo si = new SkuListInfo();

			for (int i = 0; i < ids.length; i++) {
				for (int j = 0; j < skuarr.length; j++)
					if (skuarr[i] != null
							&& skuarr[j].getSku_id().equals(ids[i]))
						si.amount += skuarr[i].getReal_price()
								* skuarr[i].getNumber(); // 得到总价
			}
			return si;
		}
		return null;
	}

	public static String getSkuIdJson(long[] sku_ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < sku_ids.length; i++) {
			sb.append(sku_ids[i]);
			if (i != (sku_ids.length - 1))
				sb.append(",");
		}
		sb.append("]");

		return sb.toString();
	}
	
	public interface OnOperateCartListener{
		public void OnOperateCart(Sku_list link_skuBean);
	}
	
	public static String getSpecStr(Spec[] spec)
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
