package com.goldfinance.jinGC.entity;

/**
 * 账户类短信系统对应实体
 * @author liumj
 *
 */
public class AccountMessage {
	private String client_name;
	private String apply_date;
	private String mobile_tel;
	private String id_no;
	private String taconfirm_flag;
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public String getMobile_tel() {
		return mobile_tel;
	}
	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getTaconfirm_flag() {
		return taconfirm_flag;
	}
	public void setTaconfirm_flag(String taconfirm_flag) {
		this.taconfirm_flag = taconfirm_flag;
	}
	@Override
	public String toString() {
		return "AccountMessage [client_name=" + client_name + ", apply_date="
				+ apply_date + ", mobile_tel=" + mobile_tel + ", id_no="
				+ id_no + ", taconfirm_flag=" + taconfirm_flag + "]";
	}
}
