package com.market.bean;

import java.io.Serializable;

public class Address implements Serializable{

	private String name;
	private String zip;
	private String address;
	private Long address_id;
	private AreaBean county;
	private AreaBean province;
	private AreaBean city;
	private Integer is_default;
	private Long user_id;
	private String mobile;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}
	public AreaBean getCounty() {
		return county;
	}
	public void setCounty(AreaBean county) {
		this.county = county;
	}
	public AreaBean getProvince() {
		return province;
	}
	public void setProvince(AreaBean province) {
		this.province = province;
	}
	public AreaBean getCity() {
		return city;
	}
	public void setCity(AreaBean city) {
		this.city = city;
	}
	public Integer getIs_default() {
		return is_default;
	}
	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
