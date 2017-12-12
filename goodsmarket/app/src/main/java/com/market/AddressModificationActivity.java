package com.market;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.bean.Address;
import com.market.bean.AreaBean;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.AddressHttpOperation;
import com.market.utils.Constants;
import com.market.utils.Log;
import com.market.utils.ResourceLocation;
import com.market.view.SwitchView;

public class AddressModificationActivity extends BaseActivity implements
		OnClickListener, OnHttpCallBack {
	private Resources resource;
	private SwitchView sv_addressSetToDefault;
	private EditText et_address_client_name;
	private EditText et_address_client_code;
	private EditText et_address_client_phone;
	private EditText et_address_client_adddesc;
	private TextView tv_address_client_area;
	private View rl_address_client_area;
	private Intent intentGetArea;
	private boolean isCompleted = false;
	private Address addressnew;
	private String addressnewids;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_address_modify);
		initTitle();
		initView();
		initData();
	}

	private void initView() {
		sv_addressSetToDefault = (SwitchView) this
				.findViewById(R.id.sv_addressSetToDefault);
		et_address_client_name = (EditText) this
				.findViewById(R.id.et_address_client_name);
		et_address_client_code = (EditText) this
				.findViewById(R.id.et_address_client_code);
		et_address_client_phone = (EditText) this
				.findViewById(R.id.et_address_client_phone);
		et_address_client_adddesc = (EditText) this
				.findViewById(R.id.et_address_client_adddesc);
		tv_address_client_area = (TextView) this
				.findViewById(R.id.tv_address_client_area);
		rl_address_client_area = this.findViewById(R.id.rl_address_client_area);

		rl_address_client_area.setOnClickListener(this);

	}

	private void initTitle() {
		resource = this.getResources();
		this.setActivityTitle(resource.getString(R.string.address_add_modify));
		this.setDeftualtHeaderLeftButton();
		this.setStringHeaderRightButton(resource.getString(R.string.confirm),
				this);
	}

	private void initData()
	{
		
		Intent intent = this.getIntent();
		addressnew = (Address)intent.getSerializableExtra(Constants.ADDRESS_TO_ADDRESSIDMODIFICATION);
		if(addressnew == null)
			addressnew = new Address();
		else
		{
			sv_addressSetToDefault.setIsOn(addressnew.getIs_default() > 0 ? true : false);
			et_address_client_name.setText(addressnew.getName());
			et_address_client_code.setText(addressnew.getZip());
			et_address_client_phone.setText(addressnew.getMobile());
			et_address_client_adddesc.setText(addressnew.getAddress());
			tv_address_client_area.setText(getAddressPCC(addressnew));
		}
	}
	
	private String getAddressPCC(Address addressnew)
	{
		StringBuilder sb = new StringBuilder();
		if(addressnew.getProvince()!=null)
		{
			sb.append(addressnew.getProvince().name);
		}
		if(addressnew.getCity()!=null)
		{
			sb.append(addressnew.getCity().name);
		}
		if(addressnew.getCounty()!=null)
		{
			sb.append(addressnew.getCounty().name);
		}
		return sb.toString();
	}
	
	private boolean isAddressPCCEmpty(Address addressnew)
	{
		if(addressnew.getProvince() == null || addressnew.getCity()==null || addressnew.getCounty()==null)
			return true;
		return false;
	}
	
	private void postAddress(Address addressnew) {
		if(!isCompleted)
			return;
		addressnew.setAddress(et_address_client_adddesc.getText().toString());
		addressnew.setName(et_address_client_name.getText().toString());
		addressnew.setMobile(et_address_client_phone.getText().toString());
		addressnew.setZip(et_address_client_code.getText().toString());
		addressnew.setIs_default(sv_addressSetToDefault.isOn() ? 1 : 0);
		AddressHttpOperation.postAddressEditadble(this, Constants.CALLBACK_FLAG_POST_ADDRESS, addressnew, this);
		this.showProcessDialog();
	}

	private void setAddressIds(Address addressnew, String ids) {
		if (addressnew == null && ids != null)
			return;
		String[] idsarr = ids.split(Constants.COMMON_TEXTSPLITER);
		for (int i = 0; i < idsarr.length; i++) {
			AreaBean ab =new AreaBean();
			ab.address_id = Integer.parseInt(idsarr[i]);
			switch (i) {
			case 0:
				addressnew.setProvince(ab);
				break;
			case 1:
				addressnew.setCity(ab);
				break;
			case 2:
				addressnew.setCounty(ab);
				break;

			}
		}

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.rl_address_client_area:
			intentGetArea = new Intent();
			intentGetArea.setClass(this, AddressAreaPickedActivity.class);
			this.startActivityForResult(intentGetArea,
					Constants.CODE_ADDRESSMODIFICATION_ASK_AREA_FORRS);
			break;
		case ResourceLocation.BUTTON_HEADER_RIGHT:
			if (TextUtils.isEmpty(et_address_client_name.getText().toString())) {
				isCompleted = false;
				break;
			}
			if (TextUtils.isEmpty(et_address_client_code.getText().toString())) {
				isCompleted = false;
				break;
			}
			if (TextUtils.isEmpty(et_address_client_phone.getText().toString())) {
				isCompleted = false;
				break;
			}
			if (TextUtils.isEmpty(et_address_client_adddesc.getText()
					.toString())) {
				isCompleted = false;
				break;
			}
			if (TextUtils.isEmpty(tv_address_client_area.getText().toString())) {
				isCompleted = false;
				break;
			}
			if(isAddressPCCEmpty(addressnew))
			{
				isCompleted = false;
				break;
			}
			isCompleted = true;
			postAddress(addressnew);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == Constants.CODE_ADDRESSMODIFICATION_ASK_AREA_FORRS) {
			Log.d(" requestCode ");
			if (data != null
					&& data.hasExtra(Constants.AREA_BACKTO_ADDRESSMODIFICATION)&& data.hasExtra(Constants.AREA_BACKTO_ADDRESSIDMODIFICATION)) {

				String area = data
						.getStringExtra(Constants.AREA_BACKTO_ADDRESSMODIFICATION);
				this.addressnewids = data.getStringExtra(Constants.AREA_BACKTO_ADDRESSIDMODIFICATION);
				Log.d(" data != null " + area);
				setAddressIds(addressnew,addressnewids);
				setArea(area);
			}
			
		}

	}

	private void setArea(String area) {
		if (tv_address_client_area != null)
			tv_address_client_area.setText(area);
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(requestId == Constants.CALLBACK_FLAG_POST_ADDRESS)
		{
			this.closeProcessDialog();
			if(httpResult != null)
			{
				if(httpResult.isSuccess())
				{
					this.showMessageToast(resource.getString(R.string.address_createdSUCCESS));
					this.setResult(Constants.CODE_ADDRESSIDMODIFICATION_BACKTO_ADDRESS);
					this.finish();
				}else
				{
					this.showMessageToast(httpResult.getResult());
				}
			}
		}
	}

}
