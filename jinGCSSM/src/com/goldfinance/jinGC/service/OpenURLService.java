package com.goldfinance.jinGC.service;

import java.util.List;

import com.goldfinance.jinGC.po.Bank;
import com.goldfinance.jinGC.po.OpenURL;

public interface OpenURLService {
	public List<String> findOpenURLList() throws Exception;
}
