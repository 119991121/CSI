package com.chinasoft.service;

import com.chinasoft.pojo.Announcement;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnnouncementMapperService {
	int insertAnnouncement(Announcement announcement);
	
	int deleteAnnouncement(List<String> announcementIDs);
	
	List<Announcement> selectAnnouncement(Announcement announcement);

	int updateAnnouncement(Announcement announcement);
	
	Announcement selectByid(int id);
}
