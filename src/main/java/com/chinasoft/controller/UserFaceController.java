package com.chinasoft.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
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


@Controller
@RequestMapping("/user")
public class UserFaceController {

	AipFace client = AiFaceObject.getClient();
	
	@RequestMapping(value = "/addFace", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> adduser(MultipartFile file, String faceid) throws IOException {
		System.out.println(file.getName());
		System.out.println(file.getContentType());
		
		Map<String,Object> result = new HashMap<>();
		
		//判断文件是否接收
        if(!file.isEmpty()) {
        	InputStream in = file.getInputStream();
    		byte[] data = new byte[in.available()];
    		in.read(data);
            in.close();
            String image = Base64.getEncoder().encodeToString(data);//base64编码
            
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
            		JSONObject add = FaceRegister.adduser(client, image, faceid);
                	if(add.get("error_code").toString().equals(String.valueOf(0))) {
                		result.put("error_code", 0);
                		result.put("messege", "注册成功");
                		System.out.println(result);
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
	public Map<String, Object> Login(MultipartFile file) throws IOException {
		System.out.println(file.getName());
		System.out.println(file.getContentType());
		
		Map<String,Object> result = new HashMap<>();

        
		//判断文件是否接收
        if(!file.isEmpty()) {
        	InputStream in = file.getInputStream();
    		byte[] data = new byte[in.available()];
    		in.read(data);
            in.close();
            String image = Base64.getEncoder().encodeToString(data);//base64编码
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
                	int faceid = Integer.valueOf((String) json2.get("user_id"));
                	System.out.println(faceid);
                	//将faceid存入数据库
                	//通过faceid检索到user，返回用户信息
                	//result.put("user",user);
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
	public Map<String, Object> updateuser(MultipartFile file, String faceid) throws IOException {
		System.out.println(file.getName());
		System.out.println(file.getContentType());
		
		Map<String,Object> result = new HashMap<>();
		
		//判断文件是否接收
        if(!file.isEmpty()) {
        	InputStream in = file.getInputStream();
    		byte[] data = new byte[in.available()];
    		in.read(data);
            in.close();
            String image = Base64.getEncoder().encodeToString(data);//base64编码
            
			//判断图片是否有可识别人脸
            JSONObject detect = FaceDetect.detect(client, image);
            if(detect.get("error_code").toString().equals(String.valueOf(0))) {
				JSONObject update = FaceUpdate.updateuser(client, image, faceid);
            	if(update.get("error_code").toString().equals(String.valueOf(0))) {
                	result.put("error_code", 0);
                	result.put("messege", "更新成功");
                	System.out.println(result);
                	return result;
                }else {
                	result.put("error_code", 3);
                	result.put("messege", "更新失败");
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
	
	@RequestMapping(value = "/deleteFace", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteuser(String faceid) throws IOException {
 		JSONObject res = FaceDelete.deleteuser(client, faceid);
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
