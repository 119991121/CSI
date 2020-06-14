package com.chinasoft.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.pojo.Department;
import com.chinasoft.service.impl.DepartmentMapperServiceImpl;


@Controller
@CrossOrigin
@RequestMapping("/dept")
public class DepartmentMapperController {
	
	@Autowired
	DepartmentMapperServiceImpl service;
	
	@RequestMapping(value="/addDept",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		String departmentName = (String) request.get("dept_name");
		String departmentMessage = (String) request.get("dept_desc");
		if(departmentMessage ==null || departmentName ==null||departmentMessage.equals("") || departmentName.equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			if(service.selectByname(departmentName)!=null) {
				rs.put("message", "部门已存在");
				rs.put("error_code",2);
			}else {
				Department department= new Department();
				department.setDepartmentName(departmentName);
				department.setDepartmentMessage(departmentMessage);
				if(service.insert(department)==1) {
					department = service.selectByname(departmentName);
					rs.put("message", "添加成功");
					rs.put("error_code",0);
				}else {
					rs.put("message", "添加失败");
					rs.put("error_code",3);
				}
			}
		}
        return rs;
	}
	
	@RequestMapping(value="/deleteDept",method= RequestMethod.POST)
	@ResponseBody
	public Object Delete(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		List<String> dept_name = (List) request.get("dept_name");
		if(dept_name==null||dept_name.size()==0) {
			rs.put("error_code", 1);
			rs.put("message", "部门名称为空");
		}
		else{
			if(service.delete(dept_name)!=0) {
				rs.put("error_code", 0);
				rs.put("message", "删除成功");			
			}else {
				rs.put("error_code", 2);
				rs.put("message", "删除失败");
			}
		}
		return rs;
	}
	
	@RequestMapping(value="/selectByName",method= RequestMethod.POST)
	@ResponseBody
	public Object SelectByName(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		String name= (String) request.get("departmentName");
		Department department = service.selectByname(name);
		if(department!=null) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", department);
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/selectDeptResult",method= RequestMethod.POST)
	@ResponseBody
	public Object selectByMessage(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String select_key= (String) request.get("select_key");
		if(select_key==null&&select_key.equals("")) {
			rs.put("error_code", 1);
			rs.put("message", "关键字为空");
			return rs;
		}
		List<Department> department = service.selectBymessage(select_key);
		if(department!=null&&department.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", department);
			return rs;
		}else {
			rs.put("error_code", 2);
			rs.put("message", "查询信息不存在");
			return rs;
		}
	}
	
	@RequestMapping(value="/showAllDept",method= RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		Map<String,Object> rs = new HashMap<>();
		List<Department> department = service.selectAll();
		if(department!=null&&department.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", department);
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/updateDept",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String departmentNameOld = (String) request.get("dept_name");
		String departmentName = (String) request.get("new_dept_name");
		String departmentMessage = (String) request.get("new_dept_desc");
		if(departmentNameOld==null||departmentNameOld.equals("")||departmentName==null||departmentName.equals("")||departmentMessage==null||departmentMessage.equals("")) {
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			if(service.selectByname(departmentNameOld)==null) {
				rs.put("message", "部门不存在");
				rs.put("error_code",2);
			}else {
				if(service.update(departmentNameOld, departmentName, departmentMessage)==1) {
					rs.put("message", "更新成功");
					rs.put("error_code",0);
				}else {
					rs.put("message", "更新失败");
					rs.put("error_code",3);
				}
			}
		}
        return rs;
	}
}
