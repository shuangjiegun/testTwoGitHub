package com.goldfinance.jinGC.service;

import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.Log;


public interface LogService {
	public void insertLog(String userId, String operate,  String moneyAmount, String result,String detail) throws Exception;
	public Log selectTimeByUserId(User user)throws Exception;
}
