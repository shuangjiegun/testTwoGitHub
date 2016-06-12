package com.goldfinance.jinGC.entity;

import java.util.Date;

public class EarningsTrend {
	private String secuCode;
	private int innerCode;
	private Float unitNV;  //单位净值
	private Float accumulatedUnitNV;  //单位累计净值
	private Float nVDailyGrowthRate;  //日回报率（%）
	private Date enddate;
	private int pageNumber;
	private static int pageSize=10;
	public Float getUnitNV() {
		return unitNV;
	}
	public void setUnitNV(Float unitNV) {
		this.unitNV = unitNV;
	}
	public Float getAccumulatedUnitNV() {
		return accumulatedUnitNV;
	}
	public void setAccumulatedUnitNV(Float accumulatedUnitNV) {
		this.accumulatedUnitNV = accumulatedUnitNV;
	}
	public Float getnVDailyGrowthRate() {
		return nVDailyGrowthRate;
	}
	public void setnVDailyGrowthRate(Float nVDailyGrowthRate) {
		this.nVDailyGrowthRate = nVDailyGrowthRate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public static int getPageSize() {
		return pageSize;
	}
	public static void setPageSize(int pageSize) {
		EarningsTrend.pageSize = pageSize;
	}
	
	public String getSecuCode() {
		return secuCode;
	}
	public void setSecuCode(String secuCode) {
		this.secuCode = secuCode;
	}
	

	public int getInnerCode() {
		return innerCode;
	}
	public void setInnerCode(int innerCode) {
		this.innerCode = innerCode;
	}
	@Override
	public String toString() {
		return "EarningsTrend [secuCode=" + secuCode + ", innerCode="
				+ innerCode + ", unitNV=" + unitNV + ", accumulatedUnitNV="
				+ accumulatedUnitNV + ", nVDailyGrowthRate="
				+ nVDailyGrowthRate + ", enddate=" + enddate + ", pageNumber="
				+ pageNumber + "]";
	}



}
