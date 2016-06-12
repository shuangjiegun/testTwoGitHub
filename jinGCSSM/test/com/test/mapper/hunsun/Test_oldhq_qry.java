package com.test.mapper.hunsun;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;

public class Test_oldhq_qry extends UfxSDKCommon{
	
    private Map<String, Object> request;

    private String url = "/oldhq_qry";

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();
        // 公共字段
/*        request.put("targetcomp_id", "3052");
        request.put("sendercomp_id", "9006");*/

        request.put("request_num", "50");
        request.put("reqry_recordsum_flag", "1");
        request.put("qry_beginrownum", "1");
        request.put("fund_code", "320107");
        request.put("nav_begin_date", "20150810");
        request.put("nav_end_date", "20150810");
	}

	@Test
	public void test() {
		PrintUtil.printRequestParam(request);
        String path = "/cwsale/v1" + url;
        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/oldhq_qry", request);
        for(Map<String, Object> info : response){
        	System.out.println(info);
        }
		   Calendar   cal   =   Calendar.getInstance();
		   cal.add(Calendar.DATE,   -2);
		   String yesterday = new SimpleDateFormat( "yyyyMMdd ").format(cal.getTime());
		   System.out.println(yesterday);
		
	}

}
