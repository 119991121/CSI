package com.chinasoft.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Announcement {
	private int announcementID;
	private String name;
	private String content;
	private Date creatTime;
	private int userID;
	public int getAnnouncementID() {
		return announcementID;
	}
	public void setAnnouncementID(int announcementID) {
		this.announcementID = announcementID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@JsonFormat(pattern="yy:MM:dd",timezone="GMT+8")
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Announcement() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Announcement(int announcementID, String name, String content, Date creatTime, int userID) {
		super();
		this.announcementID = announcementID;
		this.name = name;
		this.content = content;
		this.creatTime = creatTime;
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "Announcement [announcementID=" + announcementID + ", name=" + name + ", content=" + content
				+ ", creatTime=" + creatTime + ", userID=" + userID + "]";
	}
	
}
