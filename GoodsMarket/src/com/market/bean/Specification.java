package com.market.bean;

/**
 * 
 * @tag 商品规格
 * @author 陈伟斌
 * @date 2015-3-4
 */
public class Specification {
	
	private SpecValue [] value;//规格列表的值
	private int spec_id;
	private int type;
	private String note;//描述
	private String name;
	public SpecValue[] getValue() {
		return value;
	}
	public void setValue(SpecValue[] value) {
		this.value = value;
	}
	public int getSpec_id() {
		return spec_id;
	}
	public void setSpec_id(int spec_id) {
		this.spec_id = spec_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
