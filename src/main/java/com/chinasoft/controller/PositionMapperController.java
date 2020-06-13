package com.chinasoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.pojo.Position;
import com.chinasoft.service.impl.PositionMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/pos")
public class PositionMapperController {
	
	@Autowired
	PositionMapperServiceImpl service;
	
	@RequestMapping(value="/addPos",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		String subordinate_dept = (String) request.get("subordinate_dept");
		String positionName = (String) request.get("pos_name");
		String positionMessage = (String) request.get("pos_desc");
		int departmentID = service.getIdByname(subordinate_dept);
		if(positionMessage==null || positionName==null||positionMessage.equals("") || positionName.equals("")||subordinate_dept==null || subordinate_dept.equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByname(positionName)!=null) {
				rs.put("data",null);
				rs.put("message", "职位已存在");
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
	
	@RequestMapping(value="/deletePos",method= RequestMethod.POST)
	@ResponseBody
	public Object elete(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		List<String> names = (List) request.get("pos_name");
		if(service.delete(names)!=0) {
			rs.put("error_code", 0);
			rs.put("message", "删除成功");
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/selectByName",method= RequestMethod.POST)
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
	
	@RequestMapping(value="/selectPosResult",method= RequestMethod.POST)
	@ResponseBody
	public Object selectByMessage(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		String message= (String) request.get("select_key");
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
	
	@RequestMapping(value="/showAllPos",method= RequestMethod.POST)
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
	
	@RequestMapping(value="/updatePos",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String positionName = (String) request.get("pos_name");
		if(positionName==null||positionName.equals("")) {
			rs.put("data",null);
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			String new_subordinate_dept = (String) request.get("new_subordinate_dept");
			int departmentID = service.getIdByname(new_subordinate_dept);
			String new_pos_name = (String) request.get("new_pos_name");
			String new_pos_desc = (String) request.get("new_pos_desc");
			if(service.selectByname(positionName)==null) {
				rs.put("data",null);
				rs.put("message", "职位不存在");
				rs.put("error_code",2);
			}else {
				if(service.update(positionName,departmentID+"",new_pos_name,new_pos_desc)==1) {
					Position position = service.selectByname(new_pos_name);
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
