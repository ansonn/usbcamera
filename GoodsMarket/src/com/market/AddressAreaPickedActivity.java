package com.market;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.AreaAdapter;
import com.market.bean.AreaBean;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.AddressHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;

public class AddressAreaPickedActivity extends BaseActivity implements
		OnHttpCallBack , OnClickListener{
	private final String ADDRESSID_TAG = "address_id";
	private final String LEVEL_TAG = "level";
	private final String NAME_TAG = "name";
	private final String PID_TAG = "pid";
	private final String SIDS_TAG = "sids";
	private final String PROVINCE_TAG = "province";
	private final String ADDRESS_TAG = "address";
	private final String UPDATETIME_TAG = "update_time";
	private final String ENDCODE = "###";
	/** 　JSON分割符　 **/
	private final String SPLITER = ",";

	private final String filename = "areaList";
	private String filePath;
	private File file;

	private ListView area_listview;
	private LinearLayout ll_header;
	private TextView tv_headerinfo;
	private LinearLayout ll_header_inner;
	private TextView tv_headerinfo_inner;
	private AreaAdapter areaAdapter;
	private boolean isNew = false;
	private boolean isCompleted = false;

	private ArrayList<AreaBean> areabeanlist;
	private ArrayList<AreaBean> provinceStringlist;
	private int[] provinceId;
	private int stackIndex = 0;
	private AreaBean[] addressStack = new AreaBean[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TOStringDO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_address_area_picked);
		filePath = this.getCacheDir().getPath();
		
		initData();
		initView();
		initTitle();
		Log.d(" onCreate over ");
	}

	private void initTitle()
	{
		this.setDeftualtHeaderLeftButton(this);
	}
	
	private void initView() {
		ll_header_inner = (LinearLayout) this
				.findViewById(R.id.ll_areaActivitylistnavigator);
		tv_headerinfo_inner = (TextView) this
				.findViewById(R.id.tv_areaActivitylistnavigator);
		areaAdapter = new AreaAdapter(this);
		area_listview = (ListView) this.findViewById(R.id.lv_address_area_list);
		area_listview.setAdapter(areaAdapter);
		area_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (areaAdapter == null)
					return;

				AreaBean areabean = areaAdapter.getArealist().get(position);
				pushAddressToStack(areabean);

				addHeader(getAddressStackDesc());
				
				if (areabean.sids == null || areabean.sids.length < 1) {
					// 到达叶子，可以返回到跳转前页
					Intent result = new Intent();
					result.putExtra(Constants.AREA_BACKTO_ADDRESSMODIFICATION, getAddressStackDesc());
					result.putExtra(Constants.AREA_BACKTO_ADDRESSIDMODIFICATION, getAddressStackDescID());
					setResult(0, result);
					finish();
					return;
				}
				setActivityTitle(areabean.getName());
				changeList(getChildrenById(areabean.address_id));
			}

		});
	}

	private void addHeader(String msg) {
		if (msg == null) {
			ll_header_inner.setVisibility(View.GONE);
		}
		else if (tv_headerinfo_inner != null) {
			tv_headerinfo_inner.setText(msg);
			ll_header_inner.setVisibility(View.VISIBLE);
		}
	}

	private void initData() {
		provinceStringlist = new ArrayList<AreaBean>();
		filePath += "/" + filename;
		file = new File(filePath);
		if (file.exists()) {
			areabeanlist = new ArrayList<AreaBean>();
			getAllAreas(file);
		} else {
			try {
				file.createNewFile();
				AddressHttpOperation.getAreaList(
						Constants.CALLBACK_FLAG_GET_AREALIST, "", this,this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void changeList(ArrayList<AreaBean> al)
	{
		areaAdapter.setArealist(al);
		areaAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 在LIST中得到指定ID的子实例
	 * 
	 * @param id
	 * @return
	 */
	private ArrayList<AreaBean> getChildrenById(int id) {
		if (areabeanlist == null)
			return null;
		ArrayList<AreaBean> beans = new ArrayList<AreaBean>();

		for (int i = 0; i < areabeanlist.size(); i++) {
			if (areabeanlist.get(i).pid == id) {
				beans.add(areabeanlist.get(i));
				Log.d("beans " + areabeanlist.get(i));
			}
		}
		return beans;
	}

	/**
	 * 从LIST获得特定的IDS
	 * 
	 * @param ids
	 * @return
	 */
	private ArrayList<AreaBean> getBeansByIds(int[] ids) {
		if (areabeanlist == null)
			return null;
		ArrayList<AreaBean> beans = new ArrayList<AreaBean>();

		for (int j = 0; j < ids.length; j++)
			for (int i = 0; i < areabeanlist.size(); i++) {
				if (areabeanlist.get(i).address_id == ids[j]) {
					beans.add(areabeanlist.get(i));
					break;
				}
			}
		return beans;
	}

	/**
	 * 从List里得到省份
	 * 
	 * @return
	 */
	private ArrayList<AreaBean> getProvince() {
		if (areabeanlist == null)
			return null;
		ArrayList<AreaBean> beans = new ArrayList<AreaBean>();

		for (int i = 0; i < areabeanlist.size(); i++) {
			if (areabeanlist.get(i).level == 1) {
				Log.d("areabeanlist.get(i) " + areabeanlist.get(i).toString());
				beans.add(areabeanlist.get(i));
			}
		}
		return beans;
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		JSONObject jsonObject;
		if (httpResult == null)
			return;
		if (requestId == Constants.CALLBACK_FLAG_GET_AREALIST) {
			try {
				jsonObject = new JSONObject(httpResult.getResult());
				String updatetime = null;
				/**
				 * 从省份向下递归初始化地址
				 */
				if (jsonObject.has(PROVINCE_TAG)) {
					JSONArray jsonarea = jsonObject.getJSONArray(PROVINCE_TAG);
					final int LENGTHOFAREA = jsonarea.length();

					provinceId = new int[LENGTHOFAREA];
					for (int i = 0; i < provinceId.length; i++) {
						provinceId[i] = jsonarea.getInt(i);
						Log.d(provinceId[i] + "");
					}
				}

				if (jsonObject.has(UPDATETIME_TAG)) {
					updatetime = jsonObject.getString(UPDATETIME_TAG);
				}

				if (jsonObject.has(ADDRESS_TAG)) {
					JSONObject address = jsonObject.getJSONObject(ADDRESS_TAG);
					if (provinceId != null) {
						// 从新初始化areabeanlist
						areabeanlist = new ArrayList<AreaBean>();

						WriteFileTask wftask = new WriteFileTask(
								provinceStringlist, address, areabeanlist,
								updatetime);
						wftask.execute();
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (requestId == Constants.CALLBACK_FLAG_CHECK_AREALIST) {
			// jsonObject = new JSONObject(httpResult.getResult());
			Log.d("jsonObject " + httpResult.getResult());
			Log.d("jsonObject getCode" + httpResult.getCode());
			if ((httpResult.getCode() - 1) % 10 == 0) {

				ReadFileTask rfTask = new ReadFileTask(provinceStringlist,
						areabeanlist);
				rfTask.execute();
			}

		}

	}

	private int count = 0;

	/**
	 * 从JSON里获得AREA
	 * 
	 * @param provinceIDs
	 * @param jsonObject
	 * @throws JSONException
	 */
	private void getAllArea(int[] provinceIDs, JSONObject jsonObject)
			throws JSONException {
		if (jsonObject == null)
			return;
		for (int i = 0; i < provinceIDs.length; i++) {
			String addressname = provinceIDs[i] + "";
			if (jsonObject.has(addressname)) {
				JSONObject jsonpo = jsonObject.getJSONObject(addressname);
				AreaBean ab = getBean(jsonpo);
				Log.d("add " + ab.toString());
				areabeanlist.add(ab);
				if (ab.sids != null) {
					getAllArea(ab.sids, jsonObject);
				}
			}
		}
	}

	/**
	 * 检查文件的更新状态和获得文本的内容，并更新
	 * 
	 * @param file
	 */
	private void getAllAreas(File file) {
		StringBuilder sb = new StringBuilder();
		byte[] bytes = new byte[ENDCODE.length()];
		int byteread = 0;
		try {
			RandomAccessFile randomFile = new RandomAccessFile(file, "r");
			long fileLength = randomFile.length();

			// ENDCODE长度+换行+结束
			int tailblength = ENDCODE.length() + 1;
			if (fileLength > tailblength) {
				randomFile.seek(fileLength - tailblength);
				// 检查文本的完整性
				while ((byteread = randomFile.read(bytes)) != -1)
					;
				Log.d("byteread :" + byteread);
				Log.d("bytes :" + new String(bytes));

				String tail = new String(bytes).trim();
				Log.d("(bytes.toString().trim()).equals(ENDCODE) :"
						+ tail.equals(ENDCODE));
				if ((tail).equals(ENDCODE)) {
					isCompleted = true;
				}
			}
			if (isCompleted) {
				// 第一行为UpdateTime
				randomFile.seek(0);
				byteread = 0;
				while ((byteread = randomFile.read()) != '\n') {
					sb.append((char) byteread);
				}
				Log.d("sb " + sb.toString());
			}
			randomFile.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 不完整，需要重新获取
		if (!isCompleted) {
			AddressHttpOperation.getAreaList(
					Constants.CALLBACK_FLAG_GET_AREALIST, "", this,this);
			return;
		} else {
			AddressHttpOperation
					.getAreaList(Constants.CALLBACK_FLAG_CHECK_AREALIST,
							sb.toString(), this,this);
		}

	}

	/**
	 * 从JSON转来实例
	 * 
	 * @param jo
	 * @return
	 * @throws JSONException
	 */
	private AreaBean getBean(JSONObject jo) throws JSONException {
		AreaBean ab = new AreaBean();
		if (jo.has(ADDRESSID_TAG)) {
			ab.address_id = jo.getInt(ADDRESSID_TAG);
		}
		if (jo.has(LEVEL_TAG)) {
			ab.level = jo.getInt(LEVEL_TAG);
		}
		if (jo.has(NAME_TAG)) {
			ab.name = jo.getString(NAME_TAG);
		}
		if (jo.has(PID_TAG)) {
			ab.pid = jo.getInt(PID_TAG);
		}
		if (jo.has(SIDS_TAG)) {
			JSONArray ja = jo.getJSONArray(SIDS_TAG);
			final int LENGTHOFJA = ja.length();
			ab.sids = new int[LENGTHOFJA];
			for (int i = 0; i < ab.sids.length; i++) {
				ab.sids[i] = ja.getInt(i);
			}
		}
		return ab;
	}

	/**
	 * 从字符串转为实例
	 * 
	 * @param line
	 * @return
	 */
	private AreaBean getBean(String line) {
		AreaBean ab = new AreaBean();
		ab.String2Bean(line);
		return ab;
	}

	/**
	 * 读取本地文件，并初始化
	 * 
	 * @author pumkid
	 *
	 */
	class ReadFileTask extends AsyncTask<Integer, Integer, Void> {
		private ArrayList<AreaBean> provinceStringlist;
		private ArrayList<AreaBean> areabeanlist;

		ReadFileTask(ArrayList<AreaBean> provinceStringlist,
				ArrayList<AreaBean> areabeanlist) {
			this.provinceStringlist = provinceStringlist;
			this.areabeanlist = areabeanlist;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProcessDialog();
		}

		@Override
		protected Void doInBackground(Integer... params) {
			FileReader filereader;
			try {
				filereader = new FileReader(file);
				BufferedReader br = new BufferedReader(filereader);
				// 第一行为UpdateTime
				String update_time = br.readLine();
				String dataLine;
				while (!(dataLine = br.readLine()).equals(ENDCODE)
						&& dataLine != null) {
					areabeanlist.add(getBean(dataLine));
				}
				br.close();
				filereader.close();
				Log.d("RDONE!!!!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			changeList(getProvince());
			closeProcessDialog();
		}

	}

	/**
	 * 把在网上的的地址读下来
	 * 
	 * @author pumkid
	 *
	 */
	class WriteFileTask extends AsyncTask<Integer, Integer, Void> {
		private ArrayList<AreaBean> provinceStringlist;
		private ArrayList<AreaBean> areabeanlist;
		private String update_time;
		private JSONObject address;

		WriteFileTask(ArrayList<AreaBean> provinceStringlist,
				JSONObject address, ArrayList<AreaBean> areabeanlist,
				String update_time) {
			this.address = address;
			this.provinceStringlist = provinceStringlist;
			this.areabeanlist = areabeanlist;
			this.update_time = update_time;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProcessDialog();
		}

		@Override
		protected Void doInBackground(Integer... params) {
			try {
				getAllArea(provinceId, address);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			FileWriter filewriter;
			try {
				filewriter = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(filewriter);
				bw.write(update_time);
				bw.write('\n');
				for (int i = 0; i < areabeanlist.size(); i++) {
					bw.write(areabeanlist.get(i).toString());
				}
				bw.write(ENDCODE);
				bw.flush();
				bw.close();
				filewriter.close();
				Log.d("WDONE!!!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			super.onProgressUpdate(progress);
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			changeList(getBeansByIds(provinceId));
			closeProcessDialog();
		}

	}

	/**
	 * 添加地址
	 * 
	 * @param address
	 */
	private void pushAddressToStack(AreaBean address) {
		if (stackIndex < (addressStack.length)) {
			// 一定是父子关系才能被添加进来
			if (stackIndex > 1
					&& addressStack[stackIndex - 1].address_id != address.pid)
				return;
			addressStack[stackIndex++] = address;

		}
	}

	/**
	 * 删除地址
	 * 
	 * @param address
	 * @return　出栈前的index
	 */
	private int popAddressToStack() {
		int perstackIndex = stackIndex;
		if (stackIndex > 0)
			addressStack[--stackIndex] = null;
		return perstackIndex;
	}

	private AreaBean getStackHead()
	{
		if (stackIndex > 0)
		return addressStack[stackIndex-1];
		return null;
	}
	/**
	 * 地址字符串化
	 * 
	 * @return
	 */
	private String getAddressStackDesc() {
		if (addressStack[0] == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stackIndex; i++) {
			sb.append(addressStack[i].name);
		}
		Log.d("addressStack is " + sb.toString());
		return sb.toString();
	}
	
	/**
	 * 地址字符串化
	 * 
	 * @return
	 */
	private String getAddressStackDescID() {
		if (addressStack[0] == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < stackIndex; i++) {
			sb.append(addressStack[i].address_id);
			sb.append(Constants.COMMON_TEXTSPLITER);
		}
		if(sb.length() > 1)
		sb.deleteCharAt(sb.length() - 1);
		Log.d("addressStack is " + sb.toString());
		return sb.toString();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId())
		{
			case ResourceLocation.BUTTON_HEADER_LEFT:
				int index = popAddressToStack();
				if(index == 0)
				{
					this.finish();
				}
				AreaBean tempbean = getStackHead();
				if(tempbean != null)
				{
					changeList(getChildrenById(tempbean.address_id));
					addHeader(getAddressStackDesc());
				}
				else
				{
					changeList(getProvince());
					addHeader(getAddressStackDesc());
				}
				
			break;
		}
	}

	

	
	
}
