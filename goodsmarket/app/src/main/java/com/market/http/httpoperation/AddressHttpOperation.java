package com.market.http.httpoperation;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.market.bean.Address;
import com.market.http.HttpComm;
import com.market.http.OnHttpCallBack;
import com.market.utils.Constants;

public class AddressHttpOperation extends HttpComm {
	public static void getAreaList(final int requestId, String update_time,
			OnHttpCallBack callBack,Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "checkAddress");
		argMap.put("update_time", update_time);
		asyncAccessHttp(argMap, requestId, callBack,false,true,context);
	}

	public static void getAddressList(OnHttpCallBack callBack, int requestId,Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getAddressList");
		asyncAccessHttp(argMap, requestId, callBack,false,true,context);
	}
	
	public static void postAddressEditadble(OnHttpCallBack callBack, int requestId, Address address,Context context)
	{
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "editAddress");
		argMap.put("address_id", address.getAddress_id()+"");
		argMap.put("province", address.getProvince().address_id+"");
		argMap.put("city", address.getCity().address_id+"");
		argMap.put("county", address.getCounty().address_id+"");
		argMap.put("address", address.getAddress());
		argMap.put("zip", address.getZip());
		argMap.put("name", address.getName());
		argMap.put("mobile", address.getMobile());
		argMap.put("is_default", address.getIs_default()+"");
		asyncAccessHttp(argMap, requestId, callBack,false,true,context);
	}
}
