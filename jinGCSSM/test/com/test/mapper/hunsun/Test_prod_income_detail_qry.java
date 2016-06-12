package com.test.mapper.hunsun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_prod_income_detail_qry extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/prod_income_detail_qry";

	@Before
	public void setUp() throws Exception {


        request = new HashMap<String, Object>();

        request.put("trust_way", "2");     //交易委托方式
        request.put("requet_num", "50");//请求行数
        request.put("reqry_recordsum_flag", "1");     //重新统计总记录数标志
        request.put("qry_beginrownum", "1");     //查询其实行号
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        List<Map<String, Object>> response = getUfxSDK().query(path, request);
        for(Map<String, Object> info : response){
        	System.out.println(info);
        }
	}
}
