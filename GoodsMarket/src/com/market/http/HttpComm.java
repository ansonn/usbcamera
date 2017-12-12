package com.market.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.market.BaseActivity;
import com.market.utils.AuthCode;
import com.market.utils.Constants;

/**
 * 
 * 这里用于封装所有访问http相关的内容
 * 
 * @author 陈伟斌
 * 
 */
public class HttpComm {

	protected static ExecutorService executorService = Executors
			.newCachedThreadPool();

	public static final String TAG = "HttpComm";

	public static void login(String userName, String password,
			OnHttpCallBack callBack, int requestId, Context context) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "login");
		argMap.put("user_name", userName);
		argMap.put("password", password);

		asyncAccessHttp(argMap, requestId, callBack, true, false, context);

	}

	public static void getAbountUsInfo(String appName, OnHttpCallBack callBack,
			int requestId, Context context) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "about");
		argMap.put("name", appName);
		asyncAccessHttp(argMap, requestId, callBack, true, false, context);

	}

	public static void submitFeedback(String content, OnHttpCallBack callBack,
			int requestId, Context context) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "addFeedback");
		argMap.put("type", "1");
		asyncAccessHttp(argMap, requestId, callBack, true, false, context);

	}

	public static void checkUpdate(int versionCode, OnHttpCallBack callBack,
			int requestId, Context context) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "checkVersion");
		argMap.put("name", AuthCode.APP_ID);
		argMap.put("version_code", versionCode + "");
		asyncAccessHttp(argMap, requestId, callBack, true, false, context);

	}

	public static void register(String userName, String password,
			String mobile, String mobile_code, String email, OnHttpCallBack callBack,
			int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "register");
		argMap.put("user_name", userName);
		argMap.put("password", password);
		argMap.put("email", email);
		argMap.put("mobile", mobile);
		argMap.put("verify_code", mobile_code);

		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getRongYunToken(OnHttpCallBack callBack,
			int requestId) {
		
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getRongCloudToken");
		asyncAccessHttp(argMap, requestId, callBack);
		
	}
	public static void getRegVerify(String mobile, OnHttpCallBack callBack,
			int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "getRegVerifyCode");
		argMap.put("mobile", mobile);
		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getForgotInfo(String identifier,
			OnHttpCallBack callBack, int requestId) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "forgetPassword");
		argMap.put("identifier", identifier);
		asyncAccessHttp(argMap, requestId, callBack);

	}

	/**
	 * 获取分类列表
	 * 
	 * @return
	 */
	public static void getCategoryList(OnHttpCallBack callBack, int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getCategoryList");

		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getGoodList(int pageIndex, int pageCount, int sortType,
			OnHttpCallBack callBack, int requestId) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getGoodsList");
		argMap.put("page_index", pageIndex + "");
		argMap.put("list_row", pageCount + "");
		argMap.put("sort", sortType + "");

		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getRecommendCategoryList(OnHttpCallBack callBack,
			int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getRecommendCategoryList");

		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getAdList(OnHttpCallBack callBack, int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Ad");
		argMap.put("c", "public_getAd");
		argMap.put("flag", "mobile_index_slider");

		asyncAccessHttp(argMap, requestId, callBack);

	}

	/**
	 * 取得相关类目的商品列表
	 * 
	 * @param categoryId
	 * @param callBack
	 * @param list_row
	 * @param page_index
	 * @param sortType
	 * @param brand
	 *            品牌id
	 * @param price
	 *            价格列表
	 * @param specs
	 *            规格列表
	 * @param attr
	 *            属性列表
	 * @param requestId
	 */
	public static void getGoodListByCategoryId(int categoryId,
			OnHttpCallBack callBack, int list_row, int page_index,
			int sortType, int brand, String[] price, String[] specs,
			String[] attr, int requestId) {

		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getGoodsListByCategoryId");

		argMap.put("page_index", page_index + "");
		argMap.put("category_id", categoryId + "");

		argMap.put("list_row", list_row + "");

		if (sortType >= 0) {
			argMap.put("sort", sortType + "");
		}
		if (brand >= 0) {
			argMap.put("brand", brand + "");
		}

		if (price != null) {
			argMap.put("price", Arrays.toString(price));
		}
		if (specs != null) {
			argMap.put("specs", Arrays.toString(specs));
		}
		if (attr != null) {
			argMap.put("attr", Arrays.toString(attr));
		}
		asyncAccessHttp(argMap, requestId, callBack);
	}

	public static void getForgotVFCode(String identifier, String mothend,
			OnHttpCallBack callBack, int requestId) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "forgetPasswordSendVerify");
		argMap.put("identifier", identifier);
		argMap.put("method", mothend);// 验证方式(email,mobile)
		asyncAccessHttp(argMap, requestId, callBack);

	}

	/**
	 * @summary 提交验证码后，如果正确，得到一个Token
	 * @param identifier
	 * @param verify_code
	 * @param callBack
	 * @param requestId
	 */
	public static void postForgotToken(String identifier, String verify_code,
			OnHttpCallBack callBack, int requestId) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Public");
		argMap.put("c", "forgetPasswordCheckVerify");
		argMap.put("identifier", identifier);
		argMap.put("verify_code", verify_code);// 验证方式(email,mobile)
		asyncAccessHttp(argMap, requestId, callBack);

	}

	public static void getGoodsInfo(String id, OnHttpCallBack callBack,
			int requestId, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getGoodsInfo");
		argMap.put("id", id);
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getVoucherList(OnHttpCallBack callBack, int requestId,
			Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "getVoucherList");

		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void activeVoucher(int requestId, String key,
			OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "User");
		argMap.put("c", "activeVoucher");
		argMap.put("key", key);

		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void getGoodsCommentList(int page_index, int list_row, long id, int requestId, OnHttpCallBack callBack, Context context) {
		Map<String, String> argMap = new HashMap<String, String>();
		argMap.put("time", System.currentTimeMillis() + "");
		argMap.put("g", Constants.PROJECT_ID);
		argMap.put("m", "Goods");
		argMap.put("c", "public_getGoodsCommentList");
		argMap.put("id", id+"");
		argMap.put("page_index", page_index+"");
		argMap.put("list_row", list_row+"");
		asyncAccessHttp(argMap, requestId, callBack, false, true, context);
	}

	public static void asyncAccessHttp(final Map<String, String> args,
			final int requestId, final OnHttpCallBack callBack) {
		asyncAccessHttp(args, requestId, callBack, false, false, null);
	}

	/**
	 * 需在UI线程中调用 callBack
	 * 
	 * @param args
	 * @param requestId
	 * @param callBack
	 */
	public static void asyncAccessHttp(final Map<String, String> args,
			final int requestId, final OnHttpCallBack callBack,
			final boolean isSaveCookie, final boolean isSendCookie,
			final Context context) {

		AsyncTask<Map<String, String>, Void, HttpResult> asyncTask = new AsyncTask<Map<String, String>, Void, HttpResult>() {

			@Override
			protected HttpResult doInBackground(Map<String, String>... params) {

				JSONObject result = null;

				result = HttpUtils.sendData2Server(Constants.SERVER_URL,
						params[0], isSaveCookie, isSendCookie, context);
				Log.d("pumkid_request", args.toString());
				return convertJson2HttpResult(result);
			}

			@Override
			protected void onPostExecute(HttpResult result) {
				if (result == null) {
					result = new HttpResult();
					result.setSuccess(false);
					result.setResult("No Network");
				}
				callBack.onHttpCallBack(requestId, result);
				super.onPostExecute(result);
				if (result.getResult() != null)
					Log.d("pumkid_responsed", result.getResult());
			}

		};
		asyncTask.execute(args);

	}

	/**
	 * 将json转成 HttpResult对象
	 * 
	 * @param jsonObject
	 * @param type
	 * @return
	 */

	protected static HttpResult convertJson2HttpResult(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}
		HttpResult httpResult = new HttpResult();
		try {

			if (!jsonObject.isNull("code")) {
				httpResult.setCode(jsonObject.getInt("code"));
			}
			if (!jsonObject.isNull("status")) {
				httpResult.setSuccess(jsonObject.getInt("status") == 1);
			}
			if (!jsonObject.isNull("content")) {
				String retContent = jsonObject.getString("content");

				httpResult.setResult(retContent.toString());
			}
			return httpResult;
		} catch (Exception e) {
			Log.e(TAG, "error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
