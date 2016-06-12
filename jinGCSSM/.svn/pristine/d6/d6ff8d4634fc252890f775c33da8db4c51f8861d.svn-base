package com.goldfinance.jinGC.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.service.BankService;


/**
 * 此定时任务并无实际意义；只是每5个小时去查一遍数据库，保持与数据库的连接
 * @author liumj
 *
 */
public class RemainConnectTask {
	
	@Autowired
	private BankService bankService;
	
	public void run() {
		
		try {
			List<Bank> banks = bankService.findBankList();
			System.out.println(banks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
