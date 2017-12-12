package com.market;

import com.haoyikuai.shop.android.R;
import com.market.adapter.CategoryListAdapter;
import com.market.http.HttpComm;
import com.market.utils.Constants;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FindFragment extends Fragment implements OnClickListener {
	private Button btnTitle;
	private RelativeLayout rlScan;
	private RelativeLayout rlArround;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.find_fragment, container);

		// …Ë÷√±ÍÃ‚
		btnTitle = (Button) view.findViewById(R.id.common_header_btn_center);
		btnTitle.setVisibility(View.VISIBLE);
		btnTitle.setText(R.string.find_title);

		rlScan = (RelativeLayout) view.findViewById(R.id.rl_scan);
		rlArround = (RelativeLayout) view.findViewById(R.id.rl_arround);

		rlScan.setOnClickListener(this);
		rlArround.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v == rlScan) {
			Intent intent=new Intent();
			intent.setClass(getActivity(), QrCodeScanActivity.class);
			startActivity(intent);
		} else if (v == rlArround) {

		}

	}
}
