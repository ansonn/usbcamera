package com.market.bean;

/**
 * 商品参数
 * 
 * @tag
 * @author 陈伟斌
 * @date 2015-3-4
 */
public class Attribute {

	private int attr_id;
	private int sort;
	public int getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(int attr_id) {
		this.attr_id = attr_id;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getShow_type() {
		return show_type;
	}
	public void setShow_type(int show_type) {
		this.show_type = show_type;
	}
	public int getType_id() {
		return type_id;
	}
	public void setType_id(int type_id) {
		this.type_id = type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int show_type;
	private int type_id;
	private String name;
}
