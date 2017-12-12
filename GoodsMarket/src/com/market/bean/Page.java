package com.market.bean;

public class Page {
	private int listRows;// 一页显示多少条
	private int nowPage;
	private int totalPages;
	private int totalRows;
	
	private Object[] contentArray;//列表内容

	public int getListRows() {
		return listRows;
	}

	public void setListRows(int listRows) {
		this.listRows = listRows;
	}


	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public Object[] getContentArray() {
		return contentArray;
	}

	public void setContentArray(Object[] contentArray) {
		this.contentArray = contentArray;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
}
