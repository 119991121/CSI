package com.chinasoft.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItem;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.chinasoft.pojo.File;
import com.chinasoft.service.impl.FileMapperServiceImpl;
import com.chinasoft.util.COSClientUtil;
import com.chinasoft.util.COS.MyCOSClient;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileMapperController {

	@Autowired
	FileMapperServiceImpl service;

	public MyCOSClient client = new MyCOSClient();

	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	@ResponseBody
	//String name, String des, int userId, MultipartFile fileUpload
	public Object Insert(@RequestBody Map<String, Object> request, MultipartFile fileUpload) throws IOException {

		Map<String, Object> reults = new HashMap<>();
		
		// 将MultiperFile类型转化为java.io.File类型准备上传 method1:不需要本地文件进行暂存，这里无法使用
//		CommonsMultipartFile common = (CommonsMultipartFile)fileUpload;
//		DiskFileItem dis = (DiskFileItem)common.getFileItem();
//		java.io.File filelocal = dis.getStoreLocation();
		// method2:需要一个本地文件进行暂存，path:/WEB-INF/file/filetemp
		if(fileUpload.isEmpty()) {
			reults.put("message", "传入文件为空");
			reults.put("error_code", 3);
			return reults;
		}
		java.io.File filetemp = new java.io.File("/WEB-INF/file/filetemp");
		FileUtils.copyInputStreamToFile(fileUpload.getInputStream(), filetemp);

		// 将文件上传到腾讯云
		client.uploadFile(filetemp, fileUpload.getOriginalFilename());
		
		

		// 初始化File
		File file = new File();
		String name = (String)request.get("name");
		List<File> files = service.selectByName(name);
		if(!files.isEmpty()) {
			reults.put("message", "文件名重复，请输入其他名字");
			reults.put("error_code", 2);

			return reults;
		}
		file.setName(name);
		Date date = new Date();
		// 输出时注意格式
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		file.setUserID((int)request.get("userID"));
		file.setDate(date);
		file.setDes((String)request.get("des"));
		file.setHerf(fileUpload.getOriginalFilename());

		// 将File插入到数据库
		if(service.insert(file)==1) {
			reults.put("message", "添加成功");
			reults.put("error_code", 0);
			return reults;
		}else {
			reults.put("message", "添加失败");
			reults.put("error_code", 1);
			return reults;
		}
		
	}


	@RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
	@ResponseBody
	//fileId
	public Object Delete(@RequestBody Map<String, Object> request) {
		
		Map<String, Object> results = new HashMap<>();
		
		int fileId = (int)request.get("fileID");
		
		List<File> file = service.selectWithID(fileId);
		if(file.isEmpty()) {
			results.put("message", "不存在该fileID的文件.");
			results.put("error_code", 2);
	
			return results;
		}
		
		if(service.delete(fileId)==1) {
			results.put("message", "删除成功");
			results.put("error_code", 0);
	
			return results;
		}else {
			results.put("message", "删除失败");
			results.put("error_code", 1);
	
			return results;
		}

	}

	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	@ResponseBody
	//int fileId, String name, String des, MultipartFile fileUpload
	public Object Update(@RequestBody Map<String, Object> request,MultipartFile fileUpload) throws IOException {

		Map<String, Object> results = new HashMap<>();
		
		int fileId = (int)request.get("fileID");
		List<File> fileBefore = service.selectWithID(fileId);
		
		if(fileBefore.isEmpty()) {
			results.put("message", "该文件在数据库中不存在");
			results.put("error_code", 1);
			
			return results;
		}
		
		String name = (String)request.get("name");
		String des = (String)request.get("des");
		
		File file = new File();
		file.setFileID(fileId);
		if (name!=null&&!name.equals("")) {
			file.setName(name);
		}
		file.setDate(new Date());
		if (des!=null&&!des.equals("")) {
			file.setDes(des);
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
			results.put("error_code", 3);

			return results;
		}

		
	}

	
	@RequestMapping(value = "/selectFileByName", method = RequestMethod.POST)
	@ResponseBody
	public Object Select(@RequestBody Map<String, Object> request) throws ParseException {
	
		Map<String, Object> results = new HashMap<>();
		//初始化file
		String name = (String)request.get("name");
		
		if(name==null||name.equals("")) {
			results.put("message", "传入参数name为空");
			request.put("errod_code", "1");
			return request;
		}
		
		List<File> files = service.selectByName(name);
		
		System.out.println(files==null);
		
		results.put("message", "查询成功");
		results.put("error_code", 0);

		return results; 
	}
	
	@RequestMapping(value = "/selectFileAll", method = RequestMethod.POST)
	@ResponseBody
	public Object selectAll() {
		
		Map<String, Object> results = new HashMap<>();
		
		List<File> files = service.selectFileAll();
		
		for (File file : files) {
			System.out.println(file);
		}
		
		results.put("message", "查询成功");
		results.put("error_code", 0);
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
			request.put("message", "下载失败");
			request.put("error_code", "3");
			return request;
		}
		
		request.put("message", "下载成功");
		request.put("error_code", "0");
		return request;
	}
	
}
