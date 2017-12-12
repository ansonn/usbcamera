package com.market.bean;

import java.io.Serializable;

public class OrderDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String phone;
	private Integer pay_code;
	private String adjust_note;
	private Integer type;
	private String order_no;
	private String invoice_content;
	private Integer city;
	private Boolean is_refunded;
	private Long send_time;
	private String status_name;
	private Long completion_time;
	private Integer province;
	private Boolean is_invoice;
	private Boolean is_show_refund;
	private Integer voucher_id;
	private Double real_amount;
	private Boolean is_show_delete;
	private Integer status;
	private Boolean is_show_cancel;
	private Long pay_time;
	private Double discount_amount;
	private String user_remark;
	private OrderGoods[] order_goods;
	private String admin_remark;
	private Integer county;
	private Double adjust_amount;
	private Long create_time;
	private Boolean is_show_receipt;
	private Long confirm_time;
	private String mobile;
	private Double real_freight;
	private Boolean is_print;
	private Double payable_freight;
	private Boolean is_show_pay;
	private String payment_type_name;
	private Long prom_id;
	private Boolean is_delete;
	private String express;
	private Long order_id;
	private Integer pay_status;
	private Double order_amount;
	private Integer delivery_status;
	private Double payable_amount;
	private Long accept_time;
	private String accept_name;
	private Long user_id;
	private Boolean is_show_comment;
	private String zip;
	private String invoice_title;
	private Integer lock;
	private String full_address;
	private String prom;
	private Long estimated_receipt_time;
	private Integer point;
	private String address;
	private Long payment_id;
	private Voucher voucher;
	private Double handling_fee;
	private Double taxes;
	private Integer payment_type;
	private OrderLog[] order_log_list;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPay_code() {
		return pay_code;
	}
	public void setPay_code(Integer pay_code) {
		this.pay_code = pay_code;
	}
	public String getAdjust_note() {
		return adjust_note;
	}
	public void setAdjust_note(String adjust_note) {
		this.adjust_note = adjust_note;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getInvoice_content() {
		return invoice_content;
	}
	public void setInvoice_content(String invoice_content) {
		this.invoice_content = invoice_content;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Boolean getIs_refunded() {
		return is_refunded;
	}
	public void setIs_refunded(Boolean is_refunded) {
		this.is_refunded = is_refunded;
	}
	public Long getSend_time() {
		return send_time;
	}
	public void setSend_time(Long send_time) {
		this.send_time = send_time;
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
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Boolean getIs_invoice() {
		return is_invoice;
	}
	public void setIs_invoice(Boolean is_invoice) {
		this.is_invoice = is_invoice;
	}
	public Boolean getIs_show_refund() {
		return is_show_refund;
	}
	public void setIs_show_refund(Boolean is_show_refund) {
		this.is_show_refund = is_show_refund;
	}
	public Integer getVoucher_id() {
		return voucher_id;
	}
	public void setVoucher_id(Integer voucher_id) {
		this.voucher_id = voucher_id;
	}
	public Double getReal_amount() {
		return real_amount;
	}
	public void setReal_amount(Double real_amount) {
		this.real_amount = real_amount;
	}
	public Boolean getIs_show_delete() {
		return is_show_delete;
	}
	public void setIs_show_delete(Boolean is_show_delete) {
		this.is_show_delete = is_show_delete;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getIs_show_cancel() {
		return is_show_cancel;
	}
	public void setIs_show_cancel(Boolean is_show_cancel) {
		this.is_show_cancel = is_show_cancel;
	}
	public Long getPay_time() {
		return pay_time;
	}
	public void setPay_time(Long pay_time) {
		this.pay_time = pay_time;
	}
	public Double getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(Double discount_amount) {
		this.discount_amount = discount_amount;
	}
	public String getUser_remark() {
		return user_remark;
	}
	public void setUser_remark(String user_remark) {
		this.user_remark = user_remark;
	}
	public OrderGoods[] getOrder_goods() {
		return order_goods;
	}
	public void setOrder_goods(OrderGoods[] order_goods) {
		this.order_goods = order_goods;
	}
	public String getAdmin_remark() {
		return admin_remark;
	}
	public void setAdmin_remark(String admin_remark) {
		this.admin_remark = admin_remark;
	}
	public Integer getCounty() {
		return county;
	}
	public void setCounty(Integer county) {
		this.county = county;
	}
	public Double getAdjust_amount() {
		return adjust_amount;
	}
	public void setAdjust_amount(Double adjust_amount) {
		this.adjust_amount = adjust_amount;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public Boolean getIs_show_receipt() {
		return is_show_receipt;
	}
	public void setIs_show_receipt(Boolean is_show_receipt) {
		this.is_show_receipt = is_show_receipt;
	}
	public Long getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(Long confirm_time) {
		this.confirm_time = confirm_time;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getReal_freight() {
		return real_freight;
	}
	public void setReal_freight(Double real_freight) {
		this.real_freight = real_freight;
	}
	public Boolean getIs_print() {
		return is_print;
	}
	public void setIs_print(Boolean is_print) {
		this.is_print = is_print;
	}
	public Double getPayable_freight() {
		return payable_freight;
	}
	public void setPayable_freight(Double payable_freight) {
		this.payable_freight = payable_freight;
	}
	public Boolean getIs_show_pay() {
		return is_show_pay;
	}
	public void setIs_show_pay(Boolean is_show_pay) {
		this.is_show_pay = is_show_pay;
	}
	public String getPayment_type_name() {
		return payment_type_name;
	}
	public void setPayment_type_name(String payment_type_name) {
		this.payment_type_name = payment_type_name;
	}
	public Long getProm_id() {
		return prom_id;
	}
	public void setProm_id(Long prom_id) {
		this.prom_id = prom_id;
	}
	public Boolean getIs_delete() {
		return is_delete;
	}
	public void setIs_delete(Boolean is_delete) {
		this.is_delete = is_delete;
	}
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public Integer getPay_status() {
		return pay_status;
	}
	public void setPay_status(Integer pay_status) {
		this.pay_status = pay_status;
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
	public Double getPayable_amount() {
		return payable_amount;
	}
	public void setPayable_amount(Double payable_amount) {
		this.payable_amount = payable_amount;
	}
	public Long getAccept_time() {
		return accept_time;
	}
	public void setAccept_time(Long accept_time) {
		this.accept_time = accept_time;
	}
	public String getAccept_name() {
		return accept_name;
	}
	public void setAccept_name(String accept_name) {
		this.accept_name = accept_name;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Boolean getIs_show_comment() {
		return is_show_comment;
	}
	public void setIs_show_comment(Boolean is_show_comment) {
		this.is_show_comment = is_show_comment;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public Integer getLock() {
		return lock;
	}
	public void setLock(Integer lock) {
		this.lock = lock;
	}
	public String getFull_address() {
		return full_address;
	}
	public void setFull_address(String full_address) {
		this.full_address = full_address;
	}
	public String getProm() {
		return prom;
	}
	public void setProm(String prom) {
		this.prom = prom;
	}
	public Long getEstimated_receipt_time() {
		return estimated_receipt_time;
	}
	public void setEstimated_receipt_time(Long estimated_receipt_time) {
		this.estimated_receipt_time = estimated_receipt_time;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(Long payment_id) {
		this.payment_id = payment_id;
	}

	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	public Double getHandling_fee() {
		return handling_fee;
	}
	public void setHandling_fee(Double handling_fee) {
		this.handling_fee = handling_fee;
	}
	public Double getTaxes() {
		return taxes;
	}
	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}
	public Integer getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(Integer payment_type) {
		this.payment_type = payment_type;
	}
	public OrderLog[] getOrder_log_list() {
		return order_log_list;
	}
	public void setOrder_log_list(OrderLog[] order_log_list) {
		this.order_log_list = order_log_list;
	}
	
	
}
