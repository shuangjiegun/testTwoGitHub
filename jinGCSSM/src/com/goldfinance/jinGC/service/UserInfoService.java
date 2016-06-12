package com.goldfinance.jinGC.service;

import java.util.List;

import com.goldfinance.jinGC.entity.AccountEmail;
import com.goldfinance.jinGC.entity.PersonInfo;
import com.goldfinance.jinGC.entity.TradeEmail;
import com.goldfinance.jinGC.entity.TradeMessage;
import com.goldfinance.jinGC.po.UserInfo;

public interface UserInfoService {
	
	public void insertUserInfo(UserInfo userInfo) throws Exception;

	//public UserInfo findUserInfoByNumAndType(String codeType, String codeNum) throws Exception;
	//查询用户的总交易账户数
	public int findDistinctTradeAccoNumber(String id_kind_gb, String id_no) throws Exception;
	//查询用户针对某家TA公司的开户信息
	public List<UserInfo> findTACompanyOpenAccoInfo(String id_kind_gb, String id_no, String ta_no) throws Exception;
	//【根据银行编号、银行卡号、身份证号查询交易账号，可能查出多条记录(主要是表里有其他多对多关系)，但银行卡号与交易账号是一对一的关系
	public List<UserInfo> findUserInfo(String bank_no, String bank_account, String id_no) throws Exception;
	//根据客户编号查询用户信息，由于有多交易账号的情况，所以查出来的是List，主要是要得到客户的银行卡信息
	public List<UserInfo> findUserInfoByClientId(String client_id) throws Exception;
	////根据客户编号查询用户的不同的银行卡(交易账号)信息(同一张卡可能用在了不同的TA，所以需要去重)
	public List<UserInfo> findDistinctBankAccount(String client_id) throws Exception;
	//插入账户邮件信息
	public void insertAccountEmail(AccountEmail email) throws Exception;
	//查询AccountEmail表中某申请日期下所有不重复id_no集合
	public List<String> findAccountEmailDistinctIdNo(String string) throws Exception;
	//查询账户类记录
	public List<AccountEmail> findAccountEmailByApplyDateAndIdNo(AccountEmail query)  throws Exception;
	//查询每个人的最新的email
	public String findAccountEmailLastestEmail(AccountEmail query) throws Exception;

	public List<AccountEmail> findAccountEmailRecordList(String qryDate) throws Exception;

	public List<TradeEmail> findTradeEmailRecordList(String affirmDate) throws Exception;

	public void insertTradeEmail(TradeEmail email) throws Exception;

	public List<String> findTradeEmailDistinctTradeAcco(String affirmDate) throws Exception;

	public PersonInfo findUserTradeByTradeAcco(String string) throws Exception;

	public void insertUserTrade(PersonInfo pi) throws Exception;

	public List<String> findTradeEmailUserTradeDistinctIdNo(String affirmDate) throws Exception;

	public List<TradeEmail> findTradeEmailByAffirmDateAndIdNo(TradeEmail query) throws Exception;

	public List<TradeMessage> findTradeMessageRecordList(TradeMessage query) throws Exception;

	public void insertTradeMessage(TradeMessage msg) throws Exception;

	public List<String> findTradeMessageDistinctTradeAcco(TradeMessage query) throws Exception;

	public PersonInfo findUserTradeMessageByTradeAcco(String tradeAcco) throws Exception;

	public void insertUserTradeMessage(PersonInfo pi) throws Exception;

	public List<String> findTradeMessageUserTradeMessageDistinctIdNo(TradeMessage query) throws Exception;

	public List<TradeMessage> findTradeMessageByAffirmDateAndFundBusinCodeAndIdNo(TradeMessage query) throws Exception;

	public void deleteAccountEmailRecordList(String qryDate);

	public void deleteTradeEmailRecordList(String affirmDate);

	public void deleteTradeMessageRecordList(TradeMessage query);

	public void deleteUserTradeMessageByTradeAcco(String tradeAcco);

	public void deleteUserTradeByTradeAcco(String tradeAcco);

	public int deleteUserInfoByClientId(String client_id);
}
