package com.chinasoft.service;

import java.util.List;

import com.chinasoft.pojo.Ordering;
import com.chinasoft.pojo.Menu;

public interface OrderingMapperService {

	int insert(Ordering ordering);
	
	int nowTimeNum(int time);
	
	int delete(int orderingID);
	
	List<Ordering> selectByID(int orderingID);
	
	int updateOrdering(Ordering ordering);
	
	List<Ordering> selectAll();
	
	List<Ordering> selectByUserName(String username);
	
	int deleteAll();

	int addMenu(Menu menu);

	List<String> getLunchs();

	List<String> getDinners();

	int getSum(String time, String dishName);

	int selectByTime(String date);


}
