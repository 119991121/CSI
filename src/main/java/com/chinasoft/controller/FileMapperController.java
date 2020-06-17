package com.chinasoft.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinasoft.pojo.File;
import com.chinasoft.service.impl.FileMapperServiceImpl;
import com.chinasoft.service.impl.UserMapperServiceImpl;
import com.chinasoft.util.COS.MyCOSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileMapperController {

	@Autowired
	FileMapperServiceImpl service;
	
	@Autowired
	UserMapperServiceImpl userservice;

	public MyCOSClient client = new MyCOSClient();

	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	@ResponseBody
	public Object Insert(@RequestParam("fileUpload") MultipartFile fileUpload, String name, String des, Integer userID) throws IOException {

Map<String, Object> results = new HashMap<>();
		
		/*
		 * 将MultiperFile类型转化为java.io.File类型准备上传 method1:不需要本地文件进行暂存，这里无法使用
		 * CommonsMultipartFile common = (CommonsMultipartFile)fileUpload;
		 * DiskFileItem dis = (DiskFileItem)common.getFileItem();
		 * java.io.File filelocal = dis.getStoreLocation();
		 * method2:需要一个本地文件进行暂存，path:/WEB-INF/file/filetemp
		 */
		if(fileUpload.isEmpty()) {
			results.put("message", "传入文件为空");
			results.put("error_code", 3);
			return results;
		}
		String originalFilename = fileUpload.getOriginalFilename();
		String[] splitKey = originalFilename.split("\\.");
		String type = splitKey[splitKey.length-1];
		String objectKey = name+"."+type;

		java.io.File filetemp = new java.io.File("/WEB-INF/file/filetemp");
		FileUtils.copyInputStreamToFile(fileUpload.getInputStream(), filetemp);

		
		// 将文件上传到腾讯云
		client.uploadFile(filetemp, objectKey);

		// 初始化File
		File file = new File();
		//String name = (String)request.get("name");
		List<File> files = service.selectByName(name);
		if(!files.isEmpty()) {
			results.put("message", "已存在名为该name的文件，请重新命名");
			results.put("error_code", 2);

			return results;
		}
		file.setName(name);
		Date date = new Date();
		// 输出时注意格式
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		/*
		file.setUserID((int)request.get("userID"));
		file.setDate(date);
		file.setDes((String)request.get("des"));
		file.setHerf(fileUpload.getOriginalFilename());
		*/
		file.setUserID(userID);
		file.setDate(date);
		file.setDes(des);
		file.setHerf(objectKey);

		// 将File插入到数据库
		if(service.insert(file)==1) {
			results.put("message", "上传成功");
			results.put("error_code", 0);
			return results;
		}else {
			results.put("message", "上传失败");
			results.put("error_code", 1);
			return results;
		}
		
	}


	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	//filename
	public Object Delete(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		
		List<String> fileName = (List)request.get("filename");
		StringBuffer msg = new StringBuffer("以下文件不存在：");
		int flag=0;
		for(int i=0;i<fileName.size();i++) {
			if(service.selectByName(fileName.get(i)).size()==0) {
				msg.insert(msg.length(), fileName.get(i)+"  ");
				flag=1;
			}
		}
		if(flag==1) {
			results.put("message", msg);
			results.put("error_code", 2);
	
			return results;
		}
		
		if(service.delete(fileName)!=0) {
			results.put("message", "删除成功");
			results.put("error_code", 0);
	
			return results;
		}else {
			results.put("message", "删除失败");
			results.put("error_code", 1);
	
			return results;
		}

	}	
	
	/*
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	@ResponseBody
	// String name,String new_doc_name, String new_doc_des, MultipartFile fileUpload
	public Object Update(@RequestBody Map<String, Object> request, MultipartFile fileUpload) throws IOException {

		Map<String, Object> results = new HashMap<>();
		
		String name = (String)request.get("name");
		List<File> fileBefore = service.selectByName(name);
		
		if(fileBefore.isEmpty()) {
			results.put("message", "没有在数据库中找到要修改的文件");
			results.put("error_code", 1);
			
			return results;
		}
		
		File old_file = fileBefore.get(0);
		
		String new_doc_name = (String)request.get("new_doc_name");
		String new_doc_des = (String)request.get("new_doc_des");
		
		File file = new File();
		file.setFileID(old_file.getFileID());
		if (new_doc_name!=null&&!new_doc_name.equals("")) {
			file.setName(new_doc_name);
		}
		file.setDate(new Date());
		if (new_doc_des!=null&&!new_doc_des.equals("")) {
			file.setDes(new_doc_des);
		}
		
		List<File> files = service.selectByName(new_doc_name);
		if(!files.isEmpty()&&files.get(0).equals(name)) {
			results.put("message", "文件名重复");
			results.put("error_code", 3);
			return results;
		}
		
		if (!fileUpload.isEmpty()) {
			// 将MultipartFile转化为File
			java.io.File filetemp = new java.io.File("/WEB-INF/file/filetemp");
			FileUtils.copyInputStreamToFile(fileUpload.getInputStream(), filetemp);
			
			// 删除之前存储的文件
			client.deleteFile(fileBefore.get(0).getHerf());
			
			// 将文件传到腾讯云
			PutObjectResult success = client.uploadFile(filetemp, fileUpload.getOriginalFilename());
			
			if(success==null) {
				results.put("message", "文件上传失败");
				results.put("errod_code", 2);
				return results;
			}
			
			file.setHerf(fileUpload.getOriginalFilename());
		}
		
		if(service.update(file)==1) {
			results.put("message", "修改成功");
			results.put("error_code", 0);

			return results;
		}else {
			results.put("message", "修改失败");
			results.put("error_code", 4);

			return results;
		}

		
	}
	*/
	
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	@ResponseBody
	// String name,String new_doc_name, String new_doc_des, MultipartFile fileUpload
	public Object Update(@RequestBody Map<String, Object> request) throws IOException {

		Map<String, Object> results = new HashMap<>();
		
		String name = (String)request.get("name");
		List<File> fileBefore = service.selectByName(name);
		
		if(fileBefore.isEmpty()) {
			results.put("message", "没有在数据库中找到要修改的文件");
			results.put("error_code", 1);
			
			return results;
		}
		
		File old_file = fileBefore.get(0);
		
		String new_doc_name = (String)request.get("new_doc_name");
		String new_doc_des = (String)request.get("new_doc_des");
		int userid = Integer.valueOf((String) request.get("userID"));
		
		File file = new File();
		file.setFileID(old_file.getFileID());
		if (new_doc_name!=null&&!new_doc_name.equals("")) {
			file.setName(new_doc_name);
		}
		file.setDate(new Date());
		if (new_doc_des!=null&&!new_doc_des.equals("")) {
			file.setDes(new_doc_des);
		}
		file.setUserID(userid);
		
		List<File> files = service.selectByName(new_doc_name);
		if(!files.isEmpty()||files.get(0).getName().equals(name)) {
			results.put("message", "文件名重复");
			results.put("error_code", 3);
			return results;
		}
		
		if(service.update(file)==1) {
			results.put("message", "修改成功");
			results.put("error_code", 0);

			return results;
		}else {
			results.put("message", "修改失败");
			results.put("error_code", 4);

			return results;
		}

		
	}

	
	@RequestMapping(value = "/selectFile", method = RequestMethod.POST)
	@ResponseBody
	public Object selectFile(@RequestBody Map<String, Object> request) throws ParseException {
	
		Map<String, Object> results = new HashMap<>();
		//初始化file
		String select_key = (String)request.get("select_key");
		
		if((select_key==null||select_key.equals(""))) {
			results.put("message", "传入参数为空");
			results.put("errod_code", "1");
			return results;
		}
		else {
			List<File> files = service.select(select_key);
			if(files!=null&&files.size()!=0) {
				results.put("message", "查询成功");
				results.put("error_code", 0);
				results.put("data", files);
			}
			else {
				results.put("message", "查询失败");
				results.put("error_code", 2);
				results.put("data", files);
			}
		}

		return results; 
	}
	
	@RequestMapping(value = "/selectFileAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		
		Map<String, Object> results = new HashMap<>();
		
		List<File> files = service.selectFileAll();
		
		
		for (File file : files) {
			String user_name = userservice.selectNameById(file.getUserID());
			file.setUser_name(user_name);
		}
		
		results.put("message", "查询成功");
		results.put("error_code", 0);
		results.put("data", files);
		return results;
		
	}
	
	@RequestMapping(value = "/downLoad", method = RequestMethod.POST)
	@ResponseBody
	//String localpath     String herf
	public Object downLoad(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		
		//本地文件夹路径
		String localpath = (String)request.get("localpath");
		String herf = (String)request.get("herf");
		
		if(localpath==null||localpath.equals("")||herf==null||herf.equals("")) {
			results.put("message", "输入参数localpath/herf为空");
			results.put("error_code", "1");
			return results;
		}
		
		java.io.File localfile = new java.io.File(localpath);
		if(!localfile.exists()&&!localfile.isDirectory()) {
			results.put("message", "输入的localpath路径无法在本地找到/localpath不是一个文件夹路径");
			results.put("error_code", "2");
			return results;
		}
		
		ObjectMetadata success = client.downloadFile(herf, localpath);
		if(success==null) {
			results.put("message", "下载失败");
			results.put("error_code", "3");
			return results;
		}
		
		results.put("message", "下载成功");
		results.put("error_code", "0");
		return results;
	}
	
}