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
@RequestMapping("/announcement")
public class AnnouncementMapperController {
	@Autowired
	AnnouncementMapperServiceImpl service;
	
	@RequestMapping(value="/addAnnouncement",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String name=(String) request.get("name");
		String content=(String) request.get("content");
		int userID=(int) request.get("userID");
		if(name ==null || content ==null||name.equals("") || content.equals("")||userID==0) {
			rs.put("data",null);
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			Announcement announcement = new Announcement();
			announcement.setName(name);
			announcement.setContent(content);
			announcement.setCreatTime(new Date());
			announcement.setUserID(userID);	
				if(service.insertAnnouncement(announcement)==1) {
					rs.put("data",announcement);
					rs.put("message", "新增成功");
					rs.put("error_code",0);
				}
		}
        return rs;
	}
	
	
	@RequestMapping(value="/deleteAnnouncement",method= RequestMethod.POST)
	@ResponseBody
	public Object Delete(@RequestBody Map<String,Object> request) {
		List<String> announcementIDs = (List) request.get("announcementID");	
		Map<String,Object> rs = new HashMap<>();
		if(service.deleteAnnouncement(announcementIDs)!=0) {
			rs.put("error_code", 0);
			rs.put("message", "删除成功");
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");

		}
        return rs;
	}
	
	@RequestMapping(value="/selectAnnouncement",method= RequestMethod.POST)
	@ResponseBody
	public Object Select(@RequestBody Map<String,Object> request) {
		String name=(String) request.get("name");
		String content=(String) request.get("content");
		Announcement announcement = new Announcement();
		announcement.setName(name);
		announcement.setContent(content);
		System.out.println(announcement);
		List<Announcement> announcements = service.selectAnnouncement(announcement);
		System.out.println("announcements:"+announcements);
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
	
	@RequestMapping(value="/selectAll",method= RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		Map<String,Object> rs = new HashMap<>();
		List<Announcement> announcements= service.selectAll();
		System.out.println(announcements);
		if(announcements!=null&&announcements.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", announcements);
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/updateAnnouncement",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {
		String name=(String) request.get("name");
		String content=(String) request.get("content");
		int announcementID=(int) request.get("announcementID");
		Announcement announcement = new Announcement();
		announcement.setName(name);
		announcement.setContent(content);
		announcement.setAnnouncementID(announcementID);
		Map<String,Object> rs = new HashMap<>();	
		if(name==null||name.equals("")||announcementID==0||content==null||content.equals("")) {
			rs.put("data",null);
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByid(announcementID)==null) {
				rs.put("data",null);
				rs.put("message", "公告不存在");
				rs.put("error_code",2);
			}else 
				if(service.updateAnnouncement(announcement)==1) {
					rs.put("data",announcement);
					rs.put("message", "修改成功");
					rs.put("error_code",0);
				}else {
					rs.put("data",null);
					rs.put("message", "修改失败");
					rs.put("error_code",3);
				}
		}
        return rs;
	}
}
