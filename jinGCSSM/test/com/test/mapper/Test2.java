package com.test.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.goldfinance.jinGC.common.UfxSDKCommon;
import com.hundsun.fcloud.ufx.sdk.UfxSDK;
import com.hundsun.fcloud.ufx.sdk.http.HttpUfxSDK;



public class Test2 extends UfxSDKCommon{
    private Map<String, Object> request;

    private String url = "/fund_ets_registerinfo_qry";

    @Test
    public void query() {
        System.out.println((request));

        String path = "/cwets/v1" + url;
        Map<String, Object> response = getUfxSDK().transact("/cwets/v1/fund_ets_registerinfo_qry", request);
        System.out.println((response));
    }

    @Before
    public void before() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = sdf.format(new Date());
        String orderDate = format.substring(0, 8);
        String orderTime = format.substring(8);
        String orderId = format + "0527";

        request = new LinkedHashMap<String, Object>();
        // 公共字段
        request.put("targetcomp_id", "10000");
        request.put("sendercomp_id", "90006");

        //request.put("serial_no", orderId);

        request.put("mobile_tel", "13310881188");
        request.put("email", "550110480@qq.com");
    }

}
