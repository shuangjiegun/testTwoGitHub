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

public class Test_fasttransfer_trade extends UfxSDKCommon {
	private Map<String,Object> request;
	@Before
	public void setUp() throws Exception {
		  request = new LinkedHashMap<String, Object>();
        request.put("trust_way",   "2");     //交易委托方式
        request.put("request_num",   "50");        //请求行数
        request.put("reqry_recordsum_flag",  "0");   //重新统计总记录数标志
        request.put("qry_beginrownum","1");           //查询起始行号
        request.put("belongdate_begin","20160412");         //放入当天的日期，结果返回的收益日期做为存储的那个日期
        request.put("belongdate_end","20160412");         
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/dayincome_qry" , request);
        
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
