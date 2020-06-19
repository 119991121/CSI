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
		String subordinate_dept = (String) request.get("subordinate_dept");
		String positionName = (String) request.get("pos_name");
		String positionMessage = (String) request.get("pos_desc");
		int departmentID = service.getIdByname(subordinate_dept);
		if(positionMessage==null || positionName==null||positionMessage.equals("") || positionName.equals("")||subordinate_dept==null || subordinate_dept.equals("")) {
			rs.put("message", "信息不完整");
			rs.put("error_code",1);
		}else {
			List<Position> p = service.selectByname(positionName);
			int flag=0;
			if(p!=null&&p.size()!=0) {
				for(int i=0;i<p.size();i++) {
					if(p.get(i).getDepartmentID()==departmentID) {
						flag=1;	
						break;
					}
				}
			}
			if(flag==1) {
				rs.put("message", "职位已存在");
				rs.put("error_code",2);
			}else {
				Position position= new Position();
				position.setDepartmentID(departmentID);
				position.setPositionName(positionName);
				position.setPositionMessage(positionMessage);
				if(service.insert(position)==1) {
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
	
	@RequestMapping(value="/deletePos",method= RequestMethod.POST)
	@ResponseBody
	public Object elete(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		List<String> pos_ids = (List) request.get("pos_id");
		int userNum=service.checkUser(pos_ids).size();
		System.out.println(userNum);
		if(userNum!=0) {
			rs.put("error_code", 3);
			rs.put("message", "该职位尚有员工");	
			return rs;
		}
		if(service.delete(pos_ids)!=0) {
			rs.put("error_code", 0);
			rs.put("message", "删除成功");
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");
			return rs;
		}
	}
	

	
	@RequestMapping(value="/selectPosResult",method= RequestMethod.POST)
	@ResponseBody
	public Object selectByMessage(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		
		String message= (String) request.get("select_key");
		String departmentName= (String) request.get("departmentName");
		List<Position> positions= service.selectBymessage(message,departmentName);
		for(int i=0;i<positions.size();i++) {
			positions.get(i).setPositionNum(service.getNum(positions.get(i).getPositionID()));
		}
		System.out.println("1"+positions);
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
		for(int i=0;i<positions.size();i++) {
			positions.get(i).setPositionNum(service.getNum(positions.get(i).getPositionID()));
		}
		System.out.println("1"+positions);
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
		Integer pos_id = (Integer) request.get("pos_id");
		System.out.println();
		if(pos_id==null||pos_id==0) {
			rs.put("message", "信息不全");
			rs.put("error_code",1);
		}else {
			String new_subordinate_dept = (String) request.get("new_subordinate_dept");
			Integer departmentID = service.getIdByname(new_subordinate_dept);
			String new_pos_name = (String) request.get("new_pos_name");
			String new_pos_desc = (String) request.get("new_pos_desc");
			String pos_name=service.selectByid(pos_id);
			if(pos_name==null||departmentID==null) {
				if(pos_name==null) {
					rs.put("message", "职位不存在");
					rs.put("error_code",4);
				}
				else {
					rs.put("message", "部门不存在");
					rs.put("error_code",5);
				}
			}else {
				List<Position> p=service.selectByname(new_pos_name);
				int flag=0;
				if(p!=null&&p.size()!=0) {
					for(int i=0;i<p.size();i++) {
						if(p.get(i).getDepartmentID()==departmentID&&p.get(i).getPositionID()!=pos_id) {
							flag=1;	
							break;
						}
					}
				}
				if(flag==1) {
					rs.put("message", "职位已存在");
					rs.put("error_code",2);
				}
				else {
					if(service.update(pos_id,departmentID+"",new_pos_name,new_pos_desc)==1) {
						List<Position> position = service.selectByname(new_pos_name);
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
		}
        return rs;
	}
}
