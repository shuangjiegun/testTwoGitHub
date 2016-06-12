package com.hundsun.test;

import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class MyTest {

	@Test
	public void test() {
	
/*		PropertyConfigurator.configure("F:\\job\\job-install-two\\tomcat7two\\webapps\\config\\log4j.properties");

		Logger logger = Logger.getLogger(MyTest.class);

		org.slf4j.Logger logger2 = LoggerFactory.getLogger(MyTest.class);
		logger.debug("xxxhello");
		logger.error("xxxhello");

		logger2.debug("xxxhello");
		logger2.error("xxxhello");
*/
		
		String password = "123456*com";
		MD5 md5 = new MD5();
		System.out.println(md5.getMD5ofStr(password));
		int temp = new Random().nextInt(9999);
		System.out.println(temp);
	}
}
