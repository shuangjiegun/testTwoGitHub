package com.hundsun.test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;


// 问卷题目选项查询  
public class Test_paperinfo_question_section_qry extends UfxSDKCommon{
	private Map<String,Object> request;

	@Before
	public void setUp() throws Exception {
        request = new LinkedHashMap<String, Object>();

        request.put("trust_way",   "2");     //交易委托方式
        request.put("request_num",   "40");        //请求行数
        request.put("reqry_recordsum_flag",  "1");   //重新统计总记录数标志
        request.put("qry_beginrownum","1");           //查询起始行号
        //request.put("sort_direction","");            //返回排序方式
        request.put("question_no","2");                //问题编号
        request.put("answer_object",  "1");          //回答对象  0机构；1个人
        //request.put("paper_client_type","");         //问卷客户类别
	}

	@Test
	public void test() {
        PrintUtil.printRequestParam(request);

        List<Map<String, Object>> response = getUfxSDK().query("/cwsale/v1/paperinfo_question_section_qry" , request);

        System.out.println(response);
	}

}
