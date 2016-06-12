package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_fundinfo_query extends UfxSDKCommon{
    private Map<String, Object> request;

	@Before
	public void setUp() throws Exception {
        request =new LinkedHashMap<String, Object>();
        request.put("trust_way",  "2");     //交易委托方式
        request.put("request_num", "50");                 //请求行数
        request.put("reqry_recordsum_flag",  "1");    //重新统计总记录数标志
        request.put("qry_beginrownum",           "1"); //查询起始行号
        request.put("sort_direction","");              //返回排序方式
        request.put("trade_acco", "");                  //交易帐号
        request.put("client_id",             "");    //客户编号
        //request.put("fund_code","320108");                     //基金代码
	}

	@Test
	public void test() {
        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fundinfo_qry" , request);
        
        
        for(Map<String, Object> info : response){
        			System.out.println(info);
        }
        System.out.println("response------->"+response);
        System.out.println("response.get(0)----6--->"+(response.get(0).get("data")==null));
	}

}
