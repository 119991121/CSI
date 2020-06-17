package com.chinasoft.pojo;

import java.io.Serializable;

public class Department implements Serializable{
	private int departmentID;
	private int departmentNum;
	private String departmentName;
	private String departmentMessage;
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public int getDepartmentNum() {
		return departmentNum;
	}
	public void setDepartmentNum(int departmentNum) {
		this.departmentNum = departmentNum;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentMessage() {
		return departmentMessage;
	}
	public void setDepartmentMessage(String departmentMessage) {
		this.departmentMessage = departmentMessage;
	}
	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Department(int departmentID, int departmentNum, String departmentName, String departmentMessage) {
		super();
		this.departmentID = departmentID;
		this.departmentNum = departmentNum;
		this.departmentName = departmentName;
		this.departmentMessage = departmentMessage;
	}
	@Override
	public String toString() {
		return "Department [departmentID=" + departmentID + ", departmentNum=" + departmentNum + ", departmentName="
				+ departmentName + ", departmentMessage=" + departmentMessage + "]";
	}
	
}
