package com.test.mapper.hunsun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_pay_delegate_payment extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/pay_delegate_payment";

	@Before
	public void setUp() throws Exception {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        String format = sdf.format(new Date());
	        String orderDate = format.substring(0, 8);
	        String orderTime = format.substring(8);
	        String orderId = format + "05";

	        request = new HashMap<String, Object>();
	        // 公共字段
	        request.put("targetcomp_id", "3052");
	        request.put("sendercomp_id", "9006");

	        request.put("serial_no", orderId);
	        request.put("capital_mode", "r");       //3: sfjpay
	        request.put("merchant_name", "test");
	        request.put("orderDate", orderDate);
	        request.put("orderTime", orderTime);
	        request.put("trade_balance", "11.11");
	        request.put("money_type", "156");
	        
	        request.put("real_name", "赵钱孙");
	        request.put("bank_account_type", "1");
	        request.put("bank_account", "6225882106897891");
	        request.put("bank_no", "002");
	        request.put("open_province_code", "上海");
	        request.put("open_city_no", "上海");
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwpay/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        PrintUtil.printResponse(response);
	}

}
