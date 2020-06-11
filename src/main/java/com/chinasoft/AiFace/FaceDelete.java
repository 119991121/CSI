package com.chinasoft.AiFace;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;

public class FaceDelete {

	public FaceDelete() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static JSONObject deleteuser(AipFace client, String faceid) {
	    //删除人脸库
	    HashMap<String, String> options = new HashMap<String, String>();
	    
	    String userId = faceid;
	    String groupId = "01";
	    
	    //faceToken查询
	    JSONObject get = client.faceGetlist(userId, groupId, options);
	    JSONObject json1 = (JSONObject) get.get("result");
    	JSONArray facelist = (JSONArray) json1.get("face_list");
    	JSONObject json2 = (JSONObject) facelist.get(0);
    	String faceToken = json2.getString("face_token");
	    System.out.println(faceToken);
	    
	    // 删除
	    JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
	    System.out.println(res.toString(2));
	    
	    return res;

	}
}
