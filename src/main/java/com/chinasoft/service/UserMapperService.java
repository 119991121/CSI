package com.chinasoft.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinasoft.pojo.User;

public interface UserMapperService {

	int insert(User user);
	
	int delete(List<String> user_id);
	
	List<User> select(User user);
	
	int update(User user);
	
	int getGroupId(int user_id);
	
	User selectByName(String username);
	
	User selectByid(int user_id);

	int getPosition_id(String positionName);

	int getDepartment_id(String departmentName);
}
