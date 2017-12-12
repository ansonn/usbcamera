package com.market.bean;

public class Favorite {
	private Page page; 
	private GoodsoverviewInfo[] goodsList;
	public GoodsoverviewInfo[] getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(GoodsoverviewInfo[] goodsList) {
		this.goodsList = goodsList;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
