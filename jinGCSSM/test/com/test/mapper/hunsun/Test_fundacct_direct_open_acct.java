package com.test.mapper.hunsun;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;



public class Test_fundacct_direct_open_acct extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/fundacct_direct_open_acct";

	@Before
	public void setUp() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());

        request = new LinkedHashMap<String, Object>();


/*        request.put("trust_way", "2");
        request.put("trade_acco", "");   
        request.put("en_entrust_way", "0|1|0|0|1|0|0|");
        request.put("netno", "777");
        request.put("apply_time", format);
        request.put("account_type", "A");
        request.put("cust_type", "1");
        request.put("client_full_name", "http测试");
        request.put("client_name", "http测试");
        request.put("id_kind_gb", "2");   //0是身份证，也可以其他，参见接口文档
        request.put("id_no", "1234567812");
        request.put("password", "1123123");
        request.put("mobile_tel", "156156156");
        request.put("contact_name", "yang");
        request.put("contract_id_kind_gb", "1");
        request.put("contact_id_no", "123123123");
        request.put("bank_no", "002");
        request.put("bank_name", "中国工商银行");
        request.put("bank_account", "6222021001093605345");
        request.put("bank_account_name", "http测试");
        request.put("capital_mode", "r");  //1 普通方式   G招行网银
        request.put("ta_no", "25");     
        request.put("fund_card", "1234567");   
        request.put("detail_fund_way", "01");   */  
        
/*        request.put("trust_way", "2");
        request.put("cust_type", "0");
        request.put("account_type", "A");
        request.put("client_full_name", "孙悟空");
        request.put("client_name", "孙悟空");
        request.put("id_kind_gb", "0");
        request.put("id_no", "341223198504157395");
        request.put("password", "123456");
        request.put("mobile_tel", "13901587985");
        request.put("contact_name", "yang");
        request.put("contract_id_kind_gb", "0");
        request.put("contact_id_no", "123123123");
        request.put("bank_no", "002");
        request.put("bank_name", "中国工商银行");
        request.put("bank_account", "6222021312000128596");
        request.put("bank_account_name", "孙悟空");
        request.put("capital_mode", "1");
        request.put("ta_no", "B0");*/
        
/*        request.put("trust_way", "2");
        request.put("en_entrust_way", "0|1|0|0|1|0|0|");
        request.put("cust_type", "0");
        request.put("account_type", "A");
        request.put("client_full_name", "猪八戒");
        request.put("client_name", "猪八戒");
        request.put("id_kind_gb", "0");
        request.put("id_no", "341223198600007395");
        request.put("password", "123456");
        request.put("mobile_tel", "13901580158");
        request.put("contact_name", "yang");
        request.put("contract_id_kind_gb", "0");
        request.put("contact_id_no", "123123123");
        request.put("bank_no", "002");
        request.put("bank_name", "中国工商银行");
        request.put("bank_account", "6222021312000120000");
        request.put("bank_account_name", "猪八戒");
        request.put("capital_mode", "1");
        request.put("ta_no", "B0");*/
        // 公共字段
/*        request.put("targetcomp_id", "91001");   //3052
        request.put("sendercomp_id", "91001");*/
        
        request.put("trust_way", "2");
        //request.put("en_entrust_way", "0|1|0|0|1|0|0|");
        request.put("cust_type", "1");
        //request.put("account_type", "A");
        request.put("client_full_name", "晋飞W三");
        request.put("client_name", "晋飞W三");
        request.put("id_kind_gb", "0");
        request.put("id_no", "6225765723121701");
        request.put("password", "123456");
        request.put("mobile_tel", "13915230701");
       // request.put("contact_name", "yang");
      //  request.put("contract_id_kind_gb", "0");
       // request.put("contact_id_no", "123123123");
        request.put("bank_no", "002");
        request.put("bank_name", "中国工商银行");
        request.put("bank_account", "6225752744121701");
        request.put("bank_account_name", "晋飞W三");
        request.put("capital_mode", "M");
        request.put("ta_no", "B0");
        request.put("detail_fund_way", "01");
        request.put("fund_card", format);
        
        
        
        
      /*  request.put("trust_way", "2");
        request.put("en_entrust_way", "0|1|0|0|1|0|0|");
        request.put("cust_type", "0");
        request.put("account_type", "A");
        request.put("client_full_name", "唐僧");
        request.put("client_name", "唐僧");
        request.put("id_kind_gb", "0");
        request.put("id_no", "341223198600008888");
        request.put("password", "123456");
        request.put("mobile_tel", "13901588888");
        request.put("contact_name", "yang");
        request.put("contract_id_kind_gb", "0");
        request.put("contact_id_no", "123123123");
        request.put("bank_no", "002");
        request.put("bank_name", "中国工商银行");
        request.put("bank_account", "6222021312000128888");
        request.put("bank_account_name", "唐僧");
        request.put("capital_mode", "1");
        request.put("ta_no", "B0");*/

        
        
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        Map<String, Object> response = getUfxSDK().transact(path, request);
        System.out.println("---------------->" + response.get("success_type") + "---------------" + response.get("error_info"));
       PrintUtil.printResponse(response);
	}

}
