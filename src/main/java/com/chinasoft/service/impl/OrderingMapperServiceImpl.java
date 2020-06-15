package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.OrderingMapper;

import com.chinasoft.pojo.Ordering;
import com.chinasoft.pojo.Menu;
import com.chinasoft.service.OrderingMapperService;

@Service
public class OrderingMapperServiceImpl implements OrderingMapperService {

	@Autowired
	OrderingMapper mapper;

	@Override
	public int insert(Ordering ordering) {
		// TODO insert ordering.
		int result = mapper.insertOrdering(ordering);
		return result;
	}

	@Override
	public int deleteAll() {
		int result = mapper.deleteAll();
		return result;
	}

	@Override
	public int nowTimeNum(int time) {
		// TODO the ordering number in this time.
		int result = mapper.nowTimeNum(time);
		return result;
	}

	@Override
	public int addMenu(Menu menu) {
		int result = mapper.addMenu(menu);
		return result;
	}

	@Override
	public int delete(int orderingID) {
		// TODO delete ordering by id.
		int result = mapper.deleteOrdering(orderingID);
		return result;
	}

	@Override
	public List<Ordering> selectByID(int orderingID) {
		// TODO select ordering by orderingID.
		List<Ordering> results = mapper.selectByID(orderingID);
		return results;
	}

	@Override
	public int updateOrdering(Ordering ordering) {
		// TODO update ordering.
		int result = mapper.updateOrdering(ordering);
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
	public List<Ordering> selectAll() {
		// TODO select all ordering.
		List<Ordering> results = mapper.selectAll();
		return results;
	}

	@Override
	public List<Ordering> selectByUserName(String username) {
		// TODO select by name.
		List<Ordering> results = mapper.selectByUserName(username);
		return results;
	}

	@Override
	public int selectByTime(String date) {
		int result = mapper.selectByTime(date);
		return result;
	}

	@Override
	public int deleteAllByName(String username) {
		// TODO delete from ordering by username
		int result = mapper.deleteAllByName(username);
		return result;
	}

}
