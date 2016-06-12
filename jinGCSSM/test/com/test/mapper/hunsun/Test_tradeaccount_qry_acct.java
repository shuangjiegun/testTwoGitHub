package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_tradeaccount_qry_acct extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/tradeaccount_qry_acct";

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();
        // 公共字段
        request.put("targetcomp_id", "3052");
        request.put("sendercomp_id", "9006");
        request.put("trust_way", "2");
        request.put("trade_acco", "660Z00000840");
/*        request.put("ta_acco", "*B0Z00000839");
        request.put("password", "123456");*/
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
