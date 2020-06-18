package com.chinasoft.controller;


import java.util.ArrayList;
import java.util.Arrays;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.chinasoft.pojo.Ordering;
import com.chinasoft.pojo.Menu;
import com.chinasoft.service.impl.OrderingMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/menu")
public class OrderingMapperController {

	@Autowired
	OrderingMapperServiceImpl service;
	
	//所有时间段
	List<Integer> timeList = Arrays.asList(0,1,2,3,4,5,6,7);
	//第一个时间段
	List<Integer> timeListTime0 = Arrays.asList(0,1,2,3);
	//第二个时间段
	List<Integer> timeListTime1 = Arrays.asList(4,5,6,7);
	
	
	@RequestMapping(value = "/addOrdering", method = RequestMethod.POST)
	@ResponseBody
	// String user_name,int time,List<String> dishName
	public Object insertOrdering(@RequestBody Map<String, Object> request) {
		Map<String, Object> results = new HashMap<>();
		//获取前端传递参数user_name并检验合法性
		String user_name = (String) request.get("user_name");
		if(user_name==null||user_name.equals("")) {
			results.put("error_code", 2);
			results.put("message", "没有输入user_name");
			return results;
		}
		//在新增订单之前，先将该用户的订单全部删除
		service.deleteAllByName(user_name);
		//获取前端传递参数time并检验合法性
		int time = Integer.valueOf((String) request.get("time"));
		if(!timeList.contains(time)) {
			results.put("error_code", 3);
			results.put("message", "输入时间段不在已知范围内");
			return results;
		}
		//获取当前时间段内预约就餐的人数
		int now_time_num = service.nowTimeNum(time);
		if (now_time_num >= 50) {
			results.put("error_code", 1);
			results.put("message", "该时间段预约人数达到上限，请另选时间段");
			return results;
		}
		//获取前端传递参数dishName并将每条数据插入到订单
		List<String> dishNames = (List<String>) request.get("dishName");
		Ordering ordering = new Ordering();
		ordering.setUsername(user_name);
		ordering.setTime(time);
		ordering.setDate(new Date());
		for (String dishName : dishNames) {
			ordering.setDishName(dishName);
			if (service.insert(ordering) != 1) {
				results.put("error_code", 4);
				results.put("message", "预约失败");
				return results;
			}
		}
		results.put("error_code", 0);
		results.put("message", "预约成功");
		return results;
	}

	@RequestMapping(value = "/deleteOrdering", method = RequestMethod.POST)
	@ResponseBody
	// String user_name
	public Object deleteOrdering(@RequestBody Map<String, Object> request) {
		Map<String, Object> results = new HashMap<>();
		//获取前端传递参数user_name并检验合法性
		String user_name = (String) request.get("user_name");
		if(user_name==null||user_name.equals("")) {
			results.put("error_code", 3);
			results.put("message", "输入user_name为空");
			return results;
		}
		//判断该用户是否有订单
		List<Ordering> lists = service.selectByUserName(user_name);
		if (lists.isEmpty()) {
			results.put("error_code", 1);
			results.put("message", "该用户没有下单,可能已经删除。");
			return results;
		}

		if (service.deleteAllByName(user_name) == lists.size()) {
			results.put("error_code", 0);
			results.put("message", "取消预约成功");
			return results;
		} else {
			results.put("error_code", 2);
			results.put("message", "取消预约失败");
			return results;
		}

	}

	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		//获取全部的订单预约信息
		Map<String, Object> results = new HashMap<>();
		
		List<Ordering> orderings = service.selectAll();
		results.put("error_code", 0);
		results.put("message", "搜索成功");
		results.put("data", orderings);
		
