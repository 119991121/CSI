package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinasoft.pojo.Department;

public interface DepartmentMapper {
	int insertDepartment(Department department);

	int deleteDepartment(int DepartmentID);
	
	List<Department> selectAll();
	
	int updateDepartment(Department department);
	
	Department selectByname(String departmentName);
	
	Department selectByid(int departmentID);

	List<Department> selectBymessage(String departmentMessage);
	
}
