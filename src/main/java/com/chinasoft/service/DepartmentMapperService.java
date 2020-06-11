package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Department;

public interface DepartmentMapperService {

	int insert(Department department);
	
	int delete(int DepartmentID);
	
	List<Department> selectAll();
	
	Department selectByname(String departmentName);
	
	Department selectByid(int id);
	
	List<Department> selectBymessage(String message);
	
	int update(Department department);
}
