package com.market.bean;

import java.io.Serializable;

public class Sku_list implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SkuBean[] sku_list;

	public SkuBean[] getSku_list() {
		return sku_list;
	}

	public void setSku_list(SkuBean[] sku_list) {
		this.sku_list = sku_list;
	}
	
}
