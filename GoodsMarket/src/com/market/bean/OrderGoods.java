package com.market.bean;

import java.io.Serializable;

public class OrderGoods implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Spec[] specBean;
	private Double real_price;
	private Integer id;
	private String title;
	private Integer goods_number;
	private Integer prom_id;
	private Integer sku_id;
	private String thumb;
	private String prom_name;
	private String url;
	private Double goods_amount;
	private Double goods_price;
	private String pro_no;
	public Double getReal_price() {
		return real_price;
	}
	public void setReal_price(Double real_price) {
		this.real_price = real_price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getGoods_number() {
		return goods_number;
	}
	public void setGoods_number(Integer goods_number) {
		this.goods_number = goods_number;
	}
	public Integer getProm_id() {
		return prom_id;
	}
	public void setProm_id(Integer prom_id) {
		this.prom_id = prom_id;
	}
	public Integer getSku_id() {
		return sku_id;
	}
	public void setSku_id(Integer sku_id) {
		this.sku_id = sku_id;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getProm_name() {
		return prom_name;
	}
	public void setProm_name(String prom_name) {
		this.prom_name = prom_name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Double getGoods_amount() {
		return goods_amount;
	}
	public void setGoods_amount(Double goods_amount) {
		this.goods_amount = goods_amount;
	}
	public String getPro_no() {
		return pro_no;
	}
	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}
	public Double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(Double goods_price) {
		this.goods_price = goods_price;
	}
	public Spec[] getSpecBean() {
		return specBean;
	}
	public void setSpecBean(Spec[] specBean) {
		this.specBean = specBean;
	}
	
}
