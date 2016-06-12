package com.goldfinance.jinGC.service;

import java.util.List;

import com.goldfinance.jinGC.entity.FundBuy;
import com.goldfinance.jinGC.entity.JcbFund;

public interface JcbFundService {
	public List<JcbFund> selectJcbFund()throws Exception;
	public JcbFund selectJcbFundByfundcode(String fundcode)throws Exception;
	public List<JcbFund> selectAll()throws Exception;
}
