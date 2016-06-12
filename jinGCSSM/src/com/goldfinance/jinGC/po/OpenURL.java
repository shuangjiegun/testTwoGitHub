package com.goldfinance.jinGC.po;

public class OpenURL {
	private int id;
	private String url;
	private int flag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "OpenURL [id=" + id + ", url=" + url + ", flag=" + flag + "]";
	}
}
