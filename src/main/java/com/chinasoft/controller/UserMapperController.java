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
		int groupId=(int)request.get("groupid");
		Map<String,Object> rs = new HashMap<>();	
		if(username ==null || password ==null||username.equals("") || password.equals("")||groupId==0) {
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
		List<String> username=(List) request.get("username");
		Map<String,Object> rs = new HashMap<>();
		if(service.delete(username)!=0) {
			for(String username1 : username) {
				try {
					UserFaceController.deleteuser(username1);
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
		String positionName=(String) request.get("positionName");
		String departmentName=(String) request.get("departmentName");
		user.setName(name);
		user.setPositionName(positionName);
		user.setDepartmentName(departmentName);
		List<User> users =service.select(user);
		Map<String,Object> rs = new HashMap<>();	
		if(users!=null && users.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", users);
			System.out.println(users);
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
		}
        return rs;
	}
	
	@RequestMapping(value="/detail",method= RequestMethod.POST)
	@ResponseBody
	public Object selectDetail(@RequestBody Map<String,Object> request) {

		String username=(String) request.get("username");
		User user =service.selectDetail(username);
		Map<String,Object> rs = new HashMap<>();	
		if(user!=null) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", user);
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
		}
        return rs;
	}
	
	@RequestMapping(value="/showall",method= RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		Map<String,Object> rs = new HashMap<>();
		List<User> users= service.selectAll();
		if(users!=null&&users.size()!=0) {
			rs.put("error_code", 0);
			rs.put("message", "查询成功");
			rs.put("data", users);
			return rs;
		}else {
			rs.put("error_code", 1);
			rs.put("message", "查询失败");
			return rs;
		}
	}
	
	@RequestMapping(value="/update",method= RequestMethod.POST)
	@ResponseBody
	public Object Update(@RequestBody Map<String,Object> request) {	
		String S_groupId=(String) request.get("groupid");
		System.out.println(S_groupId);
		if(S_groupId==null||S_groupId.equals("")) {
			S_groupId="0";
		}
		int groupId = Integer.parseInt(S_groupId);
		
		String positionName=(String) request.get("positionName");
		String departmentName=(String) request.get("departmentName");
		
		System.out.println(departmentName);
		
		int position_id=0;
		if(positionName!=null&&!positionName.equals("")) {
			position_id=service.getPosition_id(positionName,departmentName);
		}
		int department_id=0;
		if(positionName!=null&&!positionName.equals("")) {
			department_id=service.getDepartment_id(departmentName);	
		}
		String username=(String) request.get("username");
		String password=(String) request.get("password");
		String phone=(String) request.get("phone");
		String name=(String) request.get("name");
		
		String S_sex=(String) request.get("sex");
		if(S_sex==null||S_sex.equals("")) {
			S_sex="0";
		}
		int sex= Integer.parseInt(S_sex);
		
		String email=(String) request.get("email");
		String education=(String) request.get("education");
		String idCardNo=(String) request.get("idCardNo");
		String address=(String) request.get("address");
		Date createdDate=new Date();
		
		User user = new User();
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
		System.out.println(user);
		Map<String,Object> rs = new HashMap<>();
		if(username==null||password.equals("")||username==null||password.equals("")) {
			rs.put("data",null);
			rs.put("message", "数据不完整");
			rs.put("error_code",1);
		}else {
			if(service.selectByName(username)==null) {
				rs.put("data",null);
				rs.put("message", "用户不存在");
				rs.put("error_code",2);
			}else 
				if(service.update(user)!=0) {
					user = service.selectByName(username);
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
	
	@RequestMapping(value="/login",method= RequestMethod.POST)
	@ResponseBody
	public Object Login(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		String username=(String) request.get("username");
		String password=(String) request.get("password");
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		User user1=service.login(user);
		if(user1!=null) {
			rs.put("error_code", 0);
			rs.put("message", "请求成功");
			rs.put("data", user1);
			System.out.println(user1);
		}else {
			user1=service.selectByName(username);
			if(user1!=null) {
				rs.put("error_code", 201);
				rs.put("message", "密码错误");
			}
			else{
				rs.put("error_code", 202);
				rs.put("message", "未注册");
			}
		}
		return rs;
	}
	
	@RequestMapping(value="/editPassword",method= RequestMethod.POST)
	@ResponseBody
	public Object EditPassword(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		System.out.println(request);
		String newPassword=(String) request.get("newPassword");
		String oldPassword=(String) request.get("oldPassword");
		String username=(String) request.get("username");
		User user = new User();
		user.setUsername(username);
		user.setPassword(oldPassword);
		if(service.login(user)==null) {
			rs.put("error_code", 203);
			rs.put("message", "旧的密码输入错误");
		}else {
			if(service.editPassword(newPassword,username)==1) {
				rs.put("error_code", 0);
				rs.put("message", "请求成功");
			}
			else{
				rs.put("error_code", 1);
				rs.put("message", "请求失败");
			}		
		}
		return rs;
	}
}
