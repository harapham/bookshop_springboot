package com.shop.library.utils;

public class CusTop {

	String username;
	Double total;
	public CusTop(String username, Double total) {
		super();
		this.username = username;
		this.total = total;
	}
	public CusTop() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
}
