package com.goldfinance.jinGC.entity;

public class ShareDetail {
	private String fundCode;
	private String enableShares;
	private String affirmDate;  //确认日
	
	public String getAffirmDate() {
		return affirmDate;
	}
	public void setAffirmDate(String affirmDate) {
		this.affirmDate = affirmDate;
	}
	public String getFundCode() {
		return fundCode;
	}
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	public String getEnableShares() {
		return enableShares;
	}
	public void setEnableShares(String enableShares) {
		this.enableShares = enableShares;
	}
	@Override
	public String toString() {
		return "ShareDetail [fundCode=" + fundCode + ", enableShares="
				+ enableShares + ", affirmDate=" + affirmDate + "]";
	}
}
