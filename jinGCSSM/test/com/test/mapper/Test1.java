package com.test.mapper;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.goldfinance.jinGC.mapperOra.JYFundProductMapper;
import com.goldfinance.jinGC.poOra.MFNetValuePerformanceExtend;
import com.goldfinance.jinGC.poOra.QueryPo;

public class Test1 {

	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/beans-*.xml");
	}

	@Test
	public void testFindUserById() throws Exception {
		String str = "[7]";
		String strA = str.replace("[", "");
		String strB = strA.replace("]", "");
		System.out.println(strB);
/*		StringBuffer sb = new StringBuffer();
		String[] arr = str.split("");
		for(int i=0;i<arr.length;i++){
			if(!arr[i].equals("[") && !arr[i].equals("]")){
				sb.append(arr[i]);
			}
		}
		System.out.println(sb);*/
		//System.out.println(str.replace("]", ""));
		//System.out.println(str.replaceAll("", "["));

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
