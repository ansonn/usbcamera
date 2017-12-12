package com.market.bean;

import java.io.Serializable;

public class SkuBean implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private Spec[] specbean;
	private String spec_key;
	private Double weight;
	private Double cost_price;
	private String image;
	private Long store_id;
	private Long category_id;
	private Long prom_id;
	private Integer number;
	private Integer store_num;
	private String url;
	private String pro_no;
	private Double amount;
	private Double real_price;
	private String title;
	private Double sell_price;
	private Double market_price;
	private Long goods_id;
	private Integer warning_line;
	private Long sku_id;
	private String thumb;
	private String prom_name;
	private Boolean isPurchased = true;
	
	
	public String getSpec_key() {
		return spec_key;
	}

	public void setSpec_key(String spec_key) {
		this.spec_key = spec_key;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getCost_price() {
		return cost_price;
	}

	public void setCost_price(Double cost_price) {
		this.cost_price = cost_price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getStore_num() {
		return store_num;
	}

	public void setStore_num(Integer store_num) {
		this.store_num = store_num;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPro_no() {
		return pro_no;
	}

	public void setPro_no(String pro_no) {
		this.pro_no = pro_no;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getReal_price() {
		return real_price;
	}

	public void setReal_price(Double real_price) {
		this.real_price = real_price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getSell_price() {
		return sell_price;
	}

	public void setSell_price(Double sell_price) {
		this.sell_price = sell_price;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public Long getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Integer getWarning_line() {
		return warning_line;
	}

	public void setWarning_line(Integer warning_line) {
		this.warning_line = warning_line;
	}

	public Long getSku_id() {
		return sku_id;
	}

	public void setSku_id(Long sku_id) {
		this.sku_id = sku_id;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	
	public Boolean getIsPurchased() {
		return isPurchased;
	}

	public void setIsPurchased(Boolean isPurchased) {
		this.isPurchased = isPurchased;
	}

	@Override
	public String toString() {
		return "spec_key "+spec_key+" weight "+weight
				+"cost_price "+cost_price+" image "+image
				+"store_id "+store_id+" category_id "+category_id
				+" number "+number+" store_num "+store_num
				+" url "+url+" pro_no "+pro_no
				+" amount "+amount+" real_price "+real_price
				+" title "+title+" sell_price "+sell_price
				+" market_price "+market_price+" goods_id "+goods_id
				+" warning_line "+warning_line+" sku_id "+sku_id
				+" thumb "+thumb;
				
	}


	public Long getProm_id() {
		return prom_id;
	}

	public void setProm_id(Long prom_id) {
		this.prom_id = prom_id;
	}

	public Spec[] getSpecbean() {
		return specbean;
	}

	public void setSpecbean(Spec[] specbean) {
		this.specbean = specbean;
	}

	public String getProm_name() {
		return prom_name;
	}

	public void setProm_name(String prom_name) {
		this.prom_name = prom_name;
	}
	
	
}
