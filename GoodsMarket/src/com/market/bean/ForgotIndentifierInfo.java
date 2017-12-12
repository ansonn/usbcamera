package com.market.bean;

public class ForgotIndentifierInfo {
//01-21 17:43:36.816: D/HttpUtils(25736): {"status":1,"code":1,"content":{"user_id":"9","user_name":"chen","email":"1********2@qq.com","mobile":"0"}}
	private int user_id;
	private String user_name;
	private String email;
	private String mobile;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
