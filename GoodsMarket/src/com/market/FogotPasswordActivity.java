package com.market;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haoyikuai.shop.android.R;
import com.market.utils.Constants;

public class FogotPasswordActivity extends BaseActivity implements OnClickListener{
	private EditText edt_idforforgot;
	private Button btn_next;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_forgot_password);
		setTitle();
		initViews();
	}
	
	private void setTitle()
	{
		super.setDeftualtHeaderLeftButton();
		super.setActivityTitle(this.getString(R.string.forgot_title));
		
	}
	
	private void initViews()
	{
		edt_idforforgot = (EditText)this.findViewById(R.id.edt_idforforgot);
		btn_next = (Button)this.findViewById(R.id.btn_fogot_next);
		btn_next.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.btn_fogot_next:
				if(edt_idforforgot == null)
					return;
				
				Intent vfchooseintent = new Intent();
				vfchooseintent.setClass(this, VFChooseActivity.class);
				vfchooseintent.putExtra(Constants.MAP_FORGOTINDENTIFY, edt_idforforgot.getText().toString().trim());
				this.startActivity(vfchooseintent);
				break;
		}
	}
}
