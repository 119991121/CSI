package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.File;
import com.chinasoft.pojo.User;

public interface FileMapper {

	int insertFile(File file);

	int deleteFile(@Param("name")List<String> name);
	
	List<File> selectFile(String select_key);
	
	List<File> selectFileByName(String name);
	
	List<File> selectFileAll();
	
	int updateFile(File file);
	
	List<File> selectFileByID(int fileId);
	
}
