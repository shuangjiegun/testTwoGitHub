package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_fundaccount_add_trade extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/fundaccount_add_trade";

	@Before
	public void setUp() throws Exception {
		 request = new LinkedHashMap<String, Object>();
	        
			 request.put("trust_way", "2");
			 request.put("trade_acco", "666Z00000066");
			 request.put("password", "123456");
			 request.put("account_type", "A");
			 request.put("ta_no", "CS");
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
