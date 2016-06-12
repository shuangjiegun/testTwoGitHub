package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_clientinfo_qry_acct extends UfxSDKCommon{
    private Map<String, Object> request;

	@Before
	public void setUp() throws Exception {
		 request = new LinkedHashMap<String, Object>();
	        // 公共字段
	        
			 request.put("trust_way", "2");
			request.put("trade_acco", "666Z00000074");
			 //request.put("password", "469888"); //正确密码
			 request.put("password", "NOPASSWORD"); //错误密码
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/clientinfo_qry_acct", request);
        //List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/clientinfo_qry_acct", request);
        PrintUtil.printResponse(response);
        //System.out.println(response);
	}

}
