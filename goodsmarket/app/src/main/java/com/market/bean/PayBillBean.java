package com.market.bean;

public class PayBillBean {
	private OrderDetail order;
	private Payment[] payment_list;

	public Payment[] getPayment_list() {
		return payment_list;
	}

	public void setPayment_list(Payment[] payment_list) {
		this.payment_list = payment_list;
	}

	public OrderDetail getOrder() {
		return order;
	}

	public void setOrder(OrderDetail order) {
		this.order = order;
	}
}
