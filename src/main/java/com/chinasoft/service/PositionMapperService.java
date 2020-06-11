package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Position;

public interface PositionMapperService {

	int insert(Position position);
	
	int delete(int positionID);
	
	List<Position> selectAll();
	
	Position selectByname(String positionName);
	
	Position selectByid(int id);
	
	List<Position> selectBymessage(String message);
	
	int update(Position position);
}
