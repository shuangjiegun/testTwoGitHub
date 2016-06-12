package com.test.mapper.hunsun;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.po.FundInfoResp;
import com.goldfinance.jinGC.util.Utils;

public class Test_trade_apply_qry extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/trade_apply_qry";

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();

        request.put("trust_way", "2");
        request.put("reqry_recordsum_flag", "1");
        request.put("qry_beginrownum", "1");
        request.put("request_num", "50");
        request.put("trade_acco","666Z00000003");   
        //request.put("begin_date","20150730");   
        //request.put("end_date","20150730");   
        //request.put("client_id","148");   
        //request.put("allot_no","20160112000082");   

	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        List<Map<String, Object>> response = getUfxSDK().query(path, request);
        
		if ("".equals(response.get(0).get("error_info"))) {
			if (response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					FundInfoResp fundInfoResp = new FundInfoResp();
					fundInfoResp = Utils.getObject(fundInfoResp, map);
				}
			}
		}
		
        for(Map<String, Object> info : response){
        	System.out.println(info);
        }
	}

}
