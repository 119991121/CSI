package com.chinasoft.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.AiFace.FaceDetect;
import com.chinasoft.pojo.Announcement;
import com.chinasoft.pojo.User;
import com.chinasoft.service.impl.UserMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserMapperController {
	
	@Autowired
	UserMapperServiceImpl service;
	@RequestMapping(value="/adduser",method= RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestBody Map<String,Object> request) {
		String username=(String) request.get("username");
		String password=(String) request.get("password");
		String S_groupId=(String)request.get("groupid");
		Map<String,Object> rs = new HashMap<>();	
		if(username ==null || password ==null||username.equals("") || password.equals("")||S_groupId==null|| S_groupId.equals("")) {
			rs.put("data",null);
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		} else {
			if(service.selectByName(username)!=null) {
				rs.put("data",null);
				rs.put("message", "用户名已存在");
				rs.put("error_code",2);
			}
			else {
				int groupId=Integer.parseInt(S_groupId);
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				user.setGroupId(groupId);
				user.setCreatedDate(new Date());
				if(service.insert(user)==1) {
					user = service.selectByName(username);
					rs.put("data",user);
					rs.put("message", "新增成功");
					rs.put("error_code",0);
				}
			}     
        }
		return rs;
	}
	
	@RequestMapping(value="/delete",method= RequestMethod.POST)
	@ResponseBody
	public Object Delete(@RequestBody Map<String,Object> request) throws IOException {
		List<String> user_id=(List) request.get("user_id");
		Map<String,Object> rs = new HashMap<>();
		if(service.delete(user_id)!=0) {
			for(String userid : user_id) {
				try {
					UserFaceController.deleteuser(userid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			rs.put("message","数据删除成功");
			rs.put("error_code",0);
		}else {
			rs.put("error_code", 1);
			rs.put("message", "删除失败");

		}      
        return rs;
	}
	
	@RequestMapping(value="/selectUser",method= RequestMethod.POST)
	@ResponseBody
	public Object Select(@RequestBody Map<String,Object> request) {
		User user = new User();
		String name=(String) request.get("name");
		String idCardNo=(String) request.get("idCardNo");
		String positionName=(String) request.get("positionName");
		String S_user_id=(String) request.get("user_id");
		if(S_user_id==null|| S_user_id.equals("")) {
			S_user_id="0";
		}
		int user_id=Integer.parseInt(S_user_id);
		String departmentName=(String) request.get("departmentName");
		user.setUser_id(user_id);
		user.setIdCardNo(idCardNo);
		user.setName(name);
		user.setPositionName(positionName);
		user.setDepartmentName(departmentName);
		List<User> users =service.select(user);
		Map<String,Object> rs = new HashMap<>();	
		if(users!=null && users.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", users);
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
		}
        return rs;
	}
	
	@RequestMapping(value="/update",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {
		String S_user_id=(String) request.get("user_id");
		if(S_user_id==null|| S_user_id.equals("")) {
			S_user_id="0";
		}
		int user_id=Integer.parseInt(S_user_id);
		
		String S_groupId=(String) request.get("groupId");
		if(S_groupId==null|| S_groupId.equals("")) {
			S_groupId="0";
		}
		int groupId=Integer.parseInt(S_groupId);
		
		String positionName=(String) request.get("positionName");
		String departmentName=(String) request.get("departmentName");		
		
		int position_id=service.getPosition_id(positionName);
		int department_id=service.getDepartment_id(departmentName);;
		
		String username=(String) request.get("username");
		String password=(String) request.get("password");
		String phone=(String) request.get("phone");
		String name=(String) request.get("name");
		String sex=(String) request.get("sex");
		String email=(String) request.get("email");
		String education=(String) request.get("education");
		String idCardNo=(String) request.get("idCardNo");
		String address=(String) request.get("address");
		Date createdDate=new Date();
		
		
		User user = new User();
		user.setUser_id(user_id);
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setGroupId(groupId);
		user.setName(name);
		user.setSex(sex);
		user.setEmail(email);
		user.setPosition_id(position_id);
		user.setEducation(education);
		user.setIdCardNo(idCardNo);
		user.setDepartment_id(department_id);
		user.setAddress(address);
		user.setCreatedDate(createdDate);
		Map<String,Object> rs = new HashMap<>();
		if(username==null||password.equals("")||username==null||password.equals("")||user_id==0) {
			rs.put("data",null);
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByid(user_id)==null) {
				rs.put("data",null);
				rs.put("message", "用户不存在");
				rs.put("error_code",2);
			}else 
				if(service.update(user)!=0) {
					rs.put("data",user);
					rs.put("message", "修改成功");
					rs.put("error_code",0);
				}else {
					rs.put("data",null);
					rs.put("message", "修改失败");
					rs.put("error_code",3);
				}
		}
        return rs;
	}
	
	
}
