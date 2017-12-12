package com.market.bean;

import java.io.Serializable;

public class Voucher  implements Serializable{
	private Long voucher_id;
	private Integer status;
	private Integer user_id;
	private Integer is_send;
	private Long start_time;
	private String name;
	private Double need_money;
	private Double par_value;
	private String key;
	private Long end_time;

	public Long getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(Long voucher_id) {
		this.voucher_id = voucher_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getIs_send() {
		return is_send;
	}

	public void setIs_send(Integer is_send) {
		this.is_send = is_send;
	}

	public Long getStart_time() {
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getNeed_money() {
		return need_money;
	}

	public void setNeed_money(Double need_money) {
		this.need_money = need_money;
	}

	public Double getPar_value() {
		return par_value;
	}

	public void setPar_value(Double par_value) {
		this.par_value = par_value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

}
