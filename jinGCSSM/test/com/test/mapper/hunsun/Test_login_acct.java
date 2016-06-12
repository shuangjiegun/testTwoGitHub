package com.test.mapper.hunsun;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.util.HSERYCPT;

public class Test_login_acct extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/login_acct";

	@Before
	public void setUp() throws Exception {
		
        request = new LinkedHashMap<String, Object>();
        // 公共字段
        //request.put("targetcomp_id", "50030");
       // request.put("sendercomp_id", "91001");
        
/*		 request.put("trust_way", "2");   //此些数据可登录
		 request.put("id_kind_gb", "0");
		 request.put("id_no", "341223198600007930");
		 request.put("client_name", "猪629八戒");
		 request.put("password", "123456");	*/
        
		 request.put("trust_way", "2");
		 request.put("id_kind_gb", "0");
		 request.put("id_no", "420702199203067664"); //330184199005294336
		/*	HSERYCPT miTool = new HSERYCPT();
			String miText = miTool.encrypt("123123");*/
		 request.put("password", new HSERYCPT().encrypt("q1w2e3"));	
        
        
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
