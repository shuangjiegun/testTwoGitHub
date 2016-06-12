package com.test.mapper.hunsun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_pay_sign_contract_sms extends UfxSDKCommon{
	
    private Map<String, Object> request;
    private Map<String, Object> requestConfirm;

	@Before
	public void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        String orderDate = format.substring(0, 8);
        String orderTime = format.substring(8);
        //String orderId = format + "0527";
        String orderId = format;
        request = new LinkedHashMap<String, Object>();
        //request.put("serial_no", "201508310851170517");
        request.put("capital_mode", "M");
        request.put("order_date",  "20150831");        
        request.put("order_time", "085325");
        request.put("merchant_name", "test");
        request.put("id_kind_gb", "0");
        request.put("id_no", "430524199209104433");
        request.put("real_name", "测试");
        request.put("mobile_tel", "13626710592");
        request.put("bank_account_type", "0");
        request.put("bank_account", "6230201234567890");
        request.put("bank_no", "012");
        
      //  TimeUnit.SECONDS.sleep(1);
        requestConfirm = new HashMap<String, Object>();        
        requestConfirm.putAll(request);        
        requestConfirm.put("serial_no", "201508310853250517");        
        //requestConfirm.put("original_serial_no", request.get("serial_no"));        
        requestConfirm.put("original_serial_no", "20150831085323");        
        requestConfirm.put("mobile_code", "111111");
        
	}

	 /* 短信签约第一步， 不发送验证码 & 原交易流水号     */
	@Test
	public void test1() {
        Map<String, Object> response = getUfxSDK().transact("/cwpay/v1/pay_sign_contract_sms", request);
        PrintUtil.printResponse(response);
	}
	
	/* 短信签约第二步， 发送校验码 & 原交易流水号     */
	@Test
	public void test2() {
        Map<String, Object> response = getUfxSDK().transact("/cwpay/v1/pay_sign_contract_sms", requestConfirm);
        PrintUtil.printResponse(response);
	}
	
}
