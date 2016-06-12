package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_subscribe_trade extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/subscribe_trade";

	@Before
	public void setUp() throws Exception {

        request = new LinkedHashMap<String, Object>();        
        request.put("trust_way", "2");
        request.put("trade_acco", "666Z00000011");
        request.put("password", "123456");
        request.put("fund_code", "320102");
        request.put("bank_no", "2");
        request.put("bank_account", "6214850212330050");
        request.put("share_type", "A");
        request.put("balance", "100");
        request.put("money_type", "156");
        request.put("capital_mode", "1");
       // request.put("trade_source", "test");

	}

	@Test
	public void test() {
		
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
        
	}

}
