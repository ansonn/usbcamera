package com.market.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.market.bean.Ads;
import com.market.bean.Category;
import com.market.bean.CommentInfo;
import com.market.bean.Favorite;
import com.market.bean.ForgotIndentifierInfo;
import com.market.bean.ForgotPasswordToken;
import com.market.bean.GoodsInfo;
import com.market.bean.GoodsInfoBean;
import com.market.bean.GoodsList;
import com.market.bean.GoodsoverviewInfo;
import com.market.bean.ImageURLBean;
import com.market.bean.Order;
import com.market.bean.OrderBeforePosted;
import com.market.bean.OrderDetail;
import com.market.bean.OrderId;
import com.market.bean.OrderList;
import com.market.bean.PayBillBean;
import com.market.bean.Payment;
import com.market.bean.RefundBean;
import com.market.bean.RefundInfo;
import com.market.bean.SkuBean;
import com.market.bean.Sku_list;
import com.market.bean.Spec;
import com.market.bean.UpdateInfo;
import com.market.bean.User;
import com.market.bean.ValueBean;
import com.market.bean.VoucherList;

/**
 * 这里定义若干方法，用于将json对像转成实本类
 * 
 * @author 陈伟斌
 * 
 */
public class JsonToBean {

	public static final String TAG = "JsonToBean";

	private static Gson gson;

	static {
		gson = new Gson();
	}

	public static CommentInfo json2CommentInfo(String json)
	{
		CommentInfo commentInfo = gson.fromJson(json, CommentInfo.class);
		return commentInfo;
	}
	
	
	public static RefundBean json2RefundBean(String json)
	{
		RefundBean refundBean = gson.fromJson(json, RefundBean.class);
		return refundBean;
	}
	
	
	public static RefundInfo json2RefundInfo(String json)
	{
		RefundInfo refundInfo = gson.fromJson(json, RefundInfo.class);
		return refundInfo;
	}
	
	
	public static Favorite json2Favorite(String json)
	{
		Favorite favorite = gson.fromJson(json, Favorite.class);
		return favorite;
	}
	
	public static PayBillBean json2PayBillBean(String json)
	{
		PayBillBean payBillBean = gson.fromJson(json, PayBillBean.class);
		return payBillBean;
	}
	
	public static OrderId json2Orderid(String json)
	{
		OrderId orderId = gson.fromJson(json, OrderId.class);
		return orderId;
	}
	
