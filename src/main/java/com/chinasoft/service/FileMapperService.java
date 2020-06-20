package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.File;

public interface FileMapperService {

	int insert(File file);
	
	int delete(List<String> name);
	
	List<File> select(String select_key);
	
	List<File> selectByName(String name);
	
	List<File> selectFileAll();
	
	int update(File file);
	
	List<File> selectWithID(int fileId);
	
	File selectone(String name);
	
}
