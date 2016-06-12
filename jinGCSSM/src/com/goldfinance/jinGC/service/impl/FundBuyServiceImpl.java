package com.goldfinance.jinGC.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.entity.FundBuy;
import com.goldfinance.jinGC.mapper.FundBuyMapper;
import com.goldfinance.jinGC.service.FundBuyService;

public class FundBuyServiceImpl implements FundBuyService {

	@Autowired
	private FundBuyMapper fundBuyMapper;
	
	@Override
	public void insertFundBuy(FundBuy fundBuy) throws Exception {
		fundBuyMapper.insertFundBuy(fundBuy);

	}

	@Override
	public FundBuy selectFundBuy(FundBuy fundBuy) throws Exception {
		
		return fundBuyMapper.selectFundBuy(fundBuy);
	}

}
