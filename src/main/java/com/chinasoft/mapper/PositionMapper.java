package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Position;

public interface PositionMapper {
	int insertPosition(Position position);

	int deletePosition(@Param("names")List<String> names);
	
	List<Position> selectAll();
	
	int updatePosition(@Param("positionName") String positionName,@Param("departmentID")String departmentID,@Param("new_pos_name")String new_pos_name,@Param("new_pos_desc")String new_pos_desc );
	
	Position selectByname(String positionName);
	
	Position selectByid(int positionID);

	List<Position> selectBymessage(String positionMessage);

	int getIdByname(String subordinate_dept);
	
}
