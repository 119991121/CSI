package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.DepartmentMapper;
import com.chinasoft.pojo.Department;
import com.chinasoft.pojo.User;
import com.chinasoft.service.DepartmentMapperService;

@Service
public class DepartmentMapperServiceImpl implements DepartmentMapperService {

	@Autowired
	DepartmentMapper mapper;

	@Override
	public int insert(Department department) {
		int result = mapper.insertDepartment(department);
		return result;
	}
	
	@Override
	public int delete(List<String> dept_name) {
		int result = mapper.deleteDepartment(dept_name);
		return result;
	}
	
	@Override
	public List<Department> selectAll() {
		List<Department> departmentlist = mapper.selectAll();
		return departmentlist;
	}
	
	@Override
	public int update(String departmentNameOld, String departmentName, String departmentMessage) {
		int result = mapper.updateDepartment(departmentNameOld, departmentName, departmentMessage);
		return result;

	}

	@Override
	public Department selectByname(String departmentName) {
		Department result = mapper.selectByname(departmentName);
		return result;
	}

	@Override
	public Department selectByid(int id) {
		Department result = mapper.selectByid(id);
		return result;
	}

	@Override
	public List<Department> selectBymessage(String message) {
		List<Department> result = mapper.selectBymessage(message);
		return result;
	}

	@Override
	public int deleteRelated(List<String> departmentID) {
		int result = mapper.deleteRelated(departmentID);
		return result;
	}

	@Override
	public List<String> getIdByName(List<String> dept_name) {
		List<String> result = mapper.getIdByName(dept_name);
		return result;
	}

	@Override
	public Integer getNum(int departmentID) {
		Integer result = mapper.getNum(departmentID);
		return result;
	}

	@Override
	public List<User> checkUser(List<String> dept_name) {
		List<User> result = mapper.checkUser(dept_name);
		return result;
	}

}
