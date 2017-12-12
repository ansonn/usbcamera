package com.market.bean;

import java.io.Serializable;

public class VoucherList implements Serializable{
	private Voucher[] voucher_list;

	public Voucher[] getVoucher_list() {
		return voucher_list;
	}

	public void setVoucher_list(Voucher[] voucher_list) {
		this.voucher_list = voucher_list;
	}
	
}
