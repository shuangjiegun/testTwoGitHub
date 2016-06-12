package com.test.mapper.hunsun;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class NewOpenAcco extends UfxSDKCommon{
	
    @Test
    public void testScenario1() throws Exception {
	
	Map<String, Object> request = new HashMap<String, Object>();
    // 公共字段
    request.put("targetcomp_id", "91001");   //
    request.put("sendercomp_id", "91001");   // 91001
    request.put("trust_way", "2");
    request.put("en_entrust_way", "0|1|0|0|1|0|0|");
    request.put("cust_type", "0");
    request.put("account_type", "A");
    request.put("client_full_name", "杨丽娟");
    request.put("client_name", "杨丽娟");
    request.put("id_kind_gb", "0");
    request.put("id_no", "341223198910108001");
    request.put("mobile_tel", "15658891011");
    request.put("contact_name", "yang");
    request.put("contract_id_kind_gb", "0");
    request.put("contact_id_no", "123123123");
    request.put("bank_no", "002");
    request.put("bank_name", "中国工商银行");
    request.put("bank_account", "6222021001093605224");
    request.put("bank_account_name", "杨丽娟");
    request.put("capital_mode", "1");
    request.put("ta_no", "B0");
    System.out.println(request);
    Map<String, Object> response = getUfxSDK().transact("/cwsale/v1/fundacct_direct_open_acct", request);
    PrintUtil.printResponse(response);
    
    
}
    
}
