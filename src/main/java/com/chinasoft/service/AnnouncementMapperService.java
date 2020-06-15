package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Announcement;
import com.chinasoft.pojo.User;

public interface AnnouncementMapperService {
	int insertAnnouncement(Announcement announcement);
	
	int deleteAnnouncement(List<String> names);
	
	List<Announcement> selectAnnouncement(String select_key);

	int updateAnnouncement(String name,String new_name,String content);
	
	Announcement selectByid(int id);

	List<Announcement> selectAll();

	Announcement selectByName(String name);

	Integer getIdByUsername(String username);

	User checkUsername(String username);
}