		return results;
	}
	
	@RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
	@ResponseBody
	//int timecode String username
	public Object selectByUserName(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		//获取前端传递参数timecode并检验合法性
		int timecode = (int)request.get("timecode");
		if(timecode != 0&& timecode !=1) {
			results.put("error_code", 1);
			results.put("message", "输入的timecode有误");
		}
		//获取前端传递参数username并检验合法性
		String username = (String)request.get("username");
		if(username == null||username.equals("")) {
			results.put("error_code", 3);
			results.put("message", "输入username为空");
			return results;
		}
		//获取用户全部订单
		List<Ordering> orderings = service.selectByUserName(username);
		List<Ordering> orderingsInTime = new ArrayList<>();
		//按照timecode进行分类
		for (Ordering ordering : orderings) {
			if(timecode == 0&&timeListTime0.contains(ordering.getTime())) {
				orderingsInTime.add(ordering);
			}else if(timecode == 1&&timeListTime1.contains(ordering.getTime())) {
				orderingsInTime.add(ordering);
			}
		}
		
		if(orderingsInTime.isEmpty()) {
			results.put("error_code", 2);
			results.put("message", "该时间段内没有预约");
			return results;
		}else {
			//将获取到的数据进行包装并传递
			Map<String, Object> data = new HashMap<>();
			Ordering orderingTemp = orderingsInTime.get(0);
			data.put("username", orderingTemp.getUsername());
			data.put("date", orderingTemp.getDate());
			data.put("time", orderingTemp.getTime());
			List<String> dishName = new ArrayList<String>();
			for (Ordering ordering : orderingsInTime) {
				dishName.add(ordering.getDishName());
			}
			data.put("dishName", dishName);
			results.put("error_code", 0);
			results.put("message", "搜索成功");
			results.put("data", data);
			return results;
		}
		
	}

	@RequestMapping(value="/addMenu",method= RequestMethod.POST)
	@ResponseBody
	public Object addMenu(@RequestBody Map<String,Object> request) {
		Map<String,Object> rs = new HashMap<>();
		List<String> lunch = (List) request.get("lunch");
		List<String> dinner = (List) request.get("dinner");
		service.deleteAll();
		Menu menu = new Menu();
	    for (int i = 0; i < lunch.size(); i++) {
	    	String dishName = lunch.get(i);
	    	if (dishName!=null) {
	    		menu.setDishName(dishName);
	    		menu.setTime(0);
	    		menu.setDate(new Date());
	    		service.addMenu(menu);
	    	}
	    }
	    for (int i = 0; i < dinner.size(); i++) {
	    	String dishName = dinner.get(i);
	    	if (dishName!=null) {
	    		menu.setDishName(dishName);
	    		menu.setTime(1);
	    		menu.setDate(new Date());
	    		service.addMenu(menu);
	    	}
	    }
		rs.put("error_code", 0);
		rs.put("message", "添加菜单成功");			
		return rs;
	}
	
	@RequestMapping(value="/showAll",method= RequestMethod.POST)
	@ResponseBody
	public Object showAll() {
		Map<String,Object> rs = new HashMap<>();
		Date date1 = new Date();
		DateFormat date2 = new SimpleDateFormat("yyyy/MM/dd");
		String date = date2.format(date1);
		int sum=service.selectByTime(date);
		if(sum!=0) {
			List<String> lunch = service.getLunchs();
			List<String> dinner = service.getDinners();
			if(lunch.size()!=0||dinner.size()!=0) {
				Map<String,Object> rs1 = new HashMap<>();
				rs1.put("lunch", lunch);
				rs1.put("dinner", dinner);	
				rs.put("error_code", 0);
				rs.put("data", rs1);
			}	
			else {
				rs.put("error_code", 2);
				rs.put("message", "查询失败");
			}
		}
		else {
			rs.put("error_code", 1);
			rs.put("message", "暂无数据");
		}
		return rs;
	}
	
	@RequestMapping(value = "/showOrdering", method = RequestMethod.POST)
	@ResponseBody
	public Object showOrdering() {
		Map<String, Object> rs = new HashMap<>();
		List<String> lunch = service.getLunchs();
		List<String> dinner = service.getDinners();
		int sum;
		Map<String, Object> rs1 = new HashMap<>();
		for (int i = 0; i < 8; i++) {
			List<Map> tem = new ArrayList<>();
			if (i >= 0 && i < 4) {
				for (int j = 0; j < lunch.size(); j++) {
					Map<String, Object> rs0 = new HashMap<>();
					sum = service.getSum(i + "", lunch.get(j));
//					System.out.println(i + " " + lunch.get(j));
					rs0.put("name", lunch.get(j));
					rs0.put("num", sum);
					tem.add(rs0);
				}
			}else {
				for (int j = 0; j < dinner.size(); j++) {
					Map<String, Object> rs0 = new HashMap<>();
					sum = service.getSum(i + "", dinner.get(j));
//					System.out.println(i + " " + dinner.get(j));
					rs0.put("name", dinner.get(j));
					rs0.put("num", sum);
					tem.add(rs0);
				}
			}
			String time = "time" + i;
			rs1.put(time, tem);
		}
		rs.put("error_code", 0);
		rs.put("data", rs1);
		return rs;
	}

}
