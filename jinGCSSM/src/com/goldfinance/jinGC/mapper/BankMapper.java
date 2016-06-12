package com.goldfinance.jinGC.mapper;

import java.util.List;
import com.goldfinance.jinGC.po.Bank;

public interface BankMapper {
	public List<Bank> findBankList() throws Exception;
	public Bank findBankByBankCode(String bankCode) throws Exception;
}
