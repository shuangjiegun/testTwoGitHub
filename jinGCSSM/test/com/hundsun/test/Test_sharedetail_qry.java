package com.hundsun.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.PrintUtil;
import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_sharedetail_qry extends UfxSDKCommon {
	private Map<String,Object> request;
	@Before
	public void setUp() throws Exception {
		  request = new LinkedHashMap<String, Object>();
        request.put("trust_way",   "2");     //交易委托方式
        request.put("request_num",   "50");        //请求行数
        //request.put("client_id",   "668");        //请求行数
        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
        request.put("qry_beginrownum","1");           //查询起始行号
        request.put("fund_code","600570");         
        //request.put("fund_busin_code","1");        //1 个人  0 机构   
        //request.put("trade_acco","666Z00000074");         
/*        request.put("begin_date","20150809");          
        request.put("end_date","20150809");       */   
        //request.put("fund_code","392001");        
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/rate_qry" , request);  //sharedetail_qry   rate_qry  divi_qry 
        for(Map<String, Object> info : response){
        System.out.println(info);
        }
	}

}
