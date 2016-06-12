package com.hundsun.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_account_apply_qry extends UfxSDKCommon {
	private Map<String,Object> request;

	@Before
	public void setUp() throws Exception {
		    request = new LinkedHashMap<String, Object>();
	        request.put("trust_way",   "2");     //交易委托方式
	        request.put("request_num",   "50");        //请求行数
	        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
	        request.put("qry_beginrownum","1");           //查询起始行号
	        //request.put("client_id","3058");          
	        request.put("begin_date","20151101");          
	        request.put("end_date","20151117");    
	        request.put("fund_busin_code","001"); 
	        //request.put("begin_date","001"); 
	        //request.put("end_date","001"); 

	}

	@Test
	public void test() {
        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/account_apply_qry" , request);
        
        System.out.println(!"".equals(response.get(0).get("error_info")));
        for(Map<String, Object> info : response){
        System.out.println(info);
        }
	}

}
