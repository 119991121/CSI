package com.chinasoft.pojo;

import java.io.Serializable;

public class Position implements Serializable{
	private int positionID;
	private int departmentID;
	private int positionNum;
	private String departmentName;
	private String positionName;
	private String positionMessage;
	public int getPositionID() {
		return positionID;
	}
	public void setPositionID(int positionID) {
		this.positionID = positionID;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}
	public int getPositionNum() {
		return positionNum;
	}
	public void setPositionNum(int positionNum) {
		this.positionNum = positionNum;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionMessage() {
		return positionMessage;
	}
	public void setPositionMessage(String positionMessage) {
		this.positionMessage = positionMessage;
	}
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Position(int positionID, int departmentID, int positionNum, String departmentName, String positionName,
			String positionMessage) {
		super();
		this.positionID = positionID;
		this.departmentID = departmentID;
		this.positionNum = positionNum;
		this.departmentName = departmentName;
		this.positionName = positionName;
		this.positionMessage = positionMessage;
	}
	@Override
	public String toString() {
		return "Position [positionID=" + positionID + ", departmentID=" + departmentID + ", positionNum=" + positionNum
				+ ", departmentName=" + departmentName + ", positionName=" + positionName + ", positionMessage="
				+ positionMessage + "]";
	}
	
}
