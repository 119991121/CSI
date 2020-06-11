package com.chinasoft.AiFace;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class FaceSearch {
	
	public FaceSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static  JSONObject Facecomparison(AipFace client, String image){
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("max_face_num", "1");
	    options.put("match_threshold", "80");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("max_user_num", "1");
	    
	    String imageType = "BASE64";
	    String groupIdList = "01";
	    
	    //查找 1：n
	    JSONObject res = client.search(image, imageType, groupIdList, options);
	    System.out.println(res);
	    return res;
	}

}

