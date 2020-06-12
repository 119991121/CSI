package com.chinasoft.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.chinasoft.pojo.User;

public interface UserMapper {
	int insertUser(User user);

	int deleteUser(@Param("username")List<String> username);
	
	List<User> selectUser(User user);
	
	int updateUser(User user);

	int getGroupId(int user_id);

	User selectByName(String username);

	User selectByid(int user_id);

	int getPosition_id(String positionName);

	int getDepartment_id(String departmentName);
	
	int registerface(@Param("user_id") String user_id,@Param("faceid") String faceid);

	List<User> selectAll();

	User login(User user);

	int editPassword(@Param("newPassword") String newPassword, @Param("username") String username);

	User selectDetail(String username);
	
}
