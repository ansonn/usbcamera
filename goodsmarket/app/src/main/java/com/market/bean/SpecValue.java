package com.market.bean;

/**
 * 关于规格的相关值
 * @tag 
 * @author 陈伟斌
 * @date 2015-3-4
 */
public class SpecValue {

	private String image;
	private int spec_id;
	private int sort;//序号
	private int spec_value_id;
	private String name;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(int spec_id) {
		this.spec_id = spec_id;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getSpec_value_id() {
		return spec_value_id;
	}
	public void setSpec_value_id(int spec_value_id) {
		this.spec_value_id = spec_value_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
