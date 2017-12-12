package com.market.bean;

/**
 * 
 * @tag商品列表，包含跟商品列表相关的信息
 * @author 陈伟斌
 * @date 2015-3-4
 */
public class GoodsList {
	private Brand[] brandList;
	private Specification[] specList;
	private String[] priceRange;
	private Attribute[] attrList;
	private GoodsInfo[] goods_list;
	private Page page;

	public Brand[] getBrandList() {
		return brandList;
	}

	public void setBrandList(Brand[] brandList) {
		this.brandList = brandList;
	}

	public Specification[] getSpecList() {
		return specList;
	}

	public void setSpecList(Specification[] specList) {
		this.specList = specList;
	}

	public String[] getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String[] priceRange) {
		this.priceRange = priceRange;
	}

	public Attribute[] getAttrList() {
		return attrList;
	}

	public void setAttrList(Attribute[] attrList) {
		this.attrList = attrList;
	}

	public GoodsInfo[] getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(GoodsInfo[] goods_list) {
		this.goods_list = goods_list;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
