package com.market.bean;

import java.io.Serializable;


public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;
	private String payment_type_name;
	private Integer lock;
	private Long order_id;
	private Long estimated_receipt_time;
	private String order_no;
	private Integer pay_status;
	private Boolean is_refunded;
	private Double order_amount;
	private Integer delivery_status;
	private String status_name;
	private Long completion_time;
	private Long create_time;
	private String accept_name;
	private Boolean is_show_comment;
	private Boolean is_show_refund;
	private Boolean is_show_receipt;
	private Boolean is_show_pay;
	private Boolean is_show_cancel;
	private Boolean is_show_delete;
	private OrderGoods[] order_goods;
	private String refund_status_name;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPayment_type_name() {
		return payment_type_name;
	}
	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}
	public Integer getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Long getEstimated_receipt_time() {
		return estimated_receipt_time;
	}
	public void setEstimated_receipt_time(Long estimated_receipt_time) {
		this.estimated_receipt_time = estimated_receipt_time;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public Integer getPay_status() {
		return pay_status;
	}
	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
	}
	public Boolean getIs_refunded() {
		return is_refunded;
	}
	public void setIs_refunded(Boolean is_refunded) {
		this.is_refunded = is_refunded;
	}
	public Double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(Double order_amount) {
		this.order_amount = order_amount;
	}
	public Integer getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(Integer delivery_status) {
		this.delivery_status = delivery_status;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public Long getCompletion_time() {
		return completion_time;
	}
	public void setCompletion_time(Long completion_time) {
		this.completion_time = completion_time;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public String getAccept_name() {
		return accept_name;
	}
	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}
	public OrderGoods[] getOrder_goods() {
		return order_goods;
	}
	public void setOrder_goods(OrderGoods[] order_goods) {
		this.order_goods = order_goods;
	}
	public String getRefund_status_name() {
		return refund_status_name;
	}
	public void setRefund_status_name(String refund_status_name) {
		this.refund_status_name = refund_status_name;
	}
	public Boolean getIs_show_comment() {
		return is_show_comment;
	}
	public void setIs_show_comment(Boolean is_show_comment) {
		this.is_show_comment = is_show_comment;
	}
	public Boolean getIs_show_refund() {
		return is_show_refund;
	}
	public void setIs_show_refund(Boolean is_show_refund) {
		this.is_show_refund = is_show_refund;
	}
	public Boolean getIs_show_receipt() {
		return is_show_receipt;
	}
	public void setIs_show_receipt(Boolean is_show_receipt) {
		this.is_show_receipt = is_show_receipt;
	}
	public Boolean getIs_show_pay() {
		return is_show_pay;
	}
	public void setIs_show_pay(Boolean is_show_pay) {
		this.is_show_pay = is_show_pay;
	}
	public Boolean getIs_show_cancel() {
		return is_show_cancel;
	}
	public void setIs_show_cancel(Boolean is_show_cancel) {
		this.is_show_cancel = is_show_cancel;
	}
	public Boolean getIs_show_delete() {
		return is_show_delete;
	}
	public void setIs_show_delete(Boolean is_show_delete) {
		this.is_show_delete = is_show_delete;
	}
	


}
