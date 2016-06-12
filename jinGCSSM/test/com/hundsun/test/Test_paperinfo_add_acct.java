package com.hundsun.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;


// 问卷调查内容增加    
public class Test_paperinfo_add_acct extends UfxSDKCommon{
	private Map<String,Object> request;

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();

        request.put("trust_way",   "2");     //交易委托方式
        request.put("cust_type","1");                  //客户类别
        request.put("full_name", "赵薇");                 //账户全称
        request.put("id_kind_gb","0");                 //证件类别
        request.put("id_no","370883197807243510");                       //证件号码
        request.put("elig_content","1:4|2:2|3:2|4:3|5:1|6:3|7:3|8:3|9:3|");    
        request.put("paper_client_type","0");         //问卷客户类别
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_add_acct" , request);

        System.out.println(response);
	}

}
