package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_tradeaccount_add_acct extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/tradeaccount_add_acct";

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();
   
        request.put("en_entrust_way", "0|1|0|0|1|0|0|");
        request.put("trust_way", "2");
        request.put("trade_acco", "666Z00000011");
        request.put("password", "123456");
        //request.put("new_password", "123456");        
      //  request.put("trade_account_name", "晓松6");
       // request.put("home_tel", "13912340050");
        //request.put("contact_name", "猪八戒");
        //request.put("contract_id_kind_gb", "1");
       // request.put("contact_id_no", "123123123");
        //request.put("address", "hangzhou");
       // request.put("zipcode", "310000");        
        request.put("mobile_tel", "13912340050");        
        request.put("bank_no", "005");
        request.put("bank_name", "中国建设银行");
        request.put("bank_account_name", "晓松6");
        request.put("bank_account", "6214850212330050");
        request.put("capital_mode", "1");

        
        
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
