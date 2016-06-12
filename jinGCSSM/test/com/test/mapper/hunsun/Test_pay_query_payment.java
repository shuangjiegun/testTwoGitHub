package com.test.mapper.hunsun;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_pay_query_payment extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/pay_query_payment";

	@Before
	public void setUp() throws Exception {

		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = "20150601101030";
        String orderDate = format.substring(0, 8);
        String orderTime = format.substring(8);
        String orderId = format + "05";

        request = new HashMap<String, Object>();
        // 公共字段
	        request.put("targetcomp_id", "3052");
	        request.put("sendercomp_id", "9006");

        //request.put("serial_no", orderId);
        request.put("capital_mode", "r");       //3: sfjpay
        request.put("merchant_name", "test");
        request.put("orderDate", orderDate);
        request.put("orderTime", orderTime);
        
        request.put("original_serial_no", orderId);
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwpay/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
