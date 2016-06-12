package com.goldfinance.jinGC.entity;

/**
 * 交易类，短信系统 对应实体
 * @author liumj
 *
 */
public class TradeMessage {
	private String id_no;
	private String client_name;
	private String mobile_tel;
	private String apply_date; //申请日期
	private String affirm_date; //确认日期
	private String fund_code; //基金代码
	private String fund_code_name;  //基金名称
	private String target_fund_code;  //目标基金代码
	private String target_fund_code_name;  //目标基金名称
	private String trade_acco; //交易账号
	private String ta_acco; //基金账号
	private String fund_busin_code; //业务代码
	private String fund_busin_code_name;//业务名称
	private String net_value; //净值
	private String trade_confirm_balance; //交易确认金额
	private String trade_confirm_type; //交易确认份额
	private String fail_cause; //手续费
	private String taconfirm_flag; //确认标识
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getMobile_tel() {
		return mobile_tel;
	}
	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public String getAffirm_date() {
		return affirm_date;
	}
	public void setAffirm_date(String affirm_date) {
		this.affirm_date = affirm_date;
	}
	public String getFund_code() {
		return fund_code;
	}
	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}
	public String getFund_code_name() {
		return fund_code_name;
	}
	public void setFund_code_name(String fund_code_name) {
		this.fund_code_name = fund_code_name;
	}
	public String getTarget_fund_code() {
		return target_fund_code;
	}
	public void setTarget_fund_code(String target_fund_code) {
		this.target_fund_code = target_fund_code;
	}
	public String getTarget_fund_code_name() {
		return target_fund_code_name;
	}
	public void setTarget_fund_code_name(String target_fund_code_name) {
		this.target_fund_code_name = target_fund_code_name;
	}
	public String getTrade_acco() {
		return trade_acco;
	}
	public void setTrade_acco(String trade_acco) {
		this.trade_acco = trade_acco;
	}
	public String getTa_acco() {
		return ta_acco;
	}
	public void setTa_acco(String ta_acco) {
		this.ta_acco = ta_acco;
	}
	public String getFund_busin_code() {
		return fund_busin_code;
	}
	public void setFund_busin_code(String fund_busin_code) {
		this.fund_busin_code = fund_busin_code;
	}
	public String getFund_busin_code_name() {
		return fund_busin_code_name;
	}
	public void setFund_busin_code_name(String fund_busin_code_name) {
		this.fund_busin_code_name = fund_busin_code_name;
	}
	public String getNet_value() {
		return net_value;
	}
	public void setNet_value(String net_value) {
		this.net_value = net_value;
	}
	public String getTrade_confirm_balance() {
		return trade_confirm_balance;
	}
	public void setTrade_confirm_balance(String trade_confirm_balance) {
		this.trade_confirm_balance = trade_confirm_balance;
	}
	public String getTrade_confirm_type() {
		return trade_confirm_type;
	}
	public void setTrade_confirm_type(String trade_confirm_type) {
		this.trade_confirm_type = trade_confirm_type;
	}
	public String getFail_cause() {
		return fail_cause;
	}
	public void setFail_cause(String fail_cause) {
		this.fail_cause = fail_cause;
	}
	public String getTaconfirm_flag() {
		return taconfirm_flag;
	}
	public void setTaconfirm_flag(String taconfirm_flag) {
		this.taconfirm_flag = taconfirm_flag;
	}
	@Override
	public String toString() {
		return "TradeMessage [id_no=" + id_no + ", client_name=" + client_name
				+ ", mobile_tel=" + mobile_tel + ", apply_date=" + apply_date
				+ ", affirm_date=" + affirm_date + ", fund_code=" + fund_code
				+ ", fund_code_name=" + fund_code_name + ", target_fund_code="
				+ target_fund_code + ", target_fund_code_name="
				+ target_fund_code_name + ", trade_acco=" + trade_acco
				+ ", ta_acco=" + ta_acco + ", fund_busin_code="
				+ fund_busin_code + ", fund_busin_code_name="
				+ fund_busin_code_name + ", net_value=" + net_value
				+ ", trade_confirm_balance=" + trade_confirm_balance
				+ ", trade_confirm_type=" + trade_confirm_type
				+ ", fail_cause=" + fail_cause + ", taconfirm_flag="
				+ taconfirm_flag + "]";
	}
}
