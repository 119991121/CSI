package com.chinasoft.mapper;

import java.text.DateFormat;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.Menu;

public interface OrderingMapper {

	int deleteAll();

	int addMenu(Menu menu);

	List<String> getLunchs();

	List<String> getDinners();

	int getSum(@Param("time") String time,@Param("dishName") String dishName);

	int selectByTime(String date);

}
