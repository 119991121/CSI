package com.chinasoft.AiFace;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class FaceRegister {

	public FaceRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static  JSONObject adduser(AipFace client,String image, String id) {
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("user_info", "user's info");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("action_type", "REPLACE");
	    
	    String imageType = "BASE64";
	    String groupId = "01";
	    String userId = id;
	    
	    //新增
	    JSONObject res = client.addUser(image, imageType, groupId, userId, options);
	    System.out.println(res);
	    return res;

	}
}
