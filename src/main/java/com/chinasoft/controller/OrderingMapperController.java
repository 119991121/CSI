package com.chinasoft.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasoft.pojo.Ordering;
import com.chinasoft.service.impl.OrderingMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/ordering")
public class OrderingMapperController {

	@Autowired
	OrderingMapperServiceImpl service;
	
	List<Integer> timeList = Arrays.asList(0,1,2,3,4,5,6,7);
	
	@RequestMapping(value = "/addOrdering", method = RequestMethod.POST)
	@ResponseBody
	// String user_name,int time,List<String> dishName
	public Object insertOrdering(@RequestBody Map<String, Object> request) {
		Map<String, Object> results = new HashMap<>();

		String user_name = (String) request.get("user_name");
		int time = (int) request.get("time");
		if(!timeList.contains(time)) {
			results.put("errod_code", 3);
			results.put("message", "输入时间段不在已知范围内");
			return results;
		}
		int now_time_num = service.nowTimeNum(time);
		System.out.println(now_time_num);
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
	// int orderingID
	public Object deleteOrdering(@RequestBody Map<String, Object> request) {
		Map<String, Object> results = new HashMap<>();

		int orderingID = (int) request.get("orderingID");

		List<Ordering> lists = service.selectByID(orderingID);
		if (lists.isEmpty()) {
			results.put("errod_code", 1);
			results.put("message", "没有找到该订单，可能已经删除。");
			return results;
		}

		if (service.delete(orderingID) == 1) {
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
	//String username
	public Object selectByUserName(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		
		String username = (String)request.get("username");
		List<Ordering> orderings = service.selectByUserName(username);
		results.put("errod_code", 0);
		results.put("message", "搜索成功");
		results.put("data", orderings);
		
		return results;
		
	}

}
