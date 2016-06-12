package com.goldfinance.jinGC.entity;

/**
 * 申购or认购时的信息
 * @author liumj
 *
 */
public class PurchaseInfo {
	private String tradeType;   //a 申购，  s认购
	private String secuCode;    //基金代码
	private String fundName;  //基金名称
	private String trade_acco;
	private String password;
	private String bankInfo;   //银行拼接信息即  bankCode|bankAccount
	private String displayedBankInfo;  //用户页面显示的银行信息即银行简称和星形账号 bankAbbrName starLikeBankAccount
	private String bankCode;
	private String money;   //涉及金额
	private String riskEvaluationName;   //
	private String deduct_status;   //扣款状态
	
	public String getDeduct_status() {
		return deduct_status;
	}
	public void setDeduct_status(String deduct_status) {
		this.deduct_status = deduct_status;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getSecuCode() {
		return secuCode;
	}
	public void setSecuCode(String secuCode) {
		this.secuCode = secuCode;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getBankInfo() {
		return bankInfo;
	}
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getDisplayedBankInfo() {
		return displayedBankInfo;
	}
	public void setDisplayedBankInfo(String displayedBankInfo) {
		this.displayedBankInfo = displayedBankInfo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getTrade_acco() {
		return trade_acco;
	}
	public void setTrade_acco(String trade_acco) {
		this.trade_acco = trade_acco;
	}
	
	public String getRiskEvaluationName() {
		return riskEvaluationName;
	}
	public void setRiskEvaluationName(String riskEvaluationName) {
		this.riskEvaluationName = riskEvaluationName;
	}
	@Override
	public String toString() {
		return "PurchaseInfo [tradeType=" + tradeType + ", secuCode="
				+ secuCode + ", fundName=" + fundName + ", trade_acco="
				+ trade_acco + ", password=" + password + ", bankInfo="
				+ bankInfo + ", displayedBankInfo=" + displayedBankInfo
				+ ", bankCode=" + bankCode + ", money=" + money
				+ ", riskEvaluationName=" + riskEvaluationName + "]";
	}

}
