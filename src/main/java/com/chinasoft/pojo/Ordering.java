package com.chinasoft.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Ordering implements Serializable{

	private int orderingID;
	private String username;
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date date;
	private int time;
	private String dishName;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	public Date getDate() {
		return date;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public int getOrderingID() {
		return orderingID;
	}
	public void setOrderingID(int orderingID) {
		this.orderingID = orderingID;
	}
	public Ordering(String username, Date date, int time, String dishName) {
		super();
		this.username = username;
		this.date = date;
		this.time = time;
		this.dishName = dishName;
	}
	public Ordering() {
		super();
	}
	@Override
	public String toString() {
		return "Ordering [orderingID=" + orderingID + ", username=" + username + ", date=" + date + ", time=" + time
				+ ", dishName=" + dishName + "]";
	}
	
}
