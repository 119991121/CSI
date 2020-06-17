package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Position;

public interface PositionMapperService {

	int insert(Position position);
	
	int delete(List<String> names);
	
	List<Position> selectAll();
	
	List<Position> selectByname(String positionName);
	
	String selectByid(int id);
	
	List<Position> selectBymessage(String message,String departmentName);
	
	int update(int pos_id,String departmentID,String new_pos_name,String new_pos_desc );

	Integer getIdByname(String subordinate_dept);
}
