package com.goldfinance.jinGC.entity;

public class MyAsset {
	private 	String	tradeAcco	;	//	交易账号
	private 	String	taAcco	;	//	TA账号
	private 	String	fundCode	;	//	基金代码
	private String fundName;    //基金名称  恒生
	private 	String	capitalMode	;	//	资金方式
	private 	String	bankNo	;	//	银行代码
	private 	String	bankAccount	;	//	银行账号
	private 	String	todayIncome	;	//	每日收益
	private 	String	worthValue	;	//	市值
	private	Float	unitNV	;     //净值   聚源返回
	private	Float	nvDailyGrowthRate;	//	日回报率（%）聚源返回
	private	String	secuAbbr	; //	证券简称   聚源返回
	private String accumIncome; //累计收益
	private String enableShares; 
	private String unpaid_income; 
	public String getEnableShares() {
		return enableShares;
	}
	public void setEnableShares(String enableShares) {
		this.enableShares = enableShares;
	}
	public String getUnpaid_income() {
		return unpaid_income;
	}
	public void setUnpaid_income(String unpaid_income) {
		this.unpaid_income = unpaid_income;
	}
	public String getTradeAcco() {
		return tradeAcco;
	}
	public void setTradeAcco(String tradeAcco) {
		this.tradeAcco = tradeAcco;
	}
	public String getTaAcco() {
		return taAcco;
	}
	public void setTaAcco(String taAcco) {
		this.taAcco = taAcco;
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getCapitalMode() {
		return capitalMode;
	}
	public void setCapitalMode(String capitalMode) {
		this.capitalMode = capitalMode;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getTodayIncome() {
		return todayIncome;
	}
	public void setTodayIncome(String todayIncome) {
		this.todayIncome = todayIncome;
	}
	public String getWorthValue() {
		return worthValue;
	}
	public void setWorthValue(String worthValue) {
		this.worthValue = worthValue;
	}
	
	public Float getUnitNV() {
		return unitNV;
	}
	public void setUnitNV(Float unitNV) {
		this.unitNV = unitNV;
	}
	public Float getNvDailyGrowthRate() {
		return nvDailyGrowthRate;
	}
	public void setNvDailyGrowthRate(Float nvDailyGrowthRate) {
		this.nvDailyGrowthRate = nvDailyGrowthRate;
	}
	public String getSecuAbbr() {
		return secuAbbr;
	}
	public void setSecuAbbr(String secuAbbr) {
		this.secuAbbr = secuAbbr;
	}
	public String getAccumIncome() {
		return accumIncome;
	}
	public void setAccumIncome(String accumIncome) {
		this.accumIncome = accumIncome;
	}
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	@Override
	public String toString() {
		return "MyAsset [tradeAcco=" + tradeAcco + ", taAcco=" + taAcco
				+ ", fundCode=" + fundCode + ", fundName=" + fundName
				+ ", capitalMode=" + capitalMode + ", bankNo=" + bankNo
				+ ", bankAccount=" + bankAccount + ", todayIncome="
				+ todayIncome + ", worthValue=" + worthValue + ", unitNV="
				+ unitNV + ", nvDailyGrowthRate=" + nvDailyGrowthRate
				+ ", secuAbbr=" + secuAbbr + ", accumIncome=" + accumIncome
				+ ", enableShares=" + enableShares + ", unpaid_income="
				+ unpaid_income + "]";
	}
}
