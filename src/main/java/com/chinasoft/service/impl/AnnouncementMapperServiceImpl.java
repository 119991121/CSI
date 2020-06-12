package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.AnnouncementMapper;
import com.chinasoft.pojo.Announcement;
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
	public int deleteAnnouncement(List<String> announcementIDs) {
		int result=mapper.deleteAnnouncement(announcementIDs);
		return  result;
	}
	@Override
	public List<Announcement> selectAnnouncement(Announcement announcement) {
		List<Announcement> announcement1 = mapper.selectAnnouncement(announcement);
		return announcement1;
	}
	@Override
	public int updateAnnouncement(Announcement announcement) {
		int result=mapper.updateAnnouncement(announcement);
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
}
