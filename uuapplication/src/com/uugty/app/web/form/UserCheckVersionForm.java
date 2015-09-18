package com.uugty.app.web.form;

public class UserCheckVersionForm {

	private String clientVersion;// 客户端端的版本号
	private String osType;// 操作系统的类型

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}
}
