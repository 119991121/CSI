package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Position;

public interface PositionMapperService {

	int insert(Position position);
	
	int delete(List<String> names);
	
	List<Position> selectAll();
	
	Position selectByname(String positionName);
	
	Position selectByid(int id);
	
	List<Position> selectBymessage(String message);
	
	int update(String positionName,String departmentID,String new_pos_name,String new_pos_desc );

	int getIdByname(String subordinate_dept);
}
