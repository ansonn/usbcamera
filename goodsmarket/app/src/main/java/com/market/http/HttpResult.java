package com.market.http;

public class HttpResult {

	private boolean isSuccess;
	private int code;
	private String result;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return "HttpResult [isSuccess=" + isSuccess + ", code=" + code
				+ ", result=" + result + "]";
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
