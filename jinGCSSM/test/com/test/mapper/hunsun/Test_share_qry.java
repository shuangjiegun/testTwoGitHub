package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_share_qry extends UfxSDKCommon{
	
    private Map<String, Object> request;

    //private String url = "/share_qry";
    private String url = "/sharedetail_qry";
	@Before
	public void setUp() throws Exception {
		 request = new LinkedHashMap<String, Object>();
        request.put("request_num", "50");
        request.put("reqry_recordsum_flag", "0");
        request.put("qry_beginrownum", "1");
        //request.put("fund_code", "320107");
        request.put("client_id", "498");
	}

	@Test
	public void test() {
        String path = "/cwsale/v1" + url;
        List<Map<String, Object>> response = getUfxSDK().query(path, request);
        for(Map<String, Object> info : response){
        	System.out.println(info);
        }
	}

}
