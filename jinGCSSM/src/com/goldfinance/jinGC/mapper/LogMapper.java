package com.goldfinance.jinGC.mapper;

import com.goldfinance.jinGC.entity.User;
import com.goldfinance.jinGC.po.Log;

public interface LogMapper {
	public void insertLog(Log log) throws Exception;
	public Log selectTimeByUserId(User user)throws Exception;
}
