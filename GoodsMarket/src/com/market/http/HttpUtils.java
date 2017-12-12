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
 * �������ڷ�װֱ�ӷ�������ķ���
 * 
 * @author ��ΰ��
 * 
 */
public class HttpUtils {

	private static final String TAG = "HttpUtils";

	/**
	 * ͨ��Post���������ݵ�������
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
		byte[] data = getRequestData(params, "utf-8").toString().getBytes();// ���������

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

			int response = httpURLConnection.getResponseCode(); // ��÷���������Ӧ��
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
				String result = dealResponseResult(inptStream); // �������������Ӧ���
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
	 * ��װ��������Ϣ
	 * 
	 * @param params
	 * @param encode
	 * @return
	 */
	public static StringBuffer getRequestData(Map<String, String> params,
			String encode) {
		StringBuffer stringBuffer = new StringBuffer(); // �洢��װ�õ���������Ϣ
		try {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				stringBuffer.append(entry.getKey()).append("=")
						.append(URLEncoder.encode(entry.getValue(), encode))
						.append("&");
			}
			stringBuffer.deleteCharAt(stringBuffer.length() - 1); // ɾ������һ��"&"
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer;
	}

	/**
	 * �������������Ӧ�������������ת�����ַ�����
	 * 
	 * @param inputStream
	 * @return
	 */ 
	public static String dealResponseResult(InputStream inputStream) {
		String resultData = null; // �洢������
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
