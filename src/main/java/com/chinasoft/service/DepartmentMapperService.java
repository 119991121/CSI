package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Department;
import com.chinasoft.pojo.User;

public interface DepartmentMapperService {

	int insert(Department department);
	
	int delete(List<String> dept_name);
	
	List<Department> selectAll();
	
	Department selectByname(String departmentName);
	
	Department selectByid(int id);
	
	List<Department> selectBymessage(String message);
	
	int update(String departmentNameOld, String departmentName, String departmentMessage);

	int deleteRelated(List<String> departmentID);

	List<String> getIdByName(List<String> dept_name);

	Integer getNum(int departmentID);

	List<User> checkUser(List<String> dept_name);
}
