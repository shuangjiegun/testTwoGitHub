package com.goldfinance.jinGC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.mapper.BankMapper;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.service.BankService;

public class BankServiceImpl implements BankService {
	
	@Autowired
	private BankMapper bankMapper;

	@Override
	public List<Bank> findBankList() throws Exception {
		List<Bank> banks = bankMapper.findBankList();
		return banks;
	}

	@Override
	public Bank findBankByBankCode(String bankCode) throws Exception {
		Bank bank = bankMapper.findBankByBankCode(bankCode);
		return bank;     
	}

}
