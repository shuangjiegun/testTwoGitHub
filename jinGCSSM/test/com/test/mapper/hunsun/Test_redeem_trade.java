package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_redeem_trade extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/redeem_trade";

	@Before
	public void setUp() throws Exception {
		
        request = new LinkedHashMap<String, Object>();
        
        request.put("trust_way", "2");
        request.put("trade_acco", "666Z00000003");
        request.put("password", "222");
        request.put("fund_code", "320102");
        request.put("share_type", "A");
        request.put("shares", "5");
        request.put("fund_exceed_flag", "1");
       // request.put("capital_mode", "1");

	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
