package com.chinasoft.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.aip.face.AipFace;
import com.chinasoft.AiFace.AiFaceObject;
import com.chinasoft.AiFace.FaceDelete;
import com.chinasoft.AiFace.FaceDetect;
import com.chinasoft.AiFace.FaceRegister;
import com.chinasoft.AiFace.FaceSearch;
import com.chinasoft.AiFace.FaceUpdate;
import com.chinasoft.pojo.User;
import com.chinasoft.service.impl.DepartmentMapperServiceImpl;
import com.chinasoft.service.impl.PositionMapperServiceImpl;
import com.chinasoft.service.impl.UserMapperServiceImpl;


@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserFaceController {

	static AipFace client = AiFaceObject.getClient();
	
	@Autowired
	UserMapperServiceImpl service;
	
	@RequestMapping(value = "/addFace", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addFace(@RequestBody Map<String, Object> map) throws IOException {
		
		String image = (String )map.get("file");
		String username = (String) map.get("username");
		
		Map<String,Object> result = new HashMap<>();
		
		//判断文件是否接收
        if(image!=null || !(image.equals(""))) {
        	//InputStream in = file.getInputStream();
    		//byte[] data = new byte[in.available()];
    		//in.read(data);
            //in.close();
            //String image = Base64.getEncoder().encodeToString(data);//base64编码
            
          //判断图片是否有可识别人脸
            JSONObject detect = FaceDetect.detect(client, image);
            if(detect.get("error_code").toString().equals(String.valueOf(0))) {
            	
				//人脸检索
            	JSONObject search = FaceSearch.Facecomparison(client, image);
            	if(search.get("error_code").toString().equals(String.valueOf(0))) {
            		result.put("error_code", 3);
            		result.put("messege", "人脸已存在");
            		
            		System.out.println(result);
            		return result;
            	}else {
					//注册人脸
            		String id = String.valueOf(service.selectByName(username).getUser_id());
            		JSONObject add = FaceRegister.adduser(client, image, id);
                	if(add.get("error_code").toString().equals(String.valueOf(0))) {
                		result.put("error_code", 0);
                		result.put("messege", "注册成功");
                		JSONObject json = (JSONObject) add.get("result");
                    	String faceid = (String) json.get("face_token");
                    	System.out.println(faceid);
                    	service.registerface(id, faceid);
                		return result;
                	}else {
                		result.put("error_code", 4);
                		result.put("messege", "注册失败");
                		System.out.println(result);
                		return result;
                	}
            	}
            }else {
            	result.put("error_code", 2);
        		result.put("messege", "未检测到人脸");
        		System.out.println(result);
        		return result;
            }
        }else {
        	result.put("error_code", 1);
    		result.put("messege", "文件读取失败");
    		System.out.println(result);
    		return result;
        }
	}
	
	@RequestMapping(value = "/facelogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> Login(@RequestBody Map<String, Object> map) throws IOException {

		String image = (String )map.get("file");
		
		Map<String,Object> result = new HashMap<>();

        
		//判断文件是否接收
        if(image!=null || !(image.equals(""))) {
        	//InputStream in = file.getInputStream();
    		//byte[] data = new byte[in.available()];
    		//in.read(data);
            //in.close();
            //String image = Base64.getEncoder().encodeToString(data);//base64编码
			JSONObject detect = FaceDetect.detect(client, image);

			//判断图片是否有可识别人脸
            if(detect.get("error_code").toString().equals(String.valueOf(0))) {
            	
				//人脸搜索
            	JSONObject search = FaceSearch.Facecomparison(client, image);
            	if(search.get("error_code").toString().equals(String.valueOf(222207))) {
            		result.put("error_code", 3);
            		result.put("messege", "人脸不存在");
            		return result;
            	}else {
            		result.put("error_code", 0);
                	result.put("messege", "登录成功");
                	JSONObject json1 = (JSONObject) search.get("result");
                	JSONArray userlist = (JSONArray) json1.get("user_list");
                	JSONObject json2 = (JSONObject) userlist.get(0);
                	int user_id = Integer.valueOf((String) json2.get("user_id"));
                	System.out.println(user_id);
                	User user = service.selectByid(user_id);
                	JSONObject data = new JSONObject();
                	data.append("userid", user.getFace_id());
                	data.append("username", user.getUsername());
                	data.append("password", user.getPassword());
                	data.append("name", user.getName());
                	data.append("phone", user.getPhone());
                	data.append("position", user.getPositionName());
                	data.append("department", user.getDepartmentName());
                	data.append("groupid", user.getGroupId());
                	data.append("faceid", user.getFace_id());
                	result.put("data",data);
                	return result;
            	}
            }else {
            	result.put("error_code", 2);
        		result.put("messege", "未检测到人脸");
        		System.out.println(result);
        		return result;
            }
        }else {
        	result.put("error_code", 1);
    		result.put("messege", "文件处理失败");
    		System.out.println(result);
    		return result;
        }
	}
	
	@RequestMapping(value = "/updateFace", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateuser(@RequestBody Map<String, Object> map) throws IOException {
		String image = (String )map.get("file");
		String username = (String) map.get("username");
		
		Map<String,Object> result = new HashMap<>();
		
		//判断文件是否接收
        if(image!=null || !(image.equals(""))) {
        	//InputStream in = file.getInputStream();
    		//byte[] data = new byte[in.available()];
    		//in.read(data);
            //in.close();
            //String image = Base64.getEncoder().encodeToString(data);//base64编码
            
			//判断图片是否有可识别人脸
            JSONObject detect = FaceDetect.detect(client, image);
            if(detect.get("error_code").toString().equals(String.valueOf(0))) {
            	String userid = String.valueOf(service.selectByName(username).getUser_id());
				JSONObject update = FaceUpdate.updateuser(client, image, userid);
            	if(update.get("error_code").toString().equals(String.valueOf(0))) {
                	result.put("error_code", 0);
                	result.put("messege", "更新成功");
                	System.out.println(result);
                	return result;
                }else {
                	result.put("error_code", 3);
                	result.put("messege", "更新失败");
                	JSONObject json = (JSONObject) update.get("result");
                	String faceid = (String) json.get("face_token");
                	System.out.println(faceid);
                	service.registerface(userid, faceid);
                	System.out.println(result);
                	return result;
                }
            }else {
            	result.put("error_code", 2);
        		result.put("messege", "未检测到人脸");
        		System.out.println(result);
        		return result;
            }
        }else {
        	result.put("error_code", 1);
    		result.put("messege", "文件处理失败");
    		System.out.println(result);
    		return result;
        }
	}
	@ResponseBody
	public static Map<String, Object> deleteuser(String userid) throws IOException {
		//查询faceid
 		JSONObject res = FaceDelete.deleteuser(client, userid);
 		Map<String,Object> result = new HashMap<>();
 		if(res.get("error_code").toString().equals(String.valueOf(0))) {
 			result.put("error_code", 0);
    		result.put("messege", "删除成功");
 		}else {
 			result.put("error_code", 1);
    		result.put("messege", "删除失败");
 		}
		return result;
	}
}
