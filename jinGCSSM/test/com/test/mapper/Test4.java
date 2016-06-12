package com.test.mapper;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.goldfinance.jinGC.entity.FundQuery;
import com.goldfinance.jinGC.mapper.AllFundProductMapper;
import com.goldfinance.jinGC.service.AllFundProductService;

public class Test4 {
	
	private ApplicationContext applicationContext;

	@Before
	public void setUp() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/beans-*.xml");
	}

	@Test
	public void test() throws Exception {
/*		TestService testService = (TestService) applicationContext.getBean("testService");
		testService.testMultiDatas();*/
		
/*		MFNetValueService mfNetValueService = (MFNetValueService) applicationContext.getBean("mfNetValueService");
		mfNetValueService.findMFNetValue();*/
		AllFundProductMapper allFundProductMapper = (AllFundProductMapper) applicationContext.getBean("allFundProductMapper");
		FundQuery fundQuery = new FundQuery();
		fundQuery.setFundType("");
		fundQuery.setKeyWord("");
		
		List<String> li = allFundProductMapper.queryFundProductByCondition(fundQuery);
		System.out.println(li);
	}

}
