package com.chinasoft.service;

import java.text.DateFormat;
import java.util.List;

import com.chinasoft.pojo.Menu;

public interface OrderingMapperService {

	int deleteAll();

	int addMenu(Menu menu);

	List<String> getLunchs();

	List<String> getDinners();

	int getSum(String time, String dishName);

	int selectByTime(String date);

}
