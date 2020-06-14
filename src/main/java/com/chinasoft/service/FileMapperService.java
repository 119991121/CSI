package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.File;

public interface FileMapperService {

	int insert(File file);
	
	int delete(String name);
	
	List<File> select(File file);
	
	List<File> selectByName(String name);
	
	List<File> selectFileAll();
	
	int update(File file);
	
	List<File> selectWithID(int fileId);
	
}
