package com.chinasoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.pojo.Position;
import com.chinasoft.service.impl.PositionMapperServiceImpl;

@Controller
@RequestMapping("/position")
public class PositionMapperController {
	
	@Autowired
	PositionMapperServiceImpl service;
	
	@RequestMapping(value="/add",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		int departmentID = (int) request.get("departmentID");
		String positionName = (String) request.get("positionName");
		String positionMessage = (String) request.get("positionMessage");
		if(positionMessage==null || positionName==null|| String.valueOf(departmentID)==null || String.valueOf(departmentID).equals("") ||positionMessage.equals("") || positionName.equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByname(positionName)!=null) {
				rs.put("data",null);
				rs.put("message", "用户已存在");
				rs.put("error_code",2);
			}else {
				Position position= new Position();
				position.setDepartmentID(departmentID);
				position.setPositionName(positionName);
				position.setPositionMessage(positionMessage);
				if(service.insert(position)==1) {
					position = service.selectByname(positionName);
					rs.put("data",position);
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
		
		int id = (int) request.get("positionID");
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
		
		String name= (String) request.get("positionName");
		Position position= service.selectByname(name);
		if(position!=null) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", position);
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
		
		String message= (String) request.get("positionMessage");
		List<Position> positions= service.selectBymessage(message);
		if(positions!=null) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", positions);
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
		
		List<Position> positions= service.selectAll();
		if(positions!=null) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", positions);
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
		if(request.get("positionID")==null||request.get("positionID").equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			int positionID = (int) request.get("positionID");
			int departmentID = (int) request.get("departmentID");
			String positionName = (String) request.get("positionName");
			String positionMessage = (String) request.get("positionMessage");
			if(service.selectByid(positionID)==null) {
				rs.put("data",null);
				rs.put("message", "用户不存在");
				rs.put("error_code",2);
			}else {
				Position position= new Position(positionID, departmentID, positionName, positionMessage);
				if(service.update(position)==1) {
					rs.put("data",position);
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
