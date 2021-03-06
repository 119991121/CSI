package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.AnnouncementMapper;
import com.chinasoft.pojo.Announcement;
import com.chinasoft.pojo.User;
import com.chinasoft.service.AnnouncementMapperService;

@Service
public class AnnouncementMapperServiceImpl implements AnnouncementMapperService {
	@Autowired
	AnnouncementMapper mapper;
	
	@Override
	public int insertAnnouncement(Announcement announcement) {
		int result=mapper.insertAnnouncement(announcement);
		return  result;
	}
	
	@Override
	public int deleteAnnouncement(List<String> names) {
		int result=mapper.deleteAnnouncement(names);
		return  result;
	}
	@Override
	public List<Announcement> selectAnnouncement(String select_key) {
		List<Announcement> announcement1 = mapper.selectAnnouncement(select_key);
		return announcement1;
	}
	@Override
	public int updateAnnouncement(String name,String new_name,String content) {
		int result=mapper.updateAnnouncement(name,new_name,content);
		return result;
	}

	@Override
	public Announcement selectByid(int id) {
		Announcement result = mapper.selectByid(id);
		return result;
	}
	
	@Override
	public List<Announcement> selectAll() {
		List<Announcement> announcements = mapper.selectAll();
		return announcements;
	}

	@Override
	public Announcement selectByName(String name) {
		Announcement result = mapper.selectByName(name);
		return result;
	}

	@Override
	public Integer getIdByUsername(String username) {
		Integer result = mapper.getIdByUsername(username);
		return result;
	}

	@Override
	public User checkUsername(String username) {
		User result = mapper.checkUsername(username);
		return result;
	}
}
