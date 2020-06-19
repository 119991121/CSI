package com.chinasoft.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User  implements Serializable{
	private Integer user_id;
	private String username;
	private String password;
	private String name;
	private Integer groupId;
	private Date createdDate;
	private String phone;
	private Integer sex;
	private String email;
	private Integer position_id;
	private String positionName;
	private String positionMessage;
	private String education;
	private String idCardNo;
	private Integer department_id;
	private String departmentName;
	private String departmentMessage;
	private String address;
	private String face_id;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPosition_id() {
		return position_id;
	}
	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
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
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public Integer getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(Integer department_id) {
		this.department_id = department_id;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFace_id() {
		return face_id;
	}
	public void setFace_id(String face_id) {
		this.face_id = face_id;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer user_id, String username, String password, String name, Integer groupId, Date createdDate, String phone,
			Integer sex, String email, Integer position_id, String positionName, String positionMessage, String education,
			String idCardNo, Integer department_id, String departmentName, String departmentMessage, String address,
			String face_id) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.groupId = groupId;
		this.createdDate = createdDate;
		this.phone = phone;
		this.sex = sex;
		this.email = email;
		this.position_id = position_id;
		this.positionName = positionName;
		this.positionMessage = positionMessage;
		this.education = education;
		this.idCardNo = idCardNo;
		this.department_id = department_id;
		this.departmentName = departmentName;
		this.departmentMessage = departmentMessage;
		this.address = address;
		this.face_id = face_id;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", groupId=" + groupId + ", createdDate=" + createdDate + ", phone=" + phone + ", sex=" + sex
				+ ", email=" + email + ", position_id=" + position_id + ", positionName=" + positionName
				+ ", positionMessage=" + positionMessage + ", education=" + education + ", idCardNo=" + idCardNo
				+ ", department_id=" + department_id + ", departmentName=" + departmentName + ", departmentMessage="
				+ departmentMessage + ", address=" + address + ", face_id=" + face_id + "]";
	}

	
	
}
