package com.goldfinance.jinGC.po;
/**
 * 
 * @author liuhui
 * 赎回响应结果类
 *
 */
public class CashBackResp {
	/**
	 * 成功标志
	 */
	private String success_type;
	/**
	 * 申请编号
	 */
	private String allot_no;
	/**
	 * 违约标志
	 */
	private String penalty_flag;
	/**
	 * 申请时间
	 */
	private String apply_time;
	/**
	 * 申请日期
	 */
	private String apply_date;
	/**
	 * 不违约总份额
	 */
	private String nodefault_total_share;
	/**
	 * 清算日期
	 */
	private String clear_date;
	/**
	 * 基金总份额
	 */
	private String fund_share;
	/**
	 * 冻结份额
	 */
	private String frozen_shares;
	/**
	 * 可用份额
	 */
	private String enable_shares;
	/**
	 * 当前日期
	 */
	private String curr_date;
	/**
	 * 当前时间
	 */
	private String curr_time;
	/**
	 * 当日总份额
	 */
	private String today_total_share;
	/**
	 * 当日冻结份额
	 */
	private String today_frozen_share;
	/**
	 * 未付收益
	 */
	private String nopay_income;
	/**
	 * 每日收益
	 */
	private String today_income;
	public String getSuccess_type() {
		return success_type;
	}
	public void setSuccess_type(String success_type) {
		this.success_type = success_type;
	}
	public String getAllot_no() {
		return allot_no;
	}
	public void setAllot_no(String allot_no) {
		this.allot_no = allot_no;
	}
	public String getPenalty_flag() {
		return penalty_flag;
	}
	public void setPenalty_flag(String penalty_flag) {
		this.penalty_flag = penalty_flag;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getApply_date() {
		return apply_date;
	}
	public void setApply_date(String apply_date) {
		this.apply_date = apply_date;
	}
	public String getNodefault_total_share() {
		return nodefault_total_share;
	}
	public void setNodefault_total_share(String nodefault_total_share) {
		this.nodefault_total_share = nodefault_total_share;
	}
	public String getClear_date() {
		return clear_date;
	}
	public void setClear_date(String clear_date) {
		this.clear_date = clear_date;
	}
	public String getFund_share() {
		return fund_share;
	}
	public void setFund_share(String fund_share) {
		this.fund_share = fund_share;
	}
	public String getFrozen_shares() {
		return frozen_shares;
	}
	public void setFrozen_shares(String frozen_shares) {
		this.frozen_shares = frozen_shares;
	}
	public String getEnable_shares() {
		return enable_shares;
	}
	public void setEnable_shares(String enable_shares) {
		this.enable_shares = enable_shares;
	}
	public String getCurr_date() {
		return curr_date;
	}
	public void setCurr_date(String curr_date) {
		this.curr_date = curr_date;
	}
	public String getCurr_time() {
		return curr_time;
	}
	public void setCurr_time(String curr_time) {
		this.curr_time = curr_time;
	}
	public String getToday_total_share() {
		return today_total_share;
	}
	public void setToday_total_share(String today_total_share) {
		this.today_total_share = today_total_share;
	}
	public String getToday_frozen_share() {
		return today_frozen_share;
	}
	public void setToday_frozen_share(String today_frozen_share) {
		this.today_frozen_share = today_frozen_share;
	}
	public String getNopay_income() {
		return nopay_income;
	}
	public void setNopay_income(String nopay_income) {
		this.nopay_income = nopay_income;
	}
	public String getToday_income() {
		return today_income;
	}
	public void setToday_income(String today_income) {
		this.today_income = today_income;
	}
	@Override
	public String toString() {
		return "CashBackResp [success_type=" + success_type + ", allot_no="
				+ allot_no + ", penalty_flag=" + penalty_flag + ", apply_time="
				+ apply_time + ", apply_date=" + apply_date
				+ ", nodefault_total_share=" + nodefault_total_share
				+ ", clear_date=" + clear_date + ", fund_share=" + fund_share
				+ ", frozen_shares=" + frozen_shares + ", enable_shares="
				+ enable_shares + ", curr_date=" + curr_date + ", curr_time="
				+ curr_time + ", today_total_share=" + today_total_share
				+ ", today_frozen_share=" + today_frozen_share
				+ ", nopay_income=" + nopay_income + ", today_income="
				+ today_income + "]";
	}
	

}
