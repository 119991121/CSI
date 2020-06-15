package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Department;

public interface DepartmentMapper {
	int insertDepartment(Department department);

	int deleteDepartment(@Param("dept_name") List<String> dept_name);
	
	List<Department> selectAll();
	
	int updateDepartment(@Param("departmentNameOld") String departmentNameOld,@Param("departmentName") String departmentName, @Param("departmentMessage")String departmentMessage);
	
	Department selectByname(String departmentName);
	
	Department selectByid(int departmentID);

	List<Department> selectBymessage(String departmentMessage);

	int deleteRelated(@Param("departmentID") List<String> departmentID);

	List<String> getIdByName(@Param("dept_name") List<String> dept_name);

}
