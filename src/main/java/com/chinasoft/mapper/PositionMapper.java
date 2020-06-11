package com.chinasoft.mapper;

import java.util.List;

import com.chinasoft.pojo.Position;

public interface PositionMapper {
	int insertPosition(Position position);

	int deletePosition(int positionID);
	
	List<Position> selectAll();
	
	int updatePosition(Position position);
	
	Position selectByname(String positionName);
	
	Position selectByid(int positionID);

	List<Position> selectBymessage(String positionMessage);
	
}
