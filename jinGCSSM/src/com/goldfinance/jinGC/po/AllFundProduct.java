package com.goldfinance.jinGC.po;

import java.util.Date;

public class AllFundProduct {
	private	String	ofund_type	;
	private	String	fund_status	;
	private	String	fund_name	;
	private	String	ta_no	;
	private	String	fund_code	;
	private	String	rowcount	;
	private	String	total_count	;
	private	String	success_type	;
	private	String	error_code	;
	private	String	error_info	;
	private String quanpinyin;
	private String initialpinyin;
	private	int	flag	;
	private	Date	 createdatetime	;
	public String getOfund_type() {
		return ofund_type;
	}
	public void setOfund_type(String ofund_type) {
		this.ofund_type = ofund_type;
	}
	public String getFund_status() {
		return fund_status;
	}
	public void setFund_status(String fund_status) {
		this.fund_status = fund_status;
	}
	public String getFund_name() {
		return fund_name;
	}
	public void setFund_name(String fund_name) {
		this.fund_name = fund_name;
	}
	public String getTa_no() {
		return ta_no;
	}
	public void setTa_no(String ta_no) {
		this.ta_no = ta_no;
	}
	public String getFund_code() {
		return fund_code;
	}
	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}
	public String getRowcount() {
		return rowcount;
	}
	public void setRowcount(String rowcount) {
		this.rowcount = rowcount;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getSuccess_type() {
		return success_type;
	}
	public void setSuccess_type(String success_type) {
		this.success_type = success_type;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public String getQuanpinyin() {
		return quanpinyin;
	}
	public void setQuanpinyin(String quanpinyin) {
		this.quanpinyin = quanpinyin;
	}
	public String getInitialpinyin() {
		return initialpinyin;
	}
	public void setInitialpinyin(String initialpinyin) {
		this.initialpinyin = initialpinyin;
	}
	@Override
	public String toString() {
		return "AllFundProduct [ofund_type=" + ofund_type + ", fund_status="
				+ fund_status + ", fund_name=" + fund_name + ", ta_no=" + ta_no
				+ ", fund_code=" + fund_code + ", rowcount=" + rowcount
				+ ", total_count=" + total_count + ", success_type="
				+ success_type + ", error_code=" + error_code + ", error_info="
				+ error_info + ", quanpinyin=" + quanpinyin
				+ ", initialpinyin=" + initialpinyin + ", flag=" + flag
				+ ", createdatetime=" + createdatetime + "]";
	}

}
