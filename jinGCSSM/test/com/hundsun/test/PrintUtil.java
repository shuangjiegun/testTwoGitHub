package com.hundsun.test;

import java.util.Map;

/**
 * @author huke10591
 * @create 2015/5/25
 */
public class PrintUtil {
    
    private static final String REMOTE = "/cwpay/v1";
    
    private static final String LOCAL = "/pay/ufx";
    
    public static final String PAY_PREFIX_PATH = REMOTE;        
    
    public static void printRequestParam(Map<String, Object> params) {
        System.out.println("请求参数: ");
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            System.out.println(String.format("\"%s\"  :  \"%s\",", entry.getKey(), entry.getValue()));
        }
    }
    
    public static void printResponse(Map<String, Object> response) {
        System.out.println("返回报文: ");
        for (Map.Entry entry : response.entrySet()) {
            System.out.println(entry.getKey() + " :  " + entry.getValue());
        }
    }
}
