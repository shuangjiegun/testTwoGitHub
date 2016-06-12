package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_capital_balance_qry extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/capital_balance_qry";
    //private String url = "/capital_serial_qry";

	@Before
	public void setUp() throws Exception {

        request = new LinkedHashMap<String, Object>();
        // 公共字段
        request.put("targetcomp_id", "3052");
        request.put("sendercomp_id", "9006");

        request.put("trust_way", "2");
        request.put("request_num", "30");
        request.put("reqry_recordsum_flag", "0");
        request.put("qry_beginrownum", "1");

		
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
        
	}
}
