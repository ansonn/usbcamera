package com.market.bean;

public class Ads {

	// {"tpl":"","pics":[{"title":"","url":"","pic_url":"http:\/\/www.haoyikuai.cn\/Upload\/File\/2015\/03\/26\/5513df2b0c7db.jpg"},{"title":"","url":"","pic_url":"http:\/\/www.haoyikuai.cn\/Upload\/File\/2015\/03\/26\/5513de2b4dc89.jpg"}],"height":"9","type":"2","width":"16"}

	private String tpl;
	public String getTpl() {
		return tpl;
	}
	public void setTpl(String tpl) {
		this.tpl = tpl;
	}
	public AdPicture[] getPics() {
		return pics;
	}
	public void setPics(AdPicture[] pics) {
		this.pics = pics;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	private AdPicture [] pics;
	private int height;
	private int width;
	private int type;
}
