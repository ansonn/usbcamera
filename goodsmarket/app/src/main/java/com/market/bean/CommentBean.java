package com.market.bean;

public class CommentBean {
	private SimpleSpec[] spec;
	private String content;
	private String user_name;
	private Long comment_id;
	private Integer score;
	private Long user_id;
	private Long date;
	private String author_ip;
	
	public SimpleSpec[] getSpec() {
		return spec;
	}
	public void setSpec(SimpleSpec[] spec) {
		this.spec = spec;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Long getComment_id() {
		return comment_id;
	}
	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getAuthor_ip() {
		return author_ip;
	}
	public void setAuthor_ip(String author_ip) {
		this.author_ip = author_ip;
	}
	
}
