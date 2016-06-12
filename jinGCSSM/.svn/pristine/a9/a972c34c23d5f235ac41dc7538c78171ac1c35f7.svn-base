package com.goldfinance.jinGC.service.impl;

import com.goldfinance.jinGC.service.SmsPortal;
import com.goldfinance.jinGC.util.OracleConnection;

public class SmsPortalImpl implements SmsPortal {

	@Override
	public String send(String destmobile, String msgText) {
		OracleConnection oracleConn = OracleConnection.getConn();
		Boolean result = oracleConn.sendSMS(destmobile, msgText);
		String flag = "ok";
		if(!result){
			flag = "error";
		}
		return flag;
	}

}
