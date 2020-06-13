package com.chinasoft.service;

import com.chinasoft.pojo.Announcement;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AnnouncementMapperService {
	int insertAnnouncement(Announcement announcement);
	
	int deleteAnnouncement(List<String> names);
	
	List<Announcement> selectAnnouncement(String select_key);

	int updateAnnouncement(String name,String new_name,String content);
	
	Announcement selectByid(int id);

	List<Announcement> selectAll();

	Announcement selectByName(String name);

	int getIdByUsername(String username);
}
