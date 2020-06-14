package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Ordering;

public interface OrderingMapperService {

	int insert(Ordering ordering);
	
	int nowTimeNum(int time);
	
	int delete(int orderingID);
	
	List<Ordering> selectByID(int orderingID);
	
	int updateOrdering(Ordering ordering);
	
	List<Ordering> selectAll();
	
	List<Ordering> selectByUserName(String username);
}
