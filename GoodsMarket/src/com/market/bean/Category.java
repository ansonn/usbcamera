package com.market.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @tag 栏目实体类
 * @author 陈伟斌
 * @date 2015-1-22
 */
public class Category implements Serializable{

	private String category_name;
	private int category_id;

	public List<Category> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<Category> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	private int[] pid_array;// 父栏目id列表
	private int[] child_id_array;
	private String image;
	private List<Category> subCategoryList;


	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}



	public int[] getPid_array() {
		return pid_array;
	}

	public void setPid_array(int[] pid_array) {
		this.pid_array = pid_array;
	}

	public int[] getChild_id_array() {
		return child_id_array;
	}

	public void setChild_id_array(int[] child_id_array) {
		this.child_id_array = child_id_array;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
