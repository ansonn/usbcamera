package com.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.haoyikuai.shop.android.R;
import com.market.utils.Log;

public class AccountFragment extends BaseFragment {
	Button btnTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.person_fragment, container);

		// 设置标题
		btnTitle = (Button) view.findViewById(R.id.common_header_btn_center);
		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.person_title);

		setStringHeaderRightButton(view, getString(R.string.save),
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 保存数据

					}
				});
		return view;
	}

	@Override
	protected void isShowtoUser(String latest) {
		// TODO Auto-generated method stub
		Log.d("latest "+latest);
	}
}
