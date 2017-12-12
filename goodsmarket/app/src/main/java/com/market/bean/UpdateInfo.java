package com.market.bean;

public class UpdateInfo {

	
	public int getVersion_code() {
		return version_code;
	}
	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMarket_url() {
		return market_url;
	}
	public void setMarket_url(String market_url) {
		this.market_url = market_url;
	}
	private int version_code;
	private String content;
	private String url;
	private String market_url;
}
