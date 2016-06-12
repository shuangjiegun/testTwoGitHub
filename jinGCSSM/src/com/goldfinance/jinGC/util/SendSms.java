package com.goldfinance.jinGC.util;

import java.util.Date;


public class SendSms {
	private Integer serialNo;//主键
	private String serviceID;//服务类型
	private String sMContent;//短信类型
	private String sendTarget;//目标地址
	private Integer priority;//优先级
	private Date rcompleteTimeBegin;//发送起始日期
	private Date rcompleteTimeEnd;//发送结束日期
	private Integer  rcompleteHourBegin;//发送开始时间
	private Integer rcompleteHourEnd;//发送结束时间
	private Date requestTime;//请求时间
	private Integer roadby;//路由
	private String sendTargetDesc;//发送描述
	private Integer feeValue;//计费条数
	private String linkID;//链接id
	private String Pad1;
	private String Pad2;
	private String Pad3;
	private String Pad4;
	private String Pad5;
	
	public SendSms() {
		super();
	}
	
	public void init(){
		this.rcompleteHourBegin = Constant.SMS_RCOMPLETE_HOUR_BEGIN;
		this.rcompleteHourEnd = Constant.SMS_RCOMPLETE_HOUR_END;
		this.roadby = Constant.SMS_ROADBY;
		this.serviceID = Constant.SMS_SSRVICE_ID;
		this.priority = Constant.SMS_PRIORITY;
		Date date = new Date();
		this.rcompleteTimeBegin = date;
		this.rcompleteTimeEnd = date;
		this.requestTime = date;
		this.sendTargetDesc = "易胜宝发送短信";
	}
	
	
	public Integer getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}
	public String getServiceID() {
		return serviceID;
	}
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}
	public String getsMContent() {
		return sMContent;
	}
	public void setsMContent(String sMContent) {
		this.sMContent = sMContent;
	}
	public String getSendTarget() {
		return sendTarget;
	}
	public void setSendTarget(String sendTarget) {
		this.sendTarget = sendTarget;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getRcompleteTimeBegin() {
		return rcompleteTimeBegin;
	}
	public void setRcompleteTimeBegin(Date rcompleteTimeBegin) {
		this.rcompleteTimeBegin = rcompleteTimeBegin;
	}
	public Date getRcompleteTimeEnd() {
		return rcompleteTimeEnd;
	}
	public void setRcompleteTimeEnd(Date rcompleteTimeEnd) {
		this.rcompleteTimeEnd = rcompleteTimeEnd;
	}
	public Integer getRcompleteHourBegin() {
		return rcompleteHourBegin;
	}
	public void setRcompleteHourBegin(Integer rcompleteHourBegin) {
		this.rcompleteHourBegin = rcompleteHourBegin;
	}
	public Integer getRcompleteHourEnd() {
		return rcompleteHourEnd;
	}
	public void setRcompleteHourEnd(Integer rcompleteHourEnd) {
		this.rcompleteHourEnd = rcompleteHourEnd;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Integer getRoadby() {
		return roadby;
	}
	public void setRoadby(Integer roadby) {
		this.roadby = roadby;
	}
	public String getSendTargetDesc() {
		return sendTargetDesc;
	}
	public void setSendTargetDesc(String sendTargetDesc) {
		this.sendTargetDesc = sendTargetDesc;
	}
	public Integer getFeeValue() {
		return feeValue;
	}
	public void setFeeValue(Integer feeValue) {
		this.feeValue = feeValue;
	}
	public String getLinkID() {
		return linkID;
	}
	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}
	public String getPad1() {
		return Pad1;
	}
	public void setPad1(String pad1) {
		Pad1 = pad1;
	}
	public String getPad2() {
		return Pad2;
	}
	public void setPad2(String pad2) {
		Pad2 = pad2;
	}
	public String getPad3() {
		return Pad3;
	}
	public void setPad3(String pad3) {
		Pad3 = pad3;
	}
	public String getPad4() {
		return Pad4;
	}
	public void setPad4(String pad4) {
		Pad4 = pad4;
	}
	public String getPad5() {
		return Pad5;
	}
	public void setPad5(String pad5) {
		Pad5 = pad5;
	}

}
