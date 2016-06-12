package com.goldfinance.jinGC.entity;
/**
 * 
 * @author Administrator
 *
 */
public class JcbFund {
	private String fundcode;
	private String fundname;
	public String getFundcode() {
		return fundcode;
	}
	public void setFundcode(String fundcode) {
		this.fundcode = fundcode;
	}
	public String getFundname() {
		return fundname;
	}
	public void setFundname(String fundname) {
		this.fundname = fundname;
	}
	@Override
	public String toString() {
		return "JcbFund [fundcode=" + fundcode + ", fundname=" + fundname + "]";
	}

}
