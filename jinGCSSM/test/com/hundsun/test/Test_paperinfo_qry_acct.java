package com.hundsun.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;


// 问卷调查内容查询  这个好像是针对已做过调查的用户查他们的问卷
public class Test_paperinfo_qry_acct extends UfxSDKCommon{
	private Map<String,Object> request;

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();

        request.put("trust_way",   "2");     //交易委托方式
        request.put("query_type", "1");        //查询类别
       // request.put("ta_acco",  "");      //TA帐号
        request.put("trade_acco", "666Z00000074");                //交易账号
        //request.put("cust_type","1");                  //客户类别
        //request.put("full_name", "尼科诺夫");                 //账户全称
        //request.put("id_kind_gb","0");                 //证件类别
        //request.put("id_no","110000198006144002");                       //证件号码
        //request.put("password","123456");                    //密码
        request.put("paper_client_type","0");         //问卷客户类别
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_qry_acct" , request);

        System.out.println(response);
	}

}
