package com.goldfinance.jinGC.po;

public class Bank {
	private int id;
	private String bankName;
	private String bankCode;
	private String capital_mode;
	private int flag;
	private String bankAbbrName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getCapital_mode() {
		return capital_mode;
	}
	public void setCapital_mode(String capital_mode) {
		this.capital_mode = capital_mode;
	}
	
	public String getBankAbbrName() {
		return bankAbbrName;
	}
	public void setBankAbbrName(String bankAbbrName) {
		this.bankAbbrName = bankAbbrName;
	}
	@Override
	public String toString() {
		return "Bank [id=" + id + ", bankName=" + bankName + ", bankCode="
				+ bankCode + ", capital_mode=" + capital_mode + ", flag="
				+ flag + ", bankAbbrName=" + bankAbbrName + "]";
	}

}
