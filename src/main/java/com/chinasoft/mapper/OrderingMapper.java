package com.chinasoft.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Menu;

import com.chinasoft.pojo.Ordering;

public interface OrderingMapper {

	int insertOrdering(Ordering ordering);
	
	int nowTimeNum(int time);
	
	int deleteOrdering(int orderingID);
	
	List<Ordering> selectByID(int orderingID);
	
	int updateOrdering(Ordering ordering);
	
	List<Ordering> selectAll();
	
	List<Ordering> selectByUserName(String username);
	
	int deleteAll();

	int addMenu(Menu menu);

	List<String> getLunchs();

	List<String> getDinners();

	int getSum(@Param("time") String time,@Param("dishName") String dishName);

	int selectByTime(String date);


}
