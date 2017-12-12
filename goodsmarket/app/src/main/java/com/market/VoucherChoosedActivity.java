package com.market;

import com.haoyikuai.shop.android.R;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class VoucherChoosedActivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_voucherperson;
	private TextView tv_vouchercompany;
	private EditText edt_vouchertitle;
	private int textColor;
	private int padding;
	private int currentType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voucherchoosed);
		initTitle();
		initView();

	}

	private void initTitle() {
		this.setActivityTitle(getResources().getString(R.string.invoice));
		this.setDeftualtHeaderLeftButton();
		this.setStringHeaderRightButton(
				getResources().getString(R.string.confirm), this);
	}

	private void initView() {
		edt_vouchertitle = (EditText) this.findViewById(R.id.edt_vouchertitle);
		tv_vouchercompany = (TextView) this
				.findViewById(R.id.tv_vouchercompany);
		tv_voucherperson = (TextView) this.findViewById(R.id.tv_voucherperson);
		textColor = getResources().getColor(R.color.common_text_color);
		tv_vouchercompany.setOnClickListener(this);
		tv_voucherperson.setOnClickListener(this);
		padding = tv_vouchercompany.getPaddingBottom();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.tv_vouchercompany:
			switchTv(tv_vouchercompany,tv_voucherperson);
			edt_vouchertitle.setVisibility(View.VISIBLE);
			currentType = 1;
			break;
		case R.id.tv_voucherperson:
			switchTv(tv_voucherperson,tv_vouchercompany);
			edt_vouchertitle.setVisibility(View.INVISIBLE);
			currentType = 0;
			break;
		case ResourceLocation.BUTTON_HEADER_RIGHT:
			if(currentType == 1 && edt_vouchertitle != null)
			{
				String comt = edt_vouchertitle.getText().toString();
				if(!TextUtils.isEmpty(comt))
				{
					Intent intent = new Intent();
					intent.putExtra(Constants.STRINGEXTRA, comt);
					this.setResult(Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE1, intent);
					finish();
				}
				else
				{
					showMessageToast(getResources().getString(R.string.order_invoiceinofcannotbeEmpty));
				}
			}
			else if(currentType == 0)
			{
				Intent intent = new Intent();
				intent.putExtra(Constants.STRINGEXTRA, getResources().getString(R.string.forperson));
				this.setResult(Constants.CODE_COMMON_JUMPBETWEEN_ACTIVITYS_RESPONSE1, intent);
				finish();
			}
		}
	}

	private void switchTv(TextView tvchosed, TextView untvchosed) {
		tvchosed.setBackgroundResource(R.drawable.bg_corner_border_red);
		untvchosed.setBackgroundColor(Color.TRANSPARENT);
		tvchosed.setTextColor(Color.WHITE);
		untvchosed.setTextColor(textColor);
		untvchosed.setPadding(padding, padding, padding, padding);
		tvchosed.setPadding(padding, padding, padding, padding);
	}
}
