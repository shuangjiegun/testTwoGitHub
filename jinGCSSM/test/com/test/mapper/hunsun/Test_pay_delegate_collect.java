package com.test.mapper.hunsun;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_pay_delegate_collect extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/pay_delegate_collect";

	@Before
	public void setUp() throws Exception {
	        String format = "20150601101030";
	        String orderDate = format.substring(0, 8);
	        String orderTime = format.substring(8);
	        String orderId = format + "05";
	        request = new HashMap<String, Object>();
	        // 公共字段
	        request.put("targetcomp_id", "3052");
	        request.put("sendercomp_id", "9006");

	        request.put("serial_no", orderId);
	        request.put("capital_mode", "1");   
	        request.put("merchant_name", "test");
	        request.put("trade_balance", "1000");
	        request.put("orderDate", orderDate);
	        request.put("orderTime", orderTime);
	        request.put("id_kind_gb", "0");
	        request.put("id_no", "341223198600007395");
	        request.put("real_name", "猪八戒");
	        request.put("mobile_tel", "13901580158");
	        request.put("bank_account_type", "0");
	        request.put("bank_account", "6222021312000120000");
	        request.put("bank_no", "002");

	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwpay/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
