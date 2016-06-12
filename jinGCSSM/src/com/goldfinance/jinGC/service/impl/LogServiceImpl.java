package com.goldfinance.jinGC.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.mapper.LogMapper;
import com.goldfinance.jinGC.po.Log;
import com.goldfinance.jinGC.service.LogService;

public class LogServiceImpl implements LogService {
	
	@Autowired
	private LogMapper logMapper;

	@Override
	public void insertLog(String userId, String operate, String moneyAmount, String result, String detail) throws Exception {
			Log log = new Log();
			log.setUserId(userId);
			log.setOperate(operate);
			log.setMoneyAmount(moneyAmount);
			log.setResult(result);
			log.setDetail(detail);
			log.setCreatedatetime(new Date());
			logMapper.insertLog(log);
	}

	@Override
	public Log selectTimeByUserId(User user) throws Exception {
		return logMapper.selectTimeByUserId(user);
	}

}
