package com.goldfinance.jinGC.po;

public class ApplyRequ {
	/**
	 * 交易委托方式
	 */
	private String trust_way;
	/**
	 * 交易账号
	 */
	private String trade_acco;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 申请时间
	 */
	private String apply_time;
	/**
	 * 下单日期
	 */
	private String order_date;
	/**
	 * 基金代码
	 */
	private String fund_code;
	/**
	 * 银行编号
	 */
	private String bank_no;
	/**
	 * 银行名称
	 */
	private String bank_name;
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	/**
	 * 银行账号
	 */
	private String bank_account;
	/**
	 * 份额分类
	 */
	private String share_type;
	/**
	 * 发生金额
	 */
	private String balance;
	/**
	 * 币种类别
	 */
	private String money_type;
	/**
	 * 资金方式
	 */
	private String capital_mode;
	/**
	 * 明细资金方式
	 */
	private String detail_fund_way;
	/**
	 * 业务大类
	 */
	private String busin_board_type;
	/**
	 * 交易来源
	 */
	private String trade_source;
	/**
	 * 推荐人
	 */
	private String referee;
	/**
	 * 接口类型
	 */
	private String fund_interface_type;
	/**
	 * 交易来源申请编号
	 */
	private String channel_serial_id;
	public String getTrust_way() {
		return trust_way;
	}
	public void setTrust_way(String trust_way) {
		this.trust_way = trust_way;
	}
	public String getTrade_acco() {
		return trade_acco;
	}
	public void setTrade_acco(String trade_acco) {
		this.trade_acco = trade_acco;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getFund_code() {
		return fund_code;
	}
	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}
	public String getBank_no() {
		return bank_no;
	}
	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}
	public String getBank_account() {
		return bank_account;
	}
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	public String getShare_type() {
		return share_type;
	}
	public void setShare_type(String share_type) {
		this.share_type = share_type;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getMoney_type() {
		return money_type;
	}
	public void setMoney_type(String money_type) {
		this.money_type = money_type;
	}
	public String getCapital_mode() {
		return capital_mode;
	}
	public void setCapital_mode(String capital_mode) {
		this.capital_mode = capital_mode;
	}
	public String getDetail_fund_way() {
		return detail_fund_way;
	}
	public void setDetail_fund_way(String detail_fund_way) {
		this.detail_fund_way = detail_fund_way;
	}
	public String getBusin_board_type() {
		return busin_board_type;
	}
	public void setBusin_board_type(String busin_board_type) {
		this.busin_board_type = busin_board_type;
	}
	public String getTrade_source() {
		return trade_source;
	}
	public void setTrade_source(String trade_source) {
		this.trade_source = trade_source;
	}
	public String getReferee() {
		return referee;
	}
	public void setReferee(String referee) {
		this.referee = referee;
	}
	public String getFund_interface_type() {
		return fund_interface_type;
	}
	public void setFund_interface_type(String fund_interface_type) {
		this.fund_interface_type = fund_interface_type;
	}
	public String getChannel_serial_id() {
		return channel_serial_id;
	}
	public void setChannel_serial_id(String channel_serial_id) {
		this.channel_serial_id = channel_serial_id;
	}
	@Override
	public String toString() {
		return "Apply [trust_way=" + trust_way + ", trade_acco=" + trade_acco
				+ ", password=" + password + ", apply_time=" + apply_time
				+ ", order_date=" + order_date + ", fund_code=" + fund_code
				+ ", bank_no=" + bank_no + ", bank_account=" + bank_account
				+ ", share_type=" + share_type + ", balance=" + balance
				+ ", money_type=" + money_type + ", capital_mode="
				+ capital_mode + ", detail_fund_way=" + detail_fund_way
				+ ", busin_board_type=" + busin_board_type + ", trade_source="
				+ trade_source + ", referee=" + referee
				+ ", fund_interface_type=" + fund_interface_type
				+ ", channel_serial_id=" + channel_serial_id + "]";
	}
	

}
