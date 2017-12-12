package com.market;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;

import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private TabHost tabHost;
	private TabWidget mTabWidget;

	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initTabHost();
//		initHeader();

	}

	private void initTabHost() {
		tabHost = (TabHost) findViewById(R.id.tabhost);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		tabHost.setup();

		tabHost.addTab(tabHost
				.newTabSpec(Constants.TAB_HOME)
				.setIndicator(
						createIndicatorView(R.string.tab_indicator_home,
								R.drawable.home_icon))
				.setContent(R.id.tab_home_fragment));
		tabHost.addTab(tabHost
				.newTabSpec(Constants.TAB_CATATORY)
				.setIndicator(
						createIndicatorView(R.string.tab_indicator_category,
								R.drawable.catagory_icon))
				.setContent(R.id.tab_catagory_fragment));
		tabHost.addTab(tabHost
				.newTabSpec(Constants.TAB_FIND)
				.setIndicator(
						createIndicatorView(R.string.tab_indicator_find,
								R.drawable.find_icon))
				.setContent(R.id.tab_find_fragment));
		tabHost.addTab(tabHost
				.newTabSpec(Constants.TAB_CART)
				.setIndicator(
						createIndicatorView(R.string.tab_indicator_cart,
								R.drawable.cart_icon))
				.setContent(R.id.tab_cart_fragment));
		tabHost.addTab(tabHost
				.newTabSpec(Constants.TAB_ACCOUNT)
				.setIndicator(
						createIndicatorView(R.string.tab_indicator_account,
								R.drawable.account_icon))
				.setContent(R.id.tab_account_fragment));
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				EventBus.getDefault().postSticky(arg0);  
			}
		});
	}

	private View createIndicatorView(int label, int icon) {
		View indicatorView = LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, null);
		final ImageView ivIcon = (ImageView) indicatorView
				.findViewById(R.id.iv_icon);
		final TextView tvLabel = (TextView) indicatorView
				.findViewById(R.id.tv_label);
		ivIcon.setImageResource(icon);
		tvLabel.setText(label);

		return indicatorView;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == ResourceLocation.BUTTON_HEADER_RIGHT) {
			// ËÑË÷
		}

	}

}
