package com.goldfinance.jinGC.entity;

/**
 * 用户风险评测与基金风险类型对应数据实体
 * @author liumj
 *
 */
public class UserAndFund {
	private int	onOff; //当基金风险高于用户评测等级时的开关
	private int	invest_risk_tolerance; //用户风险评测等级
	private String	invest_risk_tolerance_name; //用户风险评测等级中文
	private int	risk_evaluation; //基金风险等级
	private String	risk_evaluation_name;//基金风险等级中文
	private String fundName;
	public int getOnOff() {
		return onOff;
	}
	public void setOnOff(int onOff) {
		this.onOff = onOff;
	}
	public int getInvest_risk_tolerance() {
		return invest_risk_tolerance;
	}
	public void setInvest_risk_tolerance(int invest_risk_tolerance) {
		this.invest_risk_tolerance = invest_risk_tolerance;
	}
	public String getInvest_risk_tolerance_name() {
		return invest_risk_tolerance_name;
	}
	public void setInvest_risk_tolerance_name(String invest_risk_tolerance_name) {
		this.invest_risk_tolerance_name = invest_risk_tolerance_name;
	}
	public int getRisk_evaluation() {
		return risk_evaluation;
	}
	public void setRisk_evaluation(int risk_evaluation) {
		this.risk_evaluation = risk_evaluation;
	}
	public String getRisk_evaluation_name() {
		return risk_evaluation_name;
	}
	public void setRisk_evaluation_name(String risk_evaluation_name) {
		this.risk_evaluation_name = risk_evaluation_name;
	}
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	@Override
	public String toString() {
		return "UserAndFund [onOff=" + onOff + ", invest_risk_tolerance="
				+ invest_risk_tolerance + ", invest_risk_tolerance_name="
				+ invest_risk_tolerance_name + ", risk_evaluation="
				+ risk_evaluation + ", risk_evaluation_name="
				+ risk_evaluation_name + ", fundName=" + fundName + "]";
	}


}
