package com.market.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeCount extends CountDownTimer {
	private String onloading;
	private String after;
	private Button btn;
	private long countDownInterval;
	private ITicker ticker;

	public TimeCount(long millisInFuture, long countDownInterval,
			String onloading, String after, Button btn) {
		super(millisInFuture, countDownInterval);
		this.countDownInterval = countDownInterval;
		this.onloading = onloading;
		this.after = after;
		this.btn = btn;
	}

	@Override
	public void onFinish() {
		if(ticker == null)
		{
			btn.setClickable(true);
			btn.setText(after);
		}
		else
			ticker.finsihTick();
	}

	@Override
	public void onTick(long millisUntilFinished) {
		if(ticker == null)
		{
			btn.setClickable(false);
			btn.setText(onloading +"("+ millisUntilFinished / countDownInterval +")");
		}
		else
			ticker.doTick(millisUntilFinished);
	}
	
	public interface ITicker{
		public void doTick(long millisUntilFinished);
		public void finsihTick();
	}

	public ITicker getTicker() {
		return ticker;
	}

	public void setTicker(ITicker ticker) {
		this.ticker = ticker;
	}
	
	
}