package com.chinasoft.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Menu {

	private int menuID;
	private String dishName;
	private int time;
	private Date date;
	public int getMenuID() {
		return menuID;
	}
	public void setMenuID(int menuID) {
		this.menuID = menuID;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@JsonFormat(pattern = "yyyy-MM-dd ",timezone="GMT+8")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Menu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Menu(int menuID, String dishName, int time, Date date) {
		super();
		this.menuID = menuID;
		this.dishName = dishName;
		this.time = time;
		this.date = date;
	}
	@Override
	public String toString() {
		return "Menu [menuID=" + menuID + ", dishName=" + dishName + ", time=" + time + ", date=" + date + "]";
	}
	
}
