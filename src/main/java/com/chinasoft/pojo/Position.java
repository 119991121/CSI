package com.chinasoft.pojo;

import java.io.Serializable;

public class Position implements Serializable{
	private int positionID;
	private int departmentID;
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
	@Override
	public String toString() {
		return "Position [positionID=" + positionID + ", departmentID=" + departmentID + ", positionName="
				+ positionName + ", positionMessage=" + positionMessage + "]";
	}
	public Position(int positionID, int departmentID, String positionName, String positionMessage) {
		super();
		this.positionID = positionID;
		this.departmentID = departmentID;
		this.positionName = positionName;
		this.positionMessage = positionMessage;
	}
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
