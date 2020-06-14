package com.chinasoft.mapper;

import java.util.List;

import com.chinasoft.pojo.Ordering;

public interface OrderingMapper {

	int insertOrdering(Ordering ordering);
	
	int nowTimeNum(int time);
	
	int deleteOrdering(int orderingID);
	
	List<Ordering> selectByID(int orderingID);
	
	int updateOrdering(Ordering ordering);
	
	List<Ordering> selectAll();
	
	List<Ordering> selectByUserName(String username);
	
}
