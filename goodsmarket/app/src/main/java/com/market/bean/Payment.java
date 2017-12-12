package com.market.bean;

public class Payment {
	private Long payment_id;
	private String code;
	private String name;
	private String logo;
	private Payparam pay_param;
	public Long getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Payparam getPay_param() {
		return pay_param;
	}
	public void setPay_param(Payparam pay_param) {
		this.pay_param = pay_param;
	}
	
}
