package com.chinasoft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.chinasoft.pojo.Menu;
import com.chinasoft.service.impl.OrderingMapperServiceImpl;

@Controller
@CrossOrigin
@RequestMapping("/menu")
public class OrderingMapperController {

	@Autowired
	OrderingMapperServiceImpl service;
	
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
