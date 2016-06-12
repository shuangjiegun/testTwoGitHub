package com.goldfinance.jinGC.po;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author liuhui
 * 用户资金 request domain
 *
 */
public class FundsRequ {
	/**
	 * 交易委托方式
	 */
	private String trust_way;
	/**
	 * 请求行数
	 */
	private String request_num;
	/**
	 * 重新统计总记录数标志
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
	 * 交易密码
	 */
	private String entrust_password;
	public String getTrust_way() {
		return trust_way;
	}
	public void setTrust_way(String trust_way) {
		this.trust_way = trust_way;
	}
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
	public String getEntrust_password() {
		return entrust_password;
	}
	public void setEntrust_password(String entrust_password) {
		this.entrust_password = entrust_password;
	}
	@Override
	public String toString() {
		return "FundsRequ [trust_way=" + trust_way + ", request_num="
				+ request_num + ", reqry_recordsum_flag="
				+ reqry_recordsum_flag + ", qry_beginrownum=" + qry_beginrownum
				+ ", sort_direction=" + sort_direction + ", trade_acco="
				+ trade_acco + ", ta_acco=" + ta_acco + ", client_id="
				+ client_id + ", entrust_password=" + entrust_password + "]";
	}
	public static void main(String[] args) throws Exception, IllegalArgumentException, InvocationTargetException {
		FundsRequ fundsRequ=new FundsRequ();
		
		//request.put("trust_way", "2"); 							//恒生言：网上委托
       // request.put("request_num", "50");  //恒生言：网上委托 手机委托
       // request.put("reqry_recordsum_flag", "0");
       // request.put("qry_beginrownum", "1");     //自定为227
       // request.put("trade_acco", "660Z00000916");
        fundsRequ.setTrust_way("2");
        fundsRequ.setRequest_num("50");
        fundsRequ.setReqry_recordsum_flag("0");
        fundsRequ.setQry_beginrownum("1");
        fundsRequ.setTrade_acco("660Z00000916");
		Class clazz=fundsRequ.getClass();
		Method[] m=clazz.getDeclaredMethods();
		Field[] f=clazz.getDeclaredFields();
		for(int i=0;i<f.length;i++){
			//System.out.println(f[i].getName());
			
		}
		for(int j=0;j<m.length;j++){
			
			if(!m[j].getName().startsWith("set")&&!m[j].getName().equals("main")&&!m[j].getName().equals("toString")){
				Object o=m[j].invoke(fundsRequ);
				System.out.println(o);
			}
			
		}
		
		
		
	}
	

}
