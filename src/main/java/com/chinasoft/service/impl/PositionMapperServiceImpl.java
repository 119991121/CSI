package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.PositionMapper;
import com.chinasoft.pojo.Position;
import com.chinasoft.service.PositionMapperService;

@Service
public class PositionMapperServiceImpl implements PositionMapperService {

	@Autowired
	PositionMapper mapper;

	@Override
	public int insert(Position position) {
		int result = mapper.insertPosition(position);
		return result;
	}
	
	@Override
	public int delete(int positionID) {
		int result = mapper.deletePosition(positionID);
		return result;
	}
	
	@Override
	public List<Position> selectAll() {
		List<Position> positionlist= mapper.selectAll();
		return positionlist;
	}
	
	@Override
	public int update(Position position){
		int result = mapper.updatePosition(position);
		return result;
	}

	@Override
	public Position selectByname(String positionName) {
		Position result = mapper.selectByname(positionName);
		return result;
	}

	@Override
	public Position selectByid(int id) {
		Position result = mapper.selectByid(id);
		return result;
	}

	@Override
	public List<Position> selectBymessage(String message) {
		List<Position> result = mapper.selectBymessage(message);
		return result;
	}

}
