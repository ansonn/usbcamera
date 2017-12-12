package com.market.bean;

public class RefundInfo {
	private RefundBean[] refund_list;
	private Page page;
	
	public RefundBean[] getRefund_list() {
		return refund_list;
	}
	public void setRefund_list(RefundBean[] refund_list) {
		this.refund_list = refund_list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
