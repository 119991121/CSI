package com.chinasoft.service.impl;

import java.text.DateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.OrderingMapper;
import com.chinasoft.pojo.Menu;
import com.chinasoft.service.OrderingMapperService;

@Service
public class OrderingMapperServiceImpl implements OrderingMapperService{
	@Autowired
	OrderingMapper mapper;
	
	@Override
	public int deleteAll() {
		int result = mapper.deleteAll();
		return result;
	}

	@Override
	public int addMenu(Menu menu) {
		int result = mapper.addMenu(menu);
		return result;
	}

	@Override
	public List<String> getLunchs() {
		List<String> lunch = mapper.getLunchs();
		return lunch;
	}

	@Override
	public List<String> getDinners() {
		List<String> dinner = mapper.getDinners();
		return dinner;
	}

	@Override
	public int getSum(String time, String dishName) {
		int result = mapper.getSum(time,dishName);
		return result;
	}

	@Override
	public int selectByTime(String date) {
		int result = mapper.selectByTime(date);
		return result;
	}
	
}
