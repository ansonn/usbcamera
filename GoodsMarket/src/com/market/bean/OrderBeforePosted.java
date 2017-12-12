package com.market.bean;

public class OrderBeforePosted {
	private Double order_total;
	private PaymentType[] payment_type_list;
	private Address address;
	private String[] invoice_title_type_list;
	private Double fare;
	private Double cart_total;
	private Voucher[] voucher_list;
	private SkuBean[] sku_list;
	private Integer tax_rate;
	private Integer invoice_status;
	
	private Integer is_invoice;
	private String invoice_title;
	private Voucher voucher;
	private String remark;
	private Integer payment_type;
	
	private Double extraPrice;
	
	private long[] sku_ids;
	
	public Double getOrder_total() {
		return order_total;
	}
	public void setOrder_total(Double order_total) {
		this.order_total = order_total;
	}
	public PaymentType[] getPayment_type_list() {
		return payment_type_list;
	}
	public void setPayment_type_list(PaymentType[] payment_type_list) {
		this.payment_type_list = payment_type_list;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String[] getInvoice_title_type_list() {
		return invoice_title_type_list;
	}
	public void setInvoice_title_type_list(String[] invoice_title_type_list) {
		this.invoice_title_type_list = invoice_title_type_list;
	}
	public Double getFare() {
		return fare;
	}
	public void setFare(Double fare) {
		this.fare = fare;
	}
	public Double getCart_total() {
		return cart_total;
	}
	public void setCart_total(Double cart_total) {
		this.cart_total = cart_total;
	}
	public Voucher[] getVoucher_list() {
		return voucher_list;
	}
	public void setVoucher_list(Voucher[] voucher_list) {
		this.voucher_list = voucher_list;
	}
	public SkuBean[] getSku_list() {
		return sku_list;
	}
	public void setSku_list(SkuBean[] sku_list) {
		this.sku_list = sku_list;
	}
	public Integer getTax_rate() {
		return tax_rate;
	}
	public void setTax_rate(Integer tax_rate) {
		this.tax_rate = tax_rate;
	}
	public Integer getInvoice_status() {
		return invoice_status;
	}
	public void setInvoice_status(Integer invoice_status) {
		this.invoice_status = invoice_status;
	}
	public Integer getIs_invoice() {
		return is_invoice;
	}
	public void setIs_invoice(Integer is_invoice) {
		this.is_invoice = is_invoice;
	}
	public String getInvoice_title() {
		return invoice_title;
	}
	public void setInvoice_title(String invoice_title) {
		this.invoice_title = invoice_title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(Integer payment_type) {
		this.payment_type = payment_type;
	}
	public long[] getSku_ids() {
		return sku_ids;
	}
	public void setSku_ids(long[] sku_ids) {
		this.sku_ids = sku_ids;
	}
	public Double getExtraPrice() {
		return extraPrice;
	}
	public void setExtraPrice(Double extraPrice) {
		this.extraPrice = extraPrice;
	}
	public Voucher getVoucher() {
		return voucher;
	}
	public void setVoucher(Voucher voucher) {
		this.voucher = voucher;
	}
	
	
}
