package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Announcement;

public interface AnnouncementMapper {
	int insertAnnouncement(Announcement announcement);

	int deleteAnnouncement(@Param("names")List<String> names);

	List<Announcement> selectAnnouncement(Announcement announcement);
	
	int updateAnnouncement(Announcement announcement);

	Announcement selectByid(int id);

	List<Announcement> selectAll();

	Announcement selectByName(String name);

	int getIdByUsername(String username);
}
