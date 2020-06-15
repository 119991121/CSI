package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.FileMapper;
import com.chinasoft.pojo.File;
import com.chinasoft.service.FileMapperService;

@Service
public class FileMapperServiceImpl implements FileMapperService{

	@Autowired
	FileMapper mapper;
	
	@Override
	public int insert(File file) {
		// TODO insert file table with value = file's value.
		int result = mapper.insertFile(file);
		return result;
	}

	@Override
	public int delete(List<String> name) {
		// TODO delete file table with value = file_id.
		int result = mapper.deleteFile(name);
		return result;
	}

	@Override
	public List<File> select(String select_key) {
		// TODO select file table with name.
		List<File> files = mapper.selectFile(select_key);
		return files;
	}

	@Override
	public int update(File file) {
		// TODO update file table with value = file's value.
		int result = mapper.updateFile(file);
		return result;
	}

	@Override
	public List<File> selectWithID(int fileId) {
		// TODO select file.herf table with fileId.
		List<File> herf = mapper.selectFileByID(fileId);
		return herf;
	}

	@Override
	public List<File> selectFileAll() {
		// TODO select all file.
		List<File> files = mapper.selectFileAll();
		return files;
	}

	@Override
	public List<File> selectByName(String name) {
		// TODO 
		List<File> files = mapper.selectFileByName(name);
		return files;
	}
	

}
