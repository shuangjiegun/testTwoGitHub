package com.goldfinance.jinGC.po;
/**
 * 
 * @author liuhui
 *	份额查询请求
 */
public class ShareRequ {
	
	/**
	 * 请求行数
	 */
	private String request_num;
	/**
	 *重新统计总记录数标志 
	 */
	private String reqry_recordsum_flag;
	/**
	 * 查询起始行号
	 */
	private String qry_beginrownum;
	/**
	 * 返回排序方式
	 */
	private String sort_direction;
	/**
	 * 交易账号
	 */
	private String trade_acco;
	/**
	 * TA账号
	 */
	private String ta_acco;
	/**
	 * 客户编号
	 */
	private String client_id;
	/**
	 * 基金代码
	 */
	private String fund_code;
	
	/**
	 * 份额分类
	 */
	private String share_type;
	
	/**
	 * 银行代码
	 */
	private String bank_no;
	
	/**
	 * 基金类型
	 */
	private String ofund_type;

	public String getRequest_num() {
		return request_num;
	}

	public void setRequest_num(String request_num) {
		this.request_num = request_num;
	}

	public String getReqry_recordsum_flag() {
		return reqry_recordsum_flag;
	}

	public void setReqry_recordsum_flag(String reqry_recordsum_flag) {
		this.reqry_recordsum_flag = reqry_recordsum_flag;
	}

	public String getQry_beginrownum() {
		return qry_beginrownum;
	}

	public void setQry_beginrownum(String qry_beginrownum) {
		this.qry_beginrownum = qry_beginrownum;
	}

	public String getSort_direction() {
		return sort_direction;
	}

	public void setSort_direction(String sort_direction) {
		this.sort_direction = sort_direction;
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

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getFund_code() {
		return fund_code;
	}

	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}

	public String getShare_type() {
		return share_type;
	}

	public void setShare_type(String share_type) {
		this.share_type = share_type;
	}

	public String getBank_no() {
		return bank_no;
	}

	public void setBank_no(String bank_no) {
		this.bank_no = bank_no;
	}

	public String getOfund_type() {
		return ofund_type;
	}

	public void setOfund_type(String ofund_type) {
		this.ofund_type = ofund_type;
	}

	@Override
	public String toString() {
		return "ShareRequ [request_num=" + request_num
				+ ", reqry_recordsum_flag=" + reqry_recordsum_flag
				+ ", qry_beginrownum=" + qry_beginrownum + ", sort_direction="
				+ sort_direction + ", trade_acco=" + trade_acco + ", ta_acco="
				+ ta_acco + ", client_id=" + client_id + ", fund_code="
				+ fund_code + ", share_type=" + share_type + ", bank_no="
				+ bank_no + ", ofund_type=" + ofund_type + "]";
	}
	
	
}
