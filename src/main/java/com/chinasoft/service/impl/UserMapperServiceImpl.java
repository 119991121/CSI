package com.chinasoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasoft.mapper.UserMapper;
import com.chinasoft.pojo.User;
import com.chinasoft.service.UserMapperService;

@Service
public class UserMapperServiceImpl implements UserMapperService {

	@Autowired
	UserMapper mapper;

	@Override
	public int insert(User user) {
		int result=mapper.insertUser(user);
		return result;
	}
	
	@Override
	public int delete(List<String> user_id) {
		int result=mapper.deleteUser(user_id);
		return result;
	}
	
	@Override
	public List<User> select(User user) {
		List<User> user1 = mapper.selectUser(user);
		return user1;
	}
	
	@Override
	public int update(User user){
		int result=mapper.updateUser(user);
		return result;
	}

	@Override
	public int getGroupId(int user_id) {
		int groupId = mapper.getGroupId(user_id);
		return groupId;
	}
	
	@Override
	public User selectByName(String username) {
		User user = mapper.selectByName(username);
		return user;
	}
	
	@Override
	public User selectByid(int user_id) {
		User user = mapper.selectByid(user_id);
		return user;
	}
	
	@Override
	public int getPosition_id(String positionName) {
		int position_id = mapper.getPosition_id(positionName);
		return position_id;
	}

	@Override
	public int getDepartment_id(String departmentName) {
		int department_id = mapper.getDepartment_id(departmentName);
		return department_id;
	}

}
