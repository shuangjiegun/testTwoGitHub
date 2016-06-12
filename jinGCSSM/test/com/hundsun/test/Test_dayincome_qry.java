package com.hundsun.test;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.goldfinance.jinGC.po.FundInfoResp;
import com.goldfinance.jinGC.util.Utils;

public class Test_dayincome_qry extends UfxSDKCommon {
	private Map<String,Object> request;
	@Before
	public void setUp() throws Exception {
		  request = new LinkedHashMap<String, Object>();
        request.put("trust_way",   "2");     //交易委托方式
        request.put("trade_acco",   "666Z00000708");        //
        request.put("password",  "098765");   //
        request.put("fund_code","162602");     
        request.put("shares","1000");     
        request.put("capital_mode","M");     
        request.put("trade_source","");     
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/fasttransfer_trade" , request);
        
		if ("".equals(response.get(0).get("error_info"))) {
			if (response.get(0).get("success_type").equals("0")) {
				for (Map<String, Object> map : response) {
					FundInfoResp fundInfoResp = new FundInfoResp();
					fundInfoResp = Utils.getObject(fundInfoResp, map);
				}
			}
		}else{
			System.out.println("无记录");
		}
		
        for(Map<String, Object> info : response){
        System.out.println(info);
        }
	}

}
