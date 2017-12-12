package com.market;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.haoyikuai.shop.android.R;
import com.market.adapter.AddressAdapter;
import com.market.bean.Address;
import com.market.bean.AreaBean;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.AddressHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;

public class AddressListActivity extends BaseActivity implements
		OnHttpCallBack, OnClickListener {

	private ArrayList<Address> addresslist;
	private AddressAdapter addapter;
	private ListView lv;
	/**
	 * "zip": "518000", "address": "1233333333333", "county": { "address_id":
	 * "10003235", "name": "西湖区", "level": "3" }, "address_id": "45", "name":
	 * "312", "province": { "address_id": "130", "name": "浙江", "level": "1" },
	 * "is_default": "1", "user_id": "5", "city": { "address_id": "1000323",
	 * "name": "杭州", "level": "2" }, "mobile": "13800138000"
	 */
	private final String s_zip = "zip";
	private final String s_address = "address";
	private final String s_county = "county";
	private final String s_i_address_id = "address_id";
	private final String s_i_name = "name";
	private final String s_i_level = "level";
	private final String s_address_id = "address_id";
	private final String s_name = "name";
	private final String s_province = "province";
	private final String s_is_default = "is_default";
	private final String s_user_id = "user_id";
	private final String s_city = "city";
	private final String s_mobile = "mobile";

	private Resources resources;

	public enum LISTSTYLE {
		MANGER_LIST, // default
		SHOW_LIST
	}

	private LISTSTYLE liststyle = LISTSTYLE.MANGER_LIST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_address_list);
		initdate();
		initView();
		AddressHttpOperation.getAddressList(this,
				Constants.CALLBACK_FLAG_GET_ADDRESSLIST, this);
		initTitle();
	}

	private void initdate(){
		resources = this.getResources();
		Intent intent = this.getIntent();
		int style = intent.getIntExtra(Constants.STYLEEXTRA, Constants.INTEGER_ADDRESSLISTACTIVITY_LISTSTYLEM);
		if(style == Constants.INTEGER_ADDRESSLISTACTIVITY_LISTSTYLEM)
			liststyle = LISTSTYLE.MANGER_LIST;
		else
			liststyle = LISTSTYLE.SHOW_LIST;
	}
	
	private void initView() {
		lv = (ListView) this.findViewById(R.id.lv_address_area_list);
		addapter = new AddressAdapter(this, liststyle);
		lv.setAdapter(addapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> viewadapt, View view,
					int index, long postion) {
				// TODO Auto-generated method stub
				Address addresst = addapter.getAddressList().get(index);
				if(liststyle == LISTSTYLE.MANGER_LIST)
				{
					Intent intent = new Intent();
					intent.putExtra(Constants.ADDRESS_TO_ADDRESSIDMODIFICATION,
							addresst);
					intent.setClass(AddressListActivity.this,
							AddressModificationActivity.class);
					startActivityForResult(intent,
							Constants.CODE_ADDRESS_ASK_ADDRESSIDMODIFICATION);
				}
				else if(liststyle == LISTSTYLE.SHOW_LIST)
				{
					Intent intent = new Intent();
					intent.putExtra(Constants.OBJECTEXTRA,
							addresst);
					AddressListActivity.this.setResult(Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE0, intent);
					finish();
				}
			}

		});
	}

	private void initTitle() {
		String title = resources.getString(R.string.address_magaddresslist);
		if(	liststyle == LISTSTYLE.SHOW_LIST )
		{
			title = resources.getString(R.string.address_addresslist);
		}
		this.setActivityTitle(title);
		this.setDeftualtHeaderLeftButton();
		this.setStringHeaderRightButton(resources.getString(R.string.add), this);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if (requestId == Constants.CALLBACK_FLAG_GET_ADDRESSLIST) {
			if (httpResult != null) {
				Log.d("httpResult " + httpResult + " httpResult code "
						+ httpResult.getCode());

				try {
					JSONObject obj = new JSONObject(httpResult.getResult());
					if (obj.has("address_list")) {
						addresslist = this.getAddressList(obj
								.getJSONArray("address_list"));
						Log.d(addresslist.toString());
						addapter.setAddressList(addresslist);
						addapter.notifyDataSetChanged();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Address> getAddressList(JSONArray jsonArray)
			throws JSONException {
		ArrayList<Address> addresslist = new ArrayList<Address>();
		if (jsonArray != null)
			for (int i = 0; i < jsonArray.length(); i++) {
				addresslist.add(getAddress(jsonArray.getJSONObject(i)));
			}
		return addresslist;
	}

	public Address getAddress(JSONObject jsonobject) throws JSONException {
		Address address = new Address();
		if (jsonobject.has(this.s_zip)) {
			address.setZip(jsonobject.getString(s_zip));
		}
		if (jsonobject.has(this.s_address)) {
			address.setAddress(jsonobject.getString(s_address));
		}
		if (jsonobject.has(this.s_county)) {
			address.setCounty(getSimpleAreaBean(jsonobject
					.getJSONObject(s_county)));
		}
		if (jsonobject.has(this.s_address_id)) {
			address.setAddress_id(jsonobject.getLong(s_address_id));
		}
		if (jsonobject.has(this.s_name)) {
			address.setName(jsonobject.getString(s_name));
		}
		if (jsonobject.has(this.s_province)) {
			address.setProvince(getSimpleAreaBean(jsonobject
					.getJSONObject(s_province)));
		}
		if (jsonobject.has(this.s_is_default)) {
			address.setIs_default(jsonobject.getInt(s_is_default));
		}
		if (jsonobject.has(this.s_user_id)) {
			address.setUser_id(jsonobject.getLong(s_user_id));
		}
		if (jsonobject.has(this.s_city)) {
			address.setCity(getSimpleAreaBean(jsonobject.getJSONObject(s_city)));
		}
		if (jsonobject.has(this.s_mobile)) {
			address.setMobile(jsonobject.getString(s_mobile));
		}
		return address;
	}

	private AreaBean getSimpleAreaBean(JSONObject jsonobject)
			throws JSONException {
		AreaBean areaBean = new AreaBean();
		if (jsonobject.has(this.s_i_address_id)) {
			areaBean.address_id = jsonobject.getInt(this.s_i_address_id);
		}
		if (jsonobject.has(this.s_i_level)) {
			areaBean.level = jsonobject.getInt(this.s_i_level);
		}
		if (jsonobject.has(this.s_i_name)) {
			areaBean.name = jsonobject.getString(this.s_i_name);
		}
		return areaBean;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case ResourceLocation.BUTTON_HEADER_RIGHT:
			Intent intent = new Intent();
			intent.setClass(this, AddressModificationActivity.class);
			this.startActivityForResult(intent,
					Constants.CODE_ADDRESS_ASK_ADDRESSIDMODIFICATION);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constants.CODE_ADDRESS_ASK_ADDRESSIDMODIFICATION
				&& resultCode == Constants.CODE_ADDRESSIDMODIFICATION_BACKTO_ADDRESS)
			AddressHttpOperation.getAddressList(this,
					Constants.CALLBACK_FLAG_GET_ADDRESSLIST, this);
	}

}
