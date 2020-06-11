package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Announcement;

public interface AnnouncementMapper {
	int insertAnnouncement(Announcement announcement);

	int deleteAnnouncement(@Param("announcementIDs")List<String> announcementIDs);

	List<Announcement> selectAnnouncement(Announcement announcement);
	
	int updateAnnouncement(Announcement announcement);

	Announcement selectByid(int id);
}
