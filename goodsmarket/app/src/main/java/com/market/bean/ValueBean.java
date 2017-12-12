package com.market.bean;

import java.io.Serializable;

public class ValueBean implements Serializable{
	private String image;
	private Long spec_id;
	private Integer sort;
	private Long spec_value_id;
	private String name;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Long getSpec_value_id() {
		return spec_value_id;
	}
	public void setSpec_value_id(Long spec_value_id) {
		this.spec_value_id = spec_value_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
