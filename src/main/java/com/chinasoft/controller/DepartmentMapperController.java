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
@RequestMapping("/department")
public class DepartmentMapperController {
	
	@Autowired
	DepartmentMapperServiceImpl service;
	
	@RequestMapping(value="/add",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		String departmentName = (String) request.get("departmentName");
		String departmentMessage = (String) request.get("departmentMessage");
		if(departmentMessage ==null || departmentName ==null||departmentMessage.equals("") || departmentName.equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			if(service.selectByname(departmentName)!=null) {
				rs.put("data",null);
				rs.put("message", "用户已存在");
				rs.put("error_code",2);
			}else {
				Department department= new Department();
				department.setDepartmentName(departmentName);
				department.setDepartmentMessage(departmentMessage);
				if(service.insert(department)==1) {
					department = service.selectByname(departmentName);
					rs.put("data",department);
					rs.put("message", "添加成功");
					rs.put("error_code",0);
				}else {
					rs.put("data",null);
					rs.put("message", "添加失败");
					rs.put("error_code",3);
				}
			}
		}
        return rs;
	}
	
	@RequestMapping(value="/delete",method= RequestMethod.POST)
	@ResponseBody
	public Object elete(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		int id = (int) request.get("departmentID");
		if(service.delete(id)==1) {
			rs.put("error_code", 0);
			rs.put("message", "删除成功");
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/selectByName",method= RequestMethod.GET)
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
	
	@RequestMapping(value="/selectByMessage",method= RequestMethod.GET)
	@ResponseBody
	public Object selectByMessage(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		String message= (String) request.get("departmentMessage");
		List<Department> department = service.selectBymessage(message);
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
	
	@RequestMapping(value="/selectAll",method= RequestMethod.GET)
	@ResponseBody
	public Object selectAll() {
		Map<String,Object> rs = new HashMap<>();
		
		List<Department> department = service.selectAll();
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
	
	@RequestMapping(value="/update",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		if(request.get("departmentID")==null||request.get("departmentID").equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			int departmentID = (int) request.get("departmentID");
			String departmentName = (String) request.get("departmentName");
			String departmentMessage = (String) request.get("departmentMessage");
			if(service.selectByid(departmentID)==null) {
				rs.put("data",null);
				rs.put("message", "用户不存在");
				rs.put("error_code",2);
			}else {
				Department department= new Department(departmentID, departmentName, departmentMessage);
				if(service.update(department)==1) {
					rs.put("data",department);
					rs.put("message", "更新成功");
					rs.put("error_code",0);
				}else {
					rs.put("data",null);
					rs.put("message", "更新失败");
					rs.put("error_code",3);
				}
			}
		}
        return rs;
	}
}
