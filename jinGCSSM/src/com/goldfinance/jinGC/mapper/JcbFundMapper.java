package com.goldfinance.jinGC.mapper;

import java.util.List;

import com.goldfinance.jinGC.entity.FundBuy;
import com.goldfinance.jinGC.entity.JcbFund;

public interface JcbFundMapper {
	public List<JcbFund> selectJcbFund()throws Exception;
	public JcbFund selectJcbFundByfundcode(String fundcode)throws Exception;
	public List<JcbFund> selectAll()throws Exception;

}
