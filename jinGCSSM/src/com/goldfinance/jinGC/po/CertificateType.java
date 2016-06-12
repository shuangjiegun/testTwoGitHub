package com.goldfinance.jinGC.po;

public class CertificateType {
	private int id;
	private String certificateName;
	private String certificateCode;
	private int flag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCertificateName() {
		return certificateName;
	}
	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}
	public String getCertificateCode() {
		return certificateCode;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "CertificateType [id=" + id + ", certificateName="
				+ certificateName + ", certificateCode=" + certificateCode
				+ ", flag=" + flag + "]";
	}
	

}
