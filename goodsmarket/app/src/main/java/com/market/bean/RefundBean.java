package com.market.bean;

import java.io.Serializable;

public class RefundBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String refund_account;
	private Integer status;
	private String account_bank;
	private Long order_id;
	private String order_no;
	private String account_name;
	private Double amount;
	private Long id;
	private Long handling_time;
	private String status_name;
	private String refund_type_name;
	private Integer refund_type;
	private Long create_time;
	private Long admin_id;
	private Long user_id;
	private String handling_idea;
	private String bank_account;
	private String channel;
	private String note;
	
	public String getRefund_account() {
		return refund_account;
	}
	public void setRefund_account(String refund_account) {
		this.refund_account = refund_account;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAccount_bank() {
		return account_bank;
	}
	public void setAccount_bank(String account_bank) {
		this.account_bank = account_bank;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHandling_time() {
		return handling_time;
	}
	public void setHandling_time(Long handling_time) {
		this.handling_time = handling_time;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public String getRefund_type_name() {
		return refund_type_name;
	}
	public void setRefund_type_name(String refund_type_name) {
		this.refund_type_name = refund_type_name;
	}
	public Integer getRefund_type() {
		return refund_type;
	}
	public void setRefund_type(Integer refund_type) {
		this.refund_type = refund_type;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Long getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Long admin_id) {
		this.admin_id = admin_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getHandling_idea() {
		return handling_idea;
	}
	public void setHandling_idea(String handling_idea) {
		this.handling_idea = handling_idea;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
