package com.goldfinance.jinGC.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.goldfinance.jinGC.entity.AccountEmail;
import com.goldfinance.jinGC.entity.PersonInfo;
import com.goldfinance.jinGC.entity.TradeEmail;
import com.goldfinance.jinGC.entity.TradeMessage;
import com.goldfinance.jinGC.mapper.UserInfoMapper;
import com.goldfinance.jinGC.po.UserInfo;
import com.goldfinance.jinGC.service.UserInfoService;

public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public void insertUserInfo(UserInfo userInfo) throws Exception {
		userInfoMapper.insertUserInfo(userInfo);
	}

/*	@Override
	public UserInfo findUserInfoByNumAndType(String codeType, String codeNum) throws Exception {
		
		UserInfo userInfo = new UserInfo();
		userInfo.setId_kind_gb(codeType);
		userInfo.setId_no(codeNum);
		userInfo.setMobile_tel(codeNum);
		UserInfo userInfo2 = userInfoMapper.findUserInfoByNumAndType(userInfo);
		return userInfo2;
	}*/

	//查询用户的总交易账户数
	@Override
	public int findDistinctTradeAccoNumber(String id_kind_gb, String id_no) throws Exception {
		UserInfo ui = new UserInfo();
		ui.setId_kind_gb(id_kind_gb);
		ui.setId_no(id_no);
		int num = userInfoMapper.findDistinctTradeAccoNumber(ui);
		return num;
	}

	//查询用户针对某家TA公司的开户信息
	@Override
	public List<UserInfo> findTACompanyOpenAccoInfo(String id_kind_gb, String id_no, String ta_no) throws Exception {
		List<UserInfo> uiList = new ArrayList<UserInfo>();
		UserInfo ui = new UserInfo();
		ui.setId_kind_gb(id_kind_gb);
		ui.setId_no(id_no);
		ui.setTa_no(ta_no);
		uiList = userInfoMapper.findTACompanyOpenAccoInfo(ui);
		return uiList;
	}

	@Override
	public List<UserInfo> findUserInfo(String bank_no, String bank_account, String id_no) throws Exception {
		List<UserInfo> uiList = new ArrayList<UserInfo>();
		UserInfo ui = new UserInfo();
		ui.setId_no(id_no);
		ui.setBankCode(bank_no);
		ui.setBankAccount(bank_account);
		uiList = userInfoMapper.findUserInfo(ui);
		return uiList;
	}

	@Override
	public List<UserInfo> findUserInfoByClientId(String client_id) throws Exception {
		List<UserInfo> uiList = new ArrayList<UserInfo>();
		UserInfo ui = new UserInfo();
		ui.setClient_id(client_id);
		uiList = userInfoMapper.findUserInfoByClientId(ui);
		return uiList;
	}

	@Override
	public List<UserInfo> findDistinctBankAccount(String client_id) throws Exception {
		List<UserInfo> uiList = new ArrayList<UserInfo>();
		UserInfo ui = new UserInfo();
		ui.setClient_id(client_id);
		uiList = userInfoMapper.findDistinctBankAccount(ui);
		return uiList;
	}

	@Override
	public void insertAccountEmail(AccountEmail email) throws Exception {
		userInfoMapper.insertAccountEmail(email);
	}

	@Override
	public List<String> findAccountEmailDistinctIdNo(String applydate) throws Exception {
		List<String> idNoList = new ArrayList<String>(); 
		return idNoList = userInfoMapper.findAccountEmailDistinctIdNo(applydate);
	}

	@Override
	public List<AccountEmail> findAccountEmailByApplyDateAndIdNo(AccountEmail query) throws Exception {
		List<AccountEmail> list = new ArrayList<AccountEmail>();
		list = userInfoMapper.findAccountEmailByApplyDateAndIdNo(query);
		return list;
	}

	@Override
	public String findAccountEmailLastestEmail(AccountEmail query) throws Exception {
		String email = "";
		return email = userInfoMapper.findAccountEmailLastestEmail(query);
	}

	@Override
	public List<AccountEmail> findAccountEmailRecordList(String qryDate) throws Exception {
		List<AccountEmail> list = new ArrayList<AccountEmail>();
		list = userInfoMapper.findAccountEmailRecordList(qryDate);
		return list;
	}

	@Override
	public List<TradeEmail> findTradeEmailRecordList(String affirmDate) throws Exception {
		List<TradeEmail> list = new ArrayList<TradeEmail>();
		list = userInfoMapper.findTradeEmailRecordList(affirmDate);
		return list;
	}

	@Override
	public void insertTradeEmail(TradeEmail email) throws Exception {
		// TODO Auto-generated method stub
		userInfoMapper.insertTradeEmail(email);
	}

	@Override
	public List<String> findTradeEmailDistinctTradeAcco(String affirmDate) throws Exception {
		List<String> list = new ArrayList<String>(); 
		return list = userInfoMapper.findTradeEmailDistinctTradeAcco(affirmDate);
	}

	@Override
	public PersonInfo findUserTradeByTradeAcco(String string) throws Exception {
		PersonInfo per = userInfoMapper.findUserTradeByTradeAcco(string);
		return per;
	}

	@Override
	public void insertUserTrade(PersonInfo pi) throws Exception {
		// TODO Auto-generated method stub
		userInfoMapper.insertUserTrade(pi);
	}

	@Override
	public List<String> findTradeEmailUserTradeDistinctIdNo(String affirmDate) throws Exception {
		List<String> list = new ArrayList<String>(); 
		return list = userInfoMapper.findTradeEmailUserTradeDistinctIdNo(affirmDate);
	}

	@Override
	public List<TradeEmail> findTradeEmailByAffirmDateAndIdNo(TradeEmail query) throws Exception {
		List<TradeEmail> list = new ArrayList<TradeEmail>();
		list = userInfoMapper.findTradeEmailByAffirmDateAndIdNo(query);
		return list;
	}

	@Override
	public List<TradeMessage> findTradeMessageRecordList(TradeMessage query) throws Exception {
		List<TradeMessage> list = new ArrayList<TradeMessage>();
		list = userInfoMapper.findTradeMessageRecordList(query);
		return list;
	}

	@Override
	public void insertTradeMessage(TradeMessage msg) throws Exception {
		// TODO Auto-generated method stub
		userInfoMapper.insertTradeMessage(msg);
	}

	@Override
	public List<String> findTradeMessageDistinctTradeAcco(TradeMessage query) throws Exception {
		List<String> list = new ArrayList<String>(); 
		return list = userInfoMapper.findTradeMessageDistinctTradeAcco(query);
	}

	@Override
	public PersonInfo findUserTradeMessageByTradeAcco(String tradeAcco) throws Exception {
		PersonInfo per = userInfoMapper.findUserTradeMessageByTradeAcco(tradeAcco);
		return per;
	}

	@Override
	public void insertUserTradeMessage(PersonInfo pi) throws Exception {
		// TODO Auto-generated method stub
		userInfoMapper.insertUserTradeMessage(pi);
	}

	@Override
	public List<String> findTradeMessageUserTradeMessageDistinctIdNo(TradeMessage query) throws Exception {
		List<String> idNoList = new ArrayList<String>(); 
		return idNoList = userInfoMapper.findTradeMessageUserTradeMessageDistinctIdNo(query);
	}

	@Override
	public List<TradeMessage> findTradeMessageByAffirmDateAndFundBusinCodeAndIdNo(TradeMessage query) throws Exception {
		List<TradeMessage> list = new ArrayList<TradeMessage>();
		list = userInfoMapper.findTradeMessageByAffirmDateAndFundBusinCodeAndIdNo(query);
		return list;
	}

	@Override
	public void deleteAccountEmailRecordList(String qryDate) {
		userInfoMapper.deleteAccountEmailRecordList(qryDate);
	}

	@Override
	public void deleteTradeEmailRecordList(String affirmDate) {
		userInfoMapper.deleteTradeEmailRecordList(affirmDate);
	}

	@Override
	public void deleteTradeMessageRecordList(TradeMessage query) {
		userInfoMapper.deleteTradeMessageRecordList(query);
		
	}

	@Override
	public void deleteUserTradeMessageByTradeAcco(String tradeAcco) {
		userInfoMapper.deleteUserTradeMessageByTradeAcco(tradeAcco);
		
	}

	@Override
	public void deleteUserTradeByTradeAcco(String tradeAcco) {
		userInfoMapper.deleteUserTradeByTradeAcco(tradeAcco);
		
	}

	@Override
	public int deleteUserInfoByClientId(String client_id) {
		
		return userInfoMapper.deleteUserInfoByClientId(client_id);
	}

}
