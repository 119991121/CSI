package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Announcement;

public interface AnnouncementMapper {
	int insertAnnouncement(Announcement announcement);

	int deleteAnnouncement(@Param("names")List<String> names);

	List<Announcement> selectAnnouncement(String select_key);
	
	int updateAnnouncement(@Param("name")String name,@Param("new_name")String new_name,@Param("content")String content);

	Announcement selectByid(int id);

	List<Announcement> selectAll();

	Announcement selectByName(String name);

	int getIdByUsername(String username);
}
