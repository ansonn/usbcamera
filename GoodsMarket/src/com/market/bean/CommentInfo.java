package com.market.bean;

public class CommentInfo {
	private CommentBean[] comment_list;
	private Page page;
	
	public CommentBean[] getComment_list() {
		return comment_list;
	}
	public void setComment_list(CommentBean[] comment_list) {
		this.comment_list = comment_list;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
}
