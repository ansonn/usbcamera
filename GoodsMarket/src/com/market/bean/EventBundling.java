package com.market.bean;

import java.util.ArrayList;

public class EventBundling {
	private Long event_bundling_id;
	private GoodsData[] goodsData;
	private String title;
	private Double price;
	private String description;
	private ArrayList<Integer> goods_id;
	private int status;
	private String name;
	public Long getEvent_bundling_id() {
		return event_bundling_id;
	}
	public void setEvent_bundling_id(Long event_bundling_id) {
		this.event_bundling_id = event_bundling_id;
	}
	public GoodsData[] getGoodsData() {
		return goodsData;
	}
	public void setGoodsData(GoodsData[] goodsData) {
		this.goodsData = goodsData;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Integer> getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(ArrayList<Integer> goods_id) {
		this.goods_id = goods_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
