package com.chinasoft.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class File implements Serializable{
	private int fileID;
	private String name;
	//在我的电脑上，这里的注解映射是用在构造器上的，所以直接选择注解属性而不是选择注解set方法，需要注意.
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	private Date date;
	private int userID;
	private String user_name;
	private String des;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	private String herf;
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd ")
	public Date getDate() {
		return date;
	}
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public void setDate(Date date) {
		this.date = date;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getHerf() {
		return herf;
	}
	public void setHerf(String herf) {
		this.herf = herf;
	}
	
	public File(int fileID, String name, Date date, int userID, String des, String herf) {
		super();
		this.fileID = fileID;
		this.name = name;
		this.date = date;
		this.userID = userID;
		this.des = des;
		this.herf = herf;
	}
	@Override
	public String toString() {
		return "File [fileID=" + fileID + ", name=" + name + ", date=" + date + ", userID=" + userID + ", user_name="
				+ user_name + ", des=" + des + ", herf=" + herf + "]";
	}
	public File() {
		super();
	}
	
}
