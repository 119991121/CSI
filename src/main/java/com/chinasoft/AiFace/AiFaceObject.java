package com.chinasoft.AiFace;

import com.baidu.aip.face.AipFace;

public class AiFaceObject {
	    public static final String APP_ID = "20305711";
	    public static final String API_KEY = "0bjFiSeye5HI7gaQEE828zCZ";
	    public static final String SECRET_KEY = "8fCBSwGRdRTu4Dy2WOaoGdhPTmrOxOjp";
	    
	    //单例创建
	    private static AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
	    
	    public static AipFace getClient(){
	    	client.setConnectionTimeoutInMillis(2000);
	    	client.setSocketTimeoutInMillis(60000);
	    	return client;
	    }
	    
}

