package com.market.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodsInfo implements Serializable{
	
	private Long id;
	private String title;
	private long update_time;
	private long store_num;//商品详情对应的字段
	private long store;//商品列表对应的字段
	
	public long getStore() {
		return store;
	}

	public void setStore(long store) {
		this.store = store;
	}

	public double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}

	public int getComment_count() {
		return comment_count;
	}

	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

	private double sell_price;//商品详情对应的字段
	private double market_price;//商品详情对应的字段
	
	private String descirption;
	private String content;
	private String prom;
	private String url;
	private  int comment_count;//评论数 
	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	private String thumb;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCurrentchoosedime() {
		return currentchoosedime;
	}

	public void setCurrentchoosedime(int currentchoosedime) {
		this.currentchoosedime = currentchoosedime;
	}

	private ArrayList<EventBundling> eventBundlingList;
	private ArrayList<String> dimes;//test
	public int currentchoosedime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(long update_time) {
		this.update_time = update_time;
	}

	public long getStore_num() {
		return store_num;
	}

	public void setStore_num(long store_num) {
		this.store_num = store_num;
	}

	public double getSell_price() {
		return sell_price;
	}

	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}

	public String getDescirption() {
		return descirption;
	}

	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProm() {
		return prom;
	}

	public void setProm(String prom) {
		this.prom = prom;
	}

	public ArrayList<EventBundling> getEventBundlingList() {
		return eventBundlingList;
	}

	public void setEventBundlingList(ArrayList<EventBundling> eventBundlingList) {
		this.eventBundlingList = eventBundlingList;
	}

	public ArrayList<String> getDimes() {
		return dimes;
	}

	public void setDimes(ArrayList<String> dimes) {
		this.dimes = dimes;
	}

	
}
