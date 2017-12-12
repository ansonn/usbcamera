package com.market;

import com.haoyikuai.shop.android.R;
import com.lidroid.xutils.BitmapUtils;
import com.market.bean.OrderGoods;
import com.market.http.HttpComm;
import com.market.http.HttpResult;
import com.market.http.OnHttpCallBack;
import com.market.http.httpoperation.OrderHttpOperation;
import com.market.utils.Constants;
import com.market.utils.ResourceLocation;
import com.market.view.StarsComment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderGoodsCommentActivity extends BaseActivity implements
		OnClickListener, OnHttpCallBack {
	private OrderGoods orderGoods;
	private long goodsid;
	private TextView tv_goodstitle;
	private ImageView img_commentview;
	private EditText edt_goodscommentdetail;
	private StarsComment stars_goodscomment;
	private BitmapUtils bitmapUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_writecomment);
		initData();
		initView();
		initTitle();
	}

	private void initData() {
		Intent intent = this.getIntent();
		orderGoods = (OrderGoods) intent
				.getSerializableExtra(Constants.OBJECTEXTRA);
		goodsid = intent.getLongExtra(Constants.LONGEXTRA, -1);
		bitmapUtils = new BitmapUtils(this);
	}

	private void initView() {
		stars_goodscomment = (StarsComment) this
				.findViewById(R.id.stars_goodscomment);
		stars_goodscomment.setEditable(true);
		tv_goodstitle = (TextView) this.findViewById(R.id.tv_goodstitle);
		img_commentview = (ImageView) this.findViewById(R.id.img_commentview);
		edt_goodscommentdetail = (EditText) this
				.findViewById(R.id.edt_goodscommentdetail);
		if (orderGoods != null) {
			tv_goodstitle.setText(orderGoods.getTitle());
			bitmapUtils.display(img_commentview, orderGoods.getThumb());
		}
	}

	private void initTitle() {
		this.setActivityTitle(getResources().getString(
				R.string.order_writeacommenttitle));
		this.setDeftualtHeaderLeftButton();
		this.setStringHeaderRightButton(
				getResources().getString(R.string.order_makeacommentaction),
				this);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case ResourceLocation.BUTTON_HEADER_RIGHT:
			postComment();
			break;
		}
	}

	private void postComment() {
		if (orderGoods != null) {
			OrderHttpOperation.commentOrder(goodsid,
					orderGoods.getSku_id(), stars_goodscomment.getStarCount(),
					edt_goodscommentdetail.getText().toString(), Constants.CALLBACK_FLAG_POSTORDERCOMMENT,
					this, this);
		}
	}

	@Override
	public void onHttpCallBack(int requestId, HttpResult httpResult) {
		// TODO Auto-generated method stub
		if(requestId == Constants.CALLBACK_FLAG_POSTORDERCOMMENT)
		{
			if(httpResult.isSuccess())
			{
				this.ShowToastImageCorrect(getResources().getString(R.string.postsuccess));
				this.finish();
			}
		}
	}
}
