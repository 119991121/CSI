package com.chinasoft.controller;

import java.util.Date;
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

import com.chinasoft.pojo.Announcement;
import com.chinasoft.service.impl.AnnouncementMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/announce")
public class AnnouncementMapperController {
	@Autowired
	AnnouncementMapperServiceImpl service;
	
	@RequestMapping(value="/add",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String name=(String) request.get("name");
		String content=(String) request.get("content");
		String username=(String) request.get("username");
		if(name ==null || content ==null||name.equals("") || content.equals("")||username==null ||username.equals("")) {
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByName(name)!=null) {
				rs.put("message", "公告名重复");
				rs.put("error_code",3);
			}
			else{
				Integer userID=service.getIdByUsername(username);
				if(userID==null) {
					rs.put("message", "用户不存在");
					rs.put("error_code",2);
				}
				else {
					Announcement announcement = new Announcement();
					announcement.setName(name);
					announcement.setContent(content);
					announcement.setCreatTime(new Date());
					announcement.setUserID(userID);	
					if(service.insertAnnouncement(announcement)==1) {
						rs.put("message", "发布成功");
						rs.put("error_code",0);
					}
				}
			}
		}
        return rs;
	}
	
	
	@RequestMapping(value="/delete",method= RequestMethod.POST)
	@ResponseBody
	public Object Delete(@RequestBody Map<String,Object> request) {
		List<String> names = (List) request.get("name");	
		Map<String,Object> rs = new HashMap<>();
		if(service.deleteAnnouncement(names)!=0) {
			rs.put("error_code", 0);
			rs.put("message", "删除成功");
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");

		}
        return rs;
	}
	
	@RequestMapping(value="/selectresult",method= RequestMethod.POST)
	@ResponseBody
	public Object Select(@RequestBody Map<String,Object> request) {
		String select_key=(String) request.get("select_key");
		List<Announcement> announcements = service.selectAnnouncement(select_key);
		Map<String,Object> rs = new HashMap<>();	
		if(announcements!=null && announcements.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", announcements);
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
		}
        return rs;
	}
	
	@RequestMapping(value="/showAll",method= RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		Map<String,Object> rs = new HashMap<>();
		List<Announcement> announcements= service.selectAll();
		if(announcements!=null&&announcements.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", announcements);
			System.out.println(announcements);
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
		String name=(String) request.get("name");
		String new_name=(String) request.get("new_name");
		String content=(String) request.get("new_content");
		Map<String,Object> rs = new HashMap<>();	
		if(name==null||name.equals("")||content==null||content.equals("")||new_name==null||new_name.equals("")) {
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByName(name)==null) {
				rs.put("message", "公告不存在");
				rs.put("error_code",3);
			}else {
				if(service.selectByName(new_name)!=null&&!new_name.equals(name)) {
					rs.put("message", "新公告名已存在");
					rs.put("error_code",3);
				}
				else {
					if(service.updateAnnouncement(name,new_name,content)==1) {
						Announcement announcement =service.selectByName(name);
						rs.put("data",announcement);
						rs.put("message", "修改成功");
						rs.put("error_code",0);
					}else {
						rs.put("data",null);
						rs.put("message", "修改失败");
						rs.put("error_code",3);
					}
				}
			}
		}
        return rs;
	}
}
