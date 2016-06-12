package com.goldfinance.jinGC.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.entity.FundBuy;
import com.goldfinance.jinGC.entity.JcbFund;
import com.goldfinance.jinGC.mapper.JcbFundMapper;
import com.goldfinance.jinGC.service.JcbFundService;

public class JcbFundServiceImpl implements JcbFundService {
	@Autowired
	private JcbFundMapper jcbFundMapper;
	@Override
	public List<JcbFund> selectJcbFund() throws Exception {
		return jcbFundMapper.selectJcbFund();
	}
	@Override
	public JcbFund selectJcbFundByfundcode(String fundcode) throws Exception {
		return jcbFundMapper.selectJcbFundByfundcode(fundcode);
	}
	@Override
	public List<JcbFund> selectAll() throws Exception {
		// TODO Auto-generated method stub
		return jcbFundMapper.selectAll();
	}

}
