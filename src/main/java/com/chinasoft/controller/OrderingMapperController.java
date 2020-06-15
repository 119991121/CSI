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

		String user_name = (String) request.get("user_name");
		if(user_name==null||user_name.equals("")) {
			results.put("errod_code", 2);
			results.put("message", "没有输入user_name");
			return results;
		}
		service.deleteAllByName(user_name);
		int time = (int) request.get("time");
		if(!timeList.contains(time)) {
			results.put("errod_code", 3);
			results.put("message", "输入时间段不在已知范围内");
			return results;
		}
		int now_time_num = service.nowTimeNum(time);
		if (now_time_num >= 50) {
			results.put("errod_code", 1);
			results.put("message", "该时间段预约人数达到上限，请另选时间段");
			return results;
		}
		List<String> dishNames = (List<String>) request.get("dishName");
		// 这里要判断一下有没有菜...
		Ordering ordering = new Ordering();
		ordering.setUsername(user_name);
		ordering.setTime(time);
		ordering.setDate(new Date());
		for (String dishName : dishNames) {
			ordering.setDishName(dishName);
			if (service.insert(ordering) != 1) {
				results.put("errod_code", 4);
				results.put("message", "预约失败");
				return results;
			}
		}
		results.put("errod_code", 0);
		results.put("message", "预约成功");
		return results;
	}

	@RequestMapping(value = "/deleteOrdering", method = RequestMethod.POST)
	@ResponseBody
	// String user_name
	public Object deleteOrdering(@RequestBody Map<String, Object> request) {
		Map<String, Object> results = new HashMap<>();

		String user_name = (String) request.get("user_name");
		
		if(user_name==null||user_name.equals("")) {
			results.put("errod_code", 3);
			results.put("message", "输入user_name为空");
			return results;
		}

		List<Ordering> lists = service.selectByUserName(user_name);
		if (lists.isEmpty()) {
			results.put("errod_code", 1);
			results.put("message", "该用户没有下单,可能已经删除。");
			return results;
		}

		if (service.deleteAllByName(user_name) == lists.size()) {
			results.put("errod_code", 0);
			results.put("message", "取消预约成功");
			return results;
		} else {
			results.put("errod_code", 2);
			results.put("message", "取消预约失败");
			return results;
		}

	}

	@RequestMapping(value = "/updateOrdering", method = RequestMethod.POST)
	@ResponseBody
	// int orderingID, String new_dishName,int new_time.
	public Object updateOrdering(@RequestBody Map<String, Object> request) {

		Map<String, Object> results = new HashMap<>();

		int orderingID = (int) request.get("orderingID");
		List<Ordering> oldOrderings = service.selectByID(orderingID);

		if (oldOrderings.isEmpty()) {
			results.put("errod_code", 1);
			results.put("message", "该订单已经不存在，请重新选择。");
			return results;
		}

		Ordering oldOrdering = oldOrderings.get(0);
		Ordering ordering = new Ordering();
		ordering.setOrderingID(orderingID);
		ordering.setDate(new Date());
		
		int time = (int) request.get("new_time");
		if (oldOrdering.getTime() != time) {
			if(!timeList.contains(time)) {
				results.put("errod_code", 3);
				results.put("message", "输入时间段不在已知范围内");
				return results;
			}
			if (service.nowTimeNum(time) >= 50) {
				results.put("errod_code", 2);
				results.put("message", "该时间段预约人数达到上限，请另选时间段");
				return results;
			}
			ordering.setTime(time);
		}else {
			ordering.setTime(-1);
		}
		
		String dishName = (String) request.get("new_dishName");
		// 这里也需要判断一下今天有没有这个菜
		if (dishName!=null&&!dishName.equals("")&&!dishName.equals(oldOrdering.getDishName())) {
			ordering.setDishName(dishName);
		}
		
		if(service.updateOrdering(ordering)==1) {
			results.put("errod_code", 0);
			results.put("message", "修改成功");
			return results;
		}else {
			results.put("errod_code", 4);
			results.put("message", "修改失败");
			return results;
		}

	}
	
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		Map<String, Object> results = new HashMap<>();
		
		List<Ordering> orderings = service.selectAll();
		results.put("errod_code", 0);
		results.put("message", "搜索成功");
		results.put("data", orderings);
		
		return results;
	}
	
	@RequestMapping(value = "/selectByUserName", method = RequestMethod.POST)
	@ResponseBody
	//int timecode String username
	public Object selectByUserName(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		
		int timecode = (int)request.get("timecode");
		if(timecode != 0&& timecode !=1) {
			results.put("errod_code", 1);
			results.put("message", "输入的timecode有误");
		}
		String username = (String)request.get("username");
		List<Ordering> orderings = service.selectByUserName(username);
		List<Ordering> orderingsInTime = new ArrayList<>();
		for (Ordering ordering : orderings) {
			if(timecode == 0&&timeListTime0.contains(ordering.getTime())) {
				orderingsInTime.add(ordering);
			}else if(timecode == 1&&timeListTime1.contains(ordering.getTime())) {
				orderingsInTime.add(ordering);
			}
		}
		
		if(orderingsInTime.isEmpty()) {
			results.put("errod_code", 0);
			results.put("message", "该时间段内没有预约");
			return results;
		}else {
			results.put("errod_code", 0);
			results.put("message", "搜索成功");
			results.put("data", orderingsInTime);
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
	    	String dishName = lunch.get(i);
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
			if(lunch.size()!=0&&dinner.size()!=0) {
				Map<String,Object> rs1 = new HashMap<>();
				rs1.put("lunch", lunch);
				rs1.put("dinner", dinner);	
				rs.put("error_code", 0);
				rs.put("data", rs1);
			}	
			else {
				rs.put("error_code", 1);
				rs.put("message", "查询失败");
			}
		}
		else {
			rs.put("error_code", 1);
			rs.put("message", "暂无数据");
		}
		return rs;
	}
	
	@RequestMapping(value="/showOrdering",method= RequestMethod.POST)
	@ResponseBody
	public Object showOrdering() {
		Map<String,Object> rs = new HashMap<>();
		Map<String,Object> tem0 = new HashMap<>();
		Map<String,Object> tem1 = new HashMap<>();
		Map<String,Object> tem2 = new HashMap<>();
		Map<String,Object> tem3 = new HashMap<>();
		Map<String,Object> tem4 = new HashMap<>();
		Map<String,Object> tem5 = new HashMap<>();
		Map<String,Object> tem6 = new HashMap<>();
		Map<String,Object> tem7 = new HashMap<>();
		List<String> lunch = service.getLunchs();
		List<String> dinner = service.getDinners();
		List<Map<String,Object>> rs0 = new ArrayList<Map<String,Object>>(); 
		rs0.add(tem0);
		rs0.add(tem1);
		rs0.add(tem2);
		rs0.add(tem3);
		rs0.add(tem4);
		rs0.add(tem5);
		rs0.add(tem6);
		rs0.add(tem7);
		
		int sum;
		for(int i=0;i<8;i++) {
			if(i>=0&&i<4) {
				for(int j=0;j<lunch.size();j++) {
					sum=service.getSum(i+"",lunch.get(j));
					System.out.println(i+" "+lunch.get(j));
					rs0.get(i).put(lunch.get(j), sum);
				}
			}
			else {
				for(int k=0;k<dinner.size();k++) {
					sum=service.getSum(i+"",dinner.get(k));
					System.out.println(i+" "+dinner.get(k));
					rs0.get(i).put(dinner.get(k), sum);
				}
			}
			System.out.println("i:"+i);
		}
		Map<String,Object> rs1 = new HashMap<>();
		for(int f=0;f<8;f++) {
			rs1.put(f+"",rs0.get(f));
		}
		rs.put("error_code", 0);
		rs.put("data", rs1);
		return rs;
	}

}
