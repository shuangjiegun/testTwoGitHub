package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_trade_confirm_qry extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/trade_confirm_qry";

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();

        request.put("trust_way", "2");
        request.put("request_num", "50");
        request.put("reqry_recordsum_flag", "1");
        request.put("qry_beginrownum", "1");
        request.put("trade_acco","666Z00000074"); 
        request.put("capital_mode","M");  
       /* request.put("start_date", "20151029");
        request.put("end_date", "20151029");*/
        //request.put("fund_busin_code", "138");
        
        
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
