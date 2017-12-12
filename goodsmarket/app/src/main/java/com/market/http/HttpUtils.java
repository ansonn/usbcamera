package com.market.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.market.dbmanage.SharePrefrencesUtils;
import com.market.utils.AuthCode;

/**
 * 这里用于封装直接访问网络的方法
 * 
 * @author 陈伟斌
 * 
 */
public class HttpUtils {

	private static final String TAG = "HttpUtils";

	/**
	 * 通过Post请求发送数据到服务器
	 * 
	 * @param serverUrl
	 * @param params
	 * @return
	 */
	public static JSONObject sendData2Server(String serverUrl,
			Map<String, String> params, boolean isSaveCookie,
			boolean isSendCookie, Context context) {

		params.put("_token", AuthCode.generalTokenCode());
		params.put("_app_name", AuthCode.APP_ID);
		byte[] data = getRequestData(params, "utf-8").toString().getBytes();// 获得请求体

		SharePrefrencesUtils sharePrefrencesUtils = null;
		if (isSaveCookie || isSendCookie) {
			sharePrefrencesUtils = new SharePrefrencesUtils(context);
		}

		try {

			URL url = new URL(serverUrl);

			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setUseCaches(false);

			if (isSendCookie) {
				String savedCookie = sharePrefrencesUtils.getLoginCookie();
				if (savedCookie != null) {
					httpURLConnection.addRequestProperty("Cookie",
							sharePrefrencesUtils.getLoginCookie());
				}
			}

			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			com.market.utils.Log.d("httpURLConnection "
					+ httpURLConnection.getRequestProperties().toString());
			OutputStream outputStream = httpURLConnection.getOutputStream();
			outputStream.write(data);

			com.market.utils.Log.d(new String(data));

			int response = httpURLConnection.getResponseCode(); // 获得服务器的响应码
			Map<String, List<String>> headerMap = httpURLConnection
					.getHeaderFields();

			if (isSaveCookie) {

				List<String> cookieList = headerMap.get("Set-Cookie");
				String cookie = "";
				for (String str : cookieList) {
					if (!TextUtils.isEmpty(cookie)) {
						cookie += ";";
					}
					cookie += str;
				}
				sharePrefrencesUtils.saveLoginCookie(cookie);

			}

			if (response == HttpURLConnection.HTTP_OK) {
				InputStream inptStream = httpURLConnection.getInputStream();
				String result = dealResponseResult(inptStream); // 处理服务器的响应结果
				com.market.utils.Log.d(TAG, result);

				JSONObject resJsonObject = null;
				try {

					resJsonObject = new JSONObject(result);
				} catch (Exception e) {
					e.printStackTrace();
				}

				return resJsonObject;
			}
		} catch (Exception e) {
			Log.e(TAG, "sendData2Server error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 封装请求体信息
	 * 
	 * @param params
	 * @param encode
	 * @return
	 */
	public static StringBuffer getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // 存储封装好的请求体信息
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // 删除最后的一个"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/**
	 * 处理服务器的响应结果（将输入流转化成字符串）
	 * 
	 * @param inputStream
	 * @return
	 */ 
	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null; // 存储处理结果
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		try {
			while ((len = inputStream.read(data)) != -1) {
				byteArrayOutputStream.write(data, 0, len);
			}
			resultData = new String(byteArrayOutputStream.toByteArray());
			inputStream.close();
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultData;
	}

}