	public static OrderBeforePosted json2OrderBeforePosted(String json)
	{
		OrderBeforePosted orderBeforePosted = gson.fromJson(json,
				OrderBeforePosted.class);
		if(orderBeforePosted.getSku_list() != null)
		try {
			JSONObject josnObject = new JSONObject(json);
			if(josnObject.has("sku_list"))
			{
				JSONArray jsonsku = josnObject.getJSONArray("sku_list");
				for(int i = 0 ; i < jsonsku.length() ; i++)
				{
					JSONObject josnitem = jsonsku.getJSONObject(i);
					
					if(josnitem.has("spec"))
					{
						JSONObject jsonspec = josnitem.getJSONObject("spec");
						if(orderBeforePosted.getSku_list()[i] != null)
						{
							Spec[] spec = new Spec[jsonspec.length()];
							Iterator  iterator = jsonspec.keys();
							int j = 0;
							while(iterator.hasNext())
							{
								JSONObject jsonItem = jsonspec.getJSONObject(iterator.next().toString());
								spec[j] = new Spec();
								if(jsonItem.has("spec_id"))
									spec[j].setSpec_id(jsonItem.getLong("spec_id"));
								if(jsonItem.has("type"))
									spec[j].setType(jsonItem.getInt("type"));
								if(jsonItem.has("value_XXX") )
									spec[j].setValue_XXX(jsonItem.getString("value_XXX"));
								if(jsonItem.has("note") )
									spec[j].setNote(jsonItem.getString("note"));
								if(jsonItem.has("name") )
									spec[j].setName(jsonItem.getString("name"));
								if(jsonItem.has("value") )
								{
									JSONArray jsonvalue = jsonItem.getJSONArray("value");
									String[] valuearr = new String[jsonvalue.length()];
									for(int k = 0 ; k < jsonvalue.length() ; k++)
									{
										valuearr[k] = jsonvalue.getString(k);
									}
									spec[j].setValueStr(valuearr);
								}
								j++;
								
							}
							orderBeforePosted.getSku_list()[i].setSpecbean(spec);
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderBeforePosted;
	}
	
	
	
	public static GoodsInfoBean json2GoodsInfoBean(String json){
		GoodsInfoBean goodsoverviewInfo = gson.fromJson(json,
				GoodsInfoBean.class);
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject jsonsku_list = jsonObject.getJSONObject("goods_info");
			if(jsonsku_list.has("sku_list"))
			{
				JSONObject sku_listjson = jsonsku_list.getJSONObject("sku_list");
				Iterator  iterator = sku_listjson.keys();
				
				
				Sku_list sku_list = new Sku_list();
				sku_list.setSku_list(new SkuBean[sku_listjson.length()]);
				int i = 0;
				while(iterator.hasNext())
				{
					JSONObject jsonItem = sku_listjson.getJSONObject(iterator.next().toString());
					SkuBean skbean = new SkuBean();
					if(jsonItem.has("sell_price"))
						skbean.setSell_price(jsonItem.getDouble("sell_price"));
					if(jsonItem.has("sku_id"))
						skbean.setSku_id(jsonItem.getLong("sku_id"));
					if(jsonItem.has("store_num"))
						skbean.setStore_num(jsonItem.getInt("store_num"));
					if(jsonItem.has("spec_key"))
						skbean.setSpec_key(jsonItem.getString("spec_key"));
					if(jsonItem.has("market_price"))
						skbean.setMarket_price(jsonItem.getDouble("market_price"));
					if(jsonItem.has("pro_no") )
						skbean.setPro_no(jsonItem.getString("pro_no"));
					if(jsonItem.has("prom_name"))
						skbean.setProm_name("prom_name");
					sku_list.getSku_list()[i++] = skbean;
					Log.d("pumkid", skbean.getSpec_key());
				}
				goodsoverviewInfo.getGoods_info().setSku_list(sku_list);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("pumkid", e.getMessage());
		}
		
		return goodsoverviewInfo;
	}

	public static VoucherList json2VoucherList(String json) {
		VoucherList voucherList = gson.fromJson(json, VoucherList.class);
		return voucherList;
	}

	public static OrderDetail json2OrderDetail(String json) {
		OrderDetail orderDetail = gson.fromJson(json, OrderDetail.class);
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(json);
			
			if(jsonObject.has("order_goods"))
			{
				JSONArray jsonorder_goods = jsonObject.getJSONArray("order_goods");
				
				for(int i = 0 ; i < jsonorder_goods.length() ; i++)
				{
					JSONObject josnitem = jsonorder_goods.getJSONObject(i);
					Log.d("json", "josnitem.has(\"spec\") "+josnitem.has("spec"));
					if(josnitem.has("spec"))
					{
						JSONObject jsonspec = josnitem.getJSONObject("spec");
						if(orderDetail.getOrder_goods()[i] != null)
						{
							Spec[] spec = new Spec[jsonspec.length()];
							Iterator  iterator = jsonspec.keys();
							int j = 0;
							while(iterator.hasNext())
							{
								JSONObject jsonItem = jsonspec.getJSONObject(iterator.next().toString());
								spec[j] = new Spec();
								Log.d("json", "json2OrderDetail.toString() "+jsonItem.toString());
								if(jsonItem.has("spec_id"))
									spec[j].setSpec_id(jsonItem.getLong("spec_id"));
								if(jsonItem.has("type"))
									spec[j].setType(jsonItem.getInt("type"));
								if(jsonItem.has("value_XXX") )
									spec[j].setValue_XXX(jsonItem.getString("value_XXX"));
								if(jsonItem.has("note") )
									spec[j].setNote(jsonItem.getString("note"));
								if(jsonItem.has("name") )
									spec[j].setName(jsonItem.getString("name"));
								if(jsonItem.has("value") )
								{
									JSONArray jsonvalue = jsonItem.getJSONArray("value");
									String[] valuearr = new String[jsonvalue.length()];
									for(int k = 0 ; k < jsonvalue.length() ; k++)
									{
										valuearr[k] = jsonvalue.getString(k);
									}
									spec[j].setValueStr(valuearr);
								}
								j++;
								
							}
							orderDetail.getOrder_goods()[i].setSpecBean(spec);
						}
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderDetail;
	}
	public static UpdateInfo json2UpdateInfo(String json) {
		UpdateInfo updateInfo = gson.fromJson(json, UpdateInfo.class);
		return updateInfo;
	}

	public static Sku_list json2Sku_list(String json) {
		Sku_list sku_list = gson.fromJson(json, Sku_list.class);
		if(sku_list.getSku_list() != null)
			try {
				JSONObject josnObject = new JSONObject(json);
				if(josnObject.has("sku_list"))
				{
					JSONArray jsonsku = josnObject.getJSONArray("sku_list");
					for(int i = 0 ; i < jsonsku.length() ; i++)
					{
						JSONObject josnitem = jsonsku.getJSONObject(i);
						
						if(josnitem.has("spec"))
						{
							JSONObject jsonspec = josnitem.getJSONObject("spec");
							if(sku_list.getSku_list()[i] != null)
							{
								Spec[] spec = new Spec[jsonspec.length()];
								Iterator  iterator = jsonspec.keys();
								int j = 0;
								while(iterator.hasNext())
								{
									JSONObject jsonItem = jsonspec.getJSONObject(iterator.next().toString());
									spec[j] = new Spec();
									
									if(jsonItem.has("spec_id"))
										spec[j].setSpec_id(jsonItem.getLong("spec_id"));
									if(jsonItem.has("type"))
										spec[j].setType(jsonItem.getInt("type"));
									if(jsonItem.has("value_XXX") )
										spec[j].setValue_XXX(jsonItem.getString("value_XXX"));
									if(jsonItem.has("note") )
										spec[j].setNote(jsonItem.getString("note"));
									if(jsonItem.has("name") )
										spec[j].setName(jsonItem.getString("name"));
									Log.d("pumkid", spec[j].getName());
									if(jsonItem.has("value") )
									{
										JSONArray jsonvalue = jsonItem.getJSONArray("value");
										String[] valuearr = new String[jsonvalue.length()];
										for(int k = 0 ; k < jsonvalue.length() ; k++)
										{
											valuearr[k] = jsonvalue.getString(k);
										}
										spec[j].setValueStr(valuearr);
									}
									j++;
									
								}
								sku_list.getSku_list()[i].setSpecbean(spec);
							}
						}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return sku_list;
	}

	public static Order json2Order(String json) {
		Order order = gson.fromJson(json, Order.class);
		return order;
	}

	public static User json2User(String json) {
		User user = gson.fromJson(json, User.class);
		return user;
	}

	public static OrderList json2OrderList(String json) {
		OrderList orderList = gson.fromJson(json, OrderList.class);
		return orderList;
	}

	public static Category json2Category(String json) {
		Category category = gson.fromJson(json, Category.class);
		return category;
	}

	public static GoodsList json2GoodList(String json) {
		GoodsList goodsList = gson.fromJson(json, GoodsList.class);
		return goodsList;
	}

	public static Ads json2Ads(String json) {
		Ads AdsList = gson.fromJson(json, Ads.class);
		return AdsList;
	}

	public static List<Category> json2RecommandCategoryList(String json) {
		try {

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray;
			if (!jsonObject.isNull("category_list")) {
				jsonArray = jsonObject.getJSONArray("category_list");
			} else {
				jsonArray = new JSONArray(json);
			}

			List<Category> list = new ArrayList<Category>();

			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(json2Category(jsonArray.getString(i)));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Category> json2CategoryList(String json) {
		try {

			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("category_list")) {
				jsonObject = jsonObject.getJSONObject("category_list");
			}
			Iterator<String> jsonKeys = jsonObject.keys();
			Map<String, Category> cateMap = new HashMap<String, Category>();

			while (jsonKeys.hasNext()) {

				String key = jsonKeys.next();
				cateMap.put(key, json2Category(jsonObject.getString(key)));
			}

			Set<String> cateKeys = cateMap.keySet();
			List<Category> topCategoryList = new ArrayList<Category>();
			for (String key : cateKeys) {
				List<Category> childList = null;
				Category category = cateMap.get(key);
				for (int childId : category.getChild_id_array()) {
					if (childList == null) {
						childList = new ArrayList<Category>();
						category.setSubCategoryList(childList);
					}
					childList.add(cateMap.get(childId + ""));
				}
				if (category.getPid_array() == null
						|| category.getPid_array().length <= 0) {
					topCategoryList.add(category);
				}
			}

			return topCategoryList;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
//	{
//		"goods_info": {
//			"is_online": "1",
//			"comment_count": "0",
//			"spec": [],
//			"score": 5,
//			"prom": null,
//			"store_num": "0",
//			"packing_list": "遥控器*1、快速使用指南*1、保修证*1 座架*1 电视机*1 电源线",
//			"is_favorite": false,
//			"url": "http:\/\/www.haoyikuai.cn\/index.php?a=goods&id=2",
//			"sku_list": {
//				"": {
//					"sell_price": "2799.00",
//					"sku_id": "7",
//					"store_num": "0",
//					"spec_key": "",
//					"market_price": "3799.00",
//					"pro_no": "1258277"
//				}
//			},
//			"content": "<div id=\"J-detail-content\"><div class=\"content_tpl\"><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t559\/234\/1189763560\/239977\/c29d918b\/54bdbfc4N33a23778.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-1\" name=\"detail-tag-id-1\" text=\"产品特色\"><div class=\"formwork_bt_dz\">产品特色 &nbsp; &nbsp;<span class=\"s2\">Selling Point<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t661\/138\/1239513330\/20503\/2561f30d\/54bdbfd2N6b79dbea.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t553\/194\/1221887876\/133192\/decc42da\/54bf4d69N4678ece5.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t772\/254\/544717773\/120142\/f18c49ff\/54bdbfebNd5ed7bc5.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t460\/281\/625959465\/33159\/12d16ed3\/5476c384N7fae1906.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t682\/41\/405912948\/52183\/6167531b\/5465d655N723c6702.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t550\/248\/1445577821\/203338\/398eca5c\/54bf4d78N6602d22c.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t490\/37\/1187125580\/305595\/d63d6cac\/54bdc2d4N3b73608a.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t454\/75\/1196332270\/184729\/dfef2e2c\/54bdc016Ncf2b981e.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-10\" name=\"detail-tag-id-10\" text=\"产品功能\"><div class=\"formwork_bt_dz\">产品功能 &nbsp; &nbsp;<span class=\"s2\">Product Function<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t691\/214\/1193147575\/17943\/2847bc0f\/54bdc025N9d06b9b7.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t577\/217\/971108270\/318692\/f92a87ee\/54bdc396N62f3d5b4.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t745\/286\/562345884\/265230\/f08dcc10\/54bdc043Nfc2aa5cd.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_text\">酷开系统 极速 易用 好玩 重新定义电视的操作体验，人性化的交互界面，毫秒级快速响应，操作快，多任务极速切换，功能更强大，使用却更简单，微信推送、远程操控、像玩手机一样玩电视，酷开系统-专为电视而生!<\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t724\/292\/516195963\/230361\/9d9bf45b\/54bdc04fN023b776c.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t721\/200\/572211672\/32270\/dc57bdf2\/54bf4d94N49ae2f8d.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t670\/246\/1206569930\/171685\/984e3845\/54bdc06fN9bbaa3ed.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t559\/10\/1238336785\/159115\/cee06086\/54bdc07eNda39938a.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t619\/100\/1171127726\/243366\/703d31a8\/54bdc345N8b2ca21e.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_text\">专业解码采用DOLBY专业级音效解码，配备悬浮式立体音箱，声音还原真实，让您如同身临其境，酷开TV不需要配置外置音响，即可享受真实的影院效果。<\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t670\/274\/1205514512\/38733\/5341939f\/54bdc094N0b216bfd.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t610\/304\/1242119921\/124533\/1f46dafa\/54bdc0a3N68282a9b.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-23\" name=\"detail-tag-id-23\" text=\"售后服务\"><div class=\"formwork_bt_dz\">售后服务 &nbsp; &nbsp;<span class=\"s2\">After-Sales Service<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t571\/103\/1260534749\/45724\/e3ebb058\/54c38dc6N281a5d60.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t508\/122\/810507937\/306059\/4dd4209d\/548ab914N13c2038d.jpg\" class=\"\"\/><\/div><\/div><\/div><br\/><\/div>","id":"2","sell_price":"2799.00","title":"创维酷开(coocaa)K50J50英寸智能酷开系统八核网络平板液晶电视(黑色)","market_price":"3799.00","update_time":"1427358233","description":"创维酷开(coocaa)K50J50英寸智能酷开系统八核网络平板液晶电视(黑色)","service":"本产品全国联保，享受三包服务，质保期为：一年质保\r\n本产品提供上门安装调试、提供上门检测和维修等售后服务，自收到商品之日起，如您所购买家电商品出现质量问题，请先联系厂家进行检测，凭厂商提供的故障检测证明，在“我的京东-客户服务-返修退换货”页面提交退换申请，将有专业售后人员提供服务。京东承诺您：30天内可为您退货或换货，180天内无需修理直接换货，超过180天按国家三包规定享受服务。\r\n您可以查询本品牌在各地售后服务中心的联系方式，请点击这儿查询......\r\n\r\n品牌官方网站：http: \/\/www.skyworth.com\r\n售后服务电话：95105555","brand":"创维","images":[{"url":"http: \/\/www.haoyikuai.cn\/Upload\/File\/2015\/03\/26\/5513c196627c8.jpg"}],"eventBundlingList":[]}}]HttpResult [isSuccess=true, code=0, result={"goods_info":{"is_online":"1","comment_count":"0","spec":[],"score":5,"prom":null,"store_num":"0","packing_list":"遥控器*1、快速使用指南*1、保修证*1座架*1电视机*1电源线","is_favorite":false,"url":"http: \/\/www.haoyikuai.cn\/index.php?a=goods&id=2","sku_list":{"":{"sell_price":"2799.00","sku_id":"7","store_num":"0","spec_key":"","market_price":"3799.00","pro_no":"1258277"}},"content":"<divid=\"J-detail-content\"><div class=\"content_tpl\"><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t559\/234\/1189763560\/239977\/c29d918b\/54bdbfc4N33a23778.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-1\" name=\"detail-tag-id-1\" text=\"产品特色\"><div class=\"formwork_bt_dz\">产品特色 &nbsp; &nbsp;<span class=\"s2\">Selling Point<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t661\/138\/1239513330\/20503\/2561f30d\/54bdbfd2N6b79dbea.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t553\/194\/1221887876\/133192\/decc42da\/54bf4d69N4678ece5.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t772\/254\/544717773\/120142\/f18c49ff\/54bdbfebNd5ed7bc5.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t460\/281\/625959465\/33159\/12d16ed3\/5476c384N7fae1906.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t682\/41\/405912948\/52183\/6167531b\/5465d655N723c6702.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t550\/248\/1445577821\/203338\/398eca5c\/54bf4d78N6602d22c.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t490\/37\/1187125580\/305595\/d63d6cac\/54bdc2d4N3b73608a.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t454\/75\/1196332270\/184729\/dfef2e2c\/54bdc016Ncf2b981e.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-10\" name=\"detail-tag-id-10\" text=\"产品功能\"><div class=\"formwork_bt_dz\">产品功能 &nbsp; &nbsp;<span class=\"s2\">Product Function<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t691\/214\/1193147575\/17943\/2847bc0f\/54bdc025N9d06b9b7.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t577\/217\/971108270\/318692\/f92a87ee\/54bdc396N62f3d5b4.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t745\/286\/562345884\/265230\/f08dcc10\/54bdc043Nfc2aa5cd.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_text\">酷开系统 极速 易用 好玩 重新定义电视的操作体验，人性化的交互界面，毫秒级快速响应，操作快，多任务极速切换，功能更强大，使用却更简单，微信推送、远程操控、像玩手机一样玩电视，酷开系统-专为电视而生!<\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t724\/292\/516195963\/230361\/9d9bf45b\/54bdc04fN023b776c.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t721\/200\/572211672\/32270\/dc57bdf2\/54bf4d94N49ae2f8d.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t670\/246\/1206569930\/171685\/984e3845\/54bdc06fN9bbaa3ed.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t559\/10\/1238336785\/159115\/cee06086\/54bdc07eNda39938a.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t619\/100\/1171127726\/243366\/703d31a8\/54bdc345N8b2ca21e.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_text\">专业解码采用DOLBY专业级音效解码，配备悬浮式立体音箱，声音还原真实，让您如同身临其境，酷开TV不需要配置外置音响，即可享受真实的影院效果。<\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t670\/274\/1205514512\/38733\/5341939f\/54bdc094N0b216bfd.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t610\/304\/1242119921\/124533\/1f46dafa\/54bdc0a3N68282a9b.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork_bt\" id=\"detail-tag-id-23\" name=\"detail-tag-id-23\" text=\"售后服务\"><div class=\"formwork_bt_dz\">售后服务 &nbsp; &nbsp;<span class=\"s2\">After-Sales Service<\/span><\/div><\/div><div class=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t571\/103\/1260534749\/45724\/e3ebb058\/54c38dc6N281a5d60.jpg\" class=\"\"\/><\/div><\/div><divclass=\"formwork\"><div class=\"formwork_img\"><img data-lazyload=\"done\" alt=\"\"src=\"http:\/\/img20.360buyimg.com\/vc\/jfs\/t508\/122\/810507937\/306059\/4dd4209d\/548ab914N13c2038d.jpg\" class=\"\"\/><\/div><\/div><\/div><br\/><\/div>","id":"2","sell_price":"2799.00","title":"创维酷开(coocaa)K50J50英寸智能酷开系统八核网络平板液晶电视(黑色)","market_price":"3799.00","update_time":"1427358233","description":"创维酷开(coocaa)K50J50英寸智能酷开系统八核网络平板液晶电视(黑色)","service":"本产品全国联保，享受三包服务，质保期为：一年质保\r\n本产品提供上门安装调试、提供上门检测和维修等售后服务，自收到商品之日起，如您所购买家电商品出现质量问题，请先联系厂家进行检测，凭厂商提供的故障检测证明，在“我的京东-客户服务-返修退换货”页面提交退换申请，将有专业售后人员提供服务。京东承诺您：30天内可为您退货或换货，180天内无需修理直接换货，超过180天按国家三包规定享受服务。\r\n您可以查询本品牌在各地售后服务中心的联系方式，请点击这儿查询......\r\n\r\n品牌官方网站：http: \/\/www.skyworth.com\r\n售后服务电话：95105555","brand":"创维","images":[{"url":"http: \/\/www.haoyikuai.cn\/Upload\/File\/2015\/03\/26\/5513c196627c8.jpg"}],"eventBundlingList":[]}
	public static GoodsInfo json2Good(String json) {
		GoodsInfo goodsInfo = null;
		try {
			JSONObject jsonObject = new JSONObject(json);

			if (jsonObject.isNull("goods_info")) {
				goodsInfo = gson.fromJson(json, GoodsInfo.class);
			} else {

				goodsInfo = gson.fromJson(jsonObject
						.getJSONObject("goods_info").toString(),
						GoodsInfo.class);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return goodsInfo;
	}

	public static List<GoodsInfo> json2SimpleGoodList(String json) {
		try {

			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray;
			if (!jsonObject.isNull("goods_list")) {
				jsonArray = jsonObject.getJSONArray("goods_list");
			} else {
				jsonArray = new JSONArray(json);
			}

			List<GoodsInfo> list = new ArrayList<GoodsInfo>();

			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(json2Good(jsonArray.getString(i)));
			}
			return list;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ForgotIndentifierInfo json2ForgotIndentifierInfo(String json) {

		try {

			ForgotIndentifierInfo forgotIndentifierInfo = new ForgotIndentifierInfo();
			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("user_name"))
				forgotIndentifierInfo.setUser_name(jsonObject
						.getString("user_name"));
			if (!jsonObject.isNull("email"))
				forgotIndentifierInfo.setEmail(jsonObject.getString("email"));
			if (!jsonObject.isNull("user_id"))
				forgotIndentifierInfo.setUser_id(jsonObject.getInt("user_id"));
			if (!jsonObject.isNull("mobile"))
				forgotIndentifierInfo.setMobile(jsonObject.getString("mobile"));
			return forgotIndentifierInfo;
		} catch (Exception e) {
			Log.e(TAG, "error:" + e.getMessage());
		}
		return null;
	}

	public static ForgotPasswordToken json2ForgotPasswordToken(String json) {

		try {

			ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
			JSONObject jsonObject = new JSONObject(json);
			if (!jsonObject.isNull("token"))
				forgotPasswordToken.setToken("token");
			return forgotPasswordToken;
		} catch (Exception e) {
			Log.e(TAG, "error:" + e.getMessage());
		}
		return null;
	}

}
