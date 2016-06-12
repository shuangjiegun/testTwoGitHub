package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_next_day_qry extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/next_day_qry";

	@Before
	public void setUp() throws Exception {

        request = new LinkedHashMap<String, Object>();
        // 公共字段

        request.put("trust_way", "2");
        request.put("request_num", "50");
        request.put("reqry_recordsum_flag", "0");
        request.put("qry_beginrownum", "1");
       // request.put("client_id", "10078");
        //request.put("trade_acco", "666Z00001065");
        //request.put("fund_code", "320107");
        //request.put("id_kind_gb", "0");
       // request.put("id_no", "343403198709231085");

		
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        List<Map<String, Object>> response = getUfxSDK().query(path, request);
        for(Map<String, Object> info : response){
        	System.out.println(info);
        }
        
	}
}
