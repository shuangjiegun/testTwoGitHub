package com.goldfinance.jinGC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.mapper.BankMapper;
import com.goldfinance.jinGC.mapper.OpenURLMapper;
import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.OpenURL;
import com.goldfinance.jinGC.service.BankService;
import com.goldfinance.jinGC.service.OpenURLService;

public class OpenURLServiceImpl implements OpenURLService {
	
	@Autowired
	private OpenURLMapper openURLMapper;

	@Override
	public List<String> findOpenURLList() throws Exception {
		List<String> openURLList = openURLMapper.findOpenURLList();
		return openURLList;
	}
}
