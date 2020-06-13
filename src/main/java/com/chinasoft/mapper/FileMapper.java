package com.chinasoft.mapper;

import java.util.List;

import com.chinasoft.pojo.File;
import com.chinasoft.pojo.User;

public interface FileMapper {

	int insertFile(File file);

	int deleteFile(int fileId);
	
	List<File> selectFile(File file);
	
	List<File> selectFileByName(String name);
	
	List<File> selectFileAll();
	
	int updateFile(File file);
	
	List<File> selectFileByID(int fileId);
	
}
