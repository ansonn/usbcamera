package com.market.bean;

import java.io.Serializable;

public class Spec implements Serializable{
	private ValueBean[] value;
	private String[] valueStr;
	private Long spec_id;
	private String name;
	private Integer type;
	private String value_XXX;
	private String note;
	
	public ValueBean[]  getValue() {
		return value;
	}
	public void setValue(ValueBean[]  value) {
		this.value = value;
	}
	public Long getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(Long spec_id) {
		this.spec_id = spec_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getValue_XXX() {
		return value_XXX;
	}
	public void setValue_XXX(String value_XXX) {
		this.value_XXX = value_XXX;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String[] getValueStr() {
		return valueStr;
	}
	public void setValueStr(String[] valueStr) {
		this.valueStr = valueStr;
	}
	
}
