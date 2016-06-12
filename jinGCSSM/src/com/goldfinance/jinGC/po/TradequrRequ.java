package com.goldfinance.jinGC.po;
/**
 * 
 * @author liuhui
 * 资金 申购查询
 *
 */
public class TradequrRequ {
	/**
	 * 
	 */
	private String trust_way;
	/**
	 * 
	 */
	private String reqry_recordsum_flag;
	/**
	 * 
	 */
	private String qry_beginrownum;
	/**
	 * 
	 */
	private String sort_direction;
	/**
	 * 
	 */
	private String trade_acco;
	/**
	 * 
	 */
	private String ta_acco;
	/**
	 * 
	 */
	private String client_id;
	/**
	 * 
	 */
	private String request_num;
	/**
	 * 
	 */
	private String fund_code;
	/**
	 * 
	 */
	private String fund_busin_code;
	/**
	 * 
	 */
	private String begin_date;
	/**
	 * 
	 */
	private String end_date;
	/**
	 * 
	 */
	private String allot_no;
	/**
	 * 
	 */
	private String taconfirm_flag;
	/**
	 * 
	 */
	private String deduct_status;
	/**
	 * 
	 */
	private String original_appno;
	/**
	 * 
	 */
	private String busin_board_type;
	/**
	 * 
	 */
	private String protocol_traffic_flag;
	
	/**
	 * 
	 */
	private String target_fund_code;

	public String getTrust_way() {
		return trust_way;
	}

	public void setTrust_way(String trust_way) {
		this.trust_way = trust_way;
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

	public String getRequest_num() {
		return request_num;
	}

	public void setRequest_num(String request_num) {
		this.request_num = request_num;
	}

	public String getFund_code() {
		return fund_code;
	}

	public void setFund_code(String fund_code) {
		this.fund_code = fund_code;
	}

	public String getFund_busin_code() {
		return fund_busin_code;
	}

	public void setFund_busin_code(String fund_busin_code) {
		this.fund_busin_code = fund_busin_code;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getAllot_no() {
		return allot_no;
	}

	public void setAllot_no(String allot_no) {
		this.allot_no = allot_no;
	}

	public String getTaconfirm_flag() {
		return taconfirm_flag;
	}

	public void setTaconfirm_flag(String taconfirm_flag) {
		this.taconfirm_flag = taconfirm_flag;
	}

	public String getDeduct_status() {
		return deduct_status;
	}

	public void setDeduct_status(String deduct_status) {
		this.deduct_status = deduct_status;
	}

	public String getOriginal_appno() {
		return original_appno;
	}

	public void setOriginal_appno(String original_appno) {
		this.original_appno = original_appno;
	}

	public String getBusin_board_type() {
		return busin_board_type;
	}

	public void setBusin_board_type(String busin_board_type) {
		this.busin_board_type = busin_board_type;
	}

	public String getProtocol_traffic_flag() {
		return protocol_traffic_flag;
	}

	public void setProtocol_traffic_flag(String protocol_traffic_flag) {
		this.protocol_traffic_flag = protocol_traffic_flag;
	}

	public String getTarget_fund_code() {
		return target_fund_code;
	}

	public void setTarget_fund_code(String target_fund_code) {
		this.target_fund_code = target_fund_code;
	}

	@Override
	public String toString() {
		return "Tradequr [trust_way=" + trust_way + ", reqry_recordsum_flag="
				+ reqry_recordsum_flag + ", qry_beginrownum=" + qry_beginrownum
				+ ", sort_direction=" + sort_direction + ", trade_acco="
				+ trade_acco + ", ta_acco=" + ta_acco + ", client_id="
				+ client_id + ", request_num=" + request_num + ", fund_code="
				+ fund_code + ", fund_busin_code=" + fund_busin_code
				+ ", begin_date=" + begin_date + ", end_date=" + end_date
				+ ", allot_no=" + allot_no + ", taconfirm_flag="
				+ taconfirm_flag + ", deduct_status=" + deduct_status
				+ ", original_appno=" + original_appno + ", busin_board_type="
				+ busin_board_type + ", protocol_traffic_flag="
				+ protocol_traffic_flag + ", target_fund_code="
				+ target_fund_code + "]";
	}
	
	
	

}
