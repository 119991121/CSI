package com.chinasoft.AiFace;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class FaceDetect {
	
	public FaceDetect() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static  JSONObject detect(AipFace client, String image) {
	    // 人脸检测
	    HashMap<String, String> options = new HashMap<String, String>();
	    options.put("face_field", "age");
	    options.put("max_face_num", "1");
	    options.put("face_type", "LIVE");	
	    options.put("liveness_control", "LOW");
	    
	    String imageType = "BASE64";

	    JSONObject res = client.detect(image, imageType, options);
	    System.out.println(res.toString(2));
	    return res;
	}
}
