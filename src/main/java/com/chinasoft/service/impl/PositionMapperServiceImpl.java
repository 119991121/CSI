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
	public int delete(List<String> names) {
		int result = mapper.deletePosition(names);
		return result;
	}
	
	@Override
	public List<Position> selectAll() {
		List<Position> positionlist= mapper.selectAll();
		return positionlist;
	}
	
	@Override
	public int update(int pos_id,String departmentID,String new_pos_name,String new_pos_desc ){
		int result = mapper.updatePosition(pos_id,departmentID,new_pos_name,new_pos_desc);
		return result;
	}

	@Override
	public List<Position> selectByname(String positionName) {
		List<Position> result = mapper.selectByname(positionName);
		return result;
	}

	@Override
	public String selectByid(int id) {
		String result = mapper.selectByid(id);
		return result;
	}

	@Override
	public List<Position> selectBymessage(String message) {
		List<Position> result = mapper.selectBymessage(message);
		return result;
	}

	@Override
	public Integer getIdByname(String subordinate_dept) {
		Integer result = mapper.getIdByname(subordinate_dept);
		return result;
	}

}
