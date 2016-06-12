package com.goldfinance.jinGC.entity;
/**
 * 
 * @author liuhui
 *
 */
public class FundBuy {
/**
 * 
 */
	private String fundid;
	private String userid;
	private String fundshares;
	private String issuccess;
	private String fundname;
	private String applytime;
	
	public String getApplytime() {
		return applytime;
	}
	public void setApplytime(String applytime) {
		this.applytime = applytime;
	}
	public String getFundname() {
		return fundname;
	}
	public void setFundname(String fundname) {
		this.fundname = fundname;
	}
	public String getFundid() {
		return fundid;
	}
	public void setFundid(String fundid) {
		this.fundid = fundid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFundshares() {
		return fundshares;
	}
	public void setFundshares(String fundshares) {
		this.fundshares = fundshares;
	}
	public String getIssuccess() {
		return issuccess;
	}
	public void setIssuccess(String issuccess) {
		this.issuccess = issuccess;
	}
	@Override
	public String toString() {
		return "FundBuy [fundid=" + fundid + ", userid=" + userid
				+ ", fundshares=" + fundshares + ", issuccess=" + issuccess
				+ ", fundname=" + fundname + ", apply_time=" + applytime + "]";
	}
	
}
