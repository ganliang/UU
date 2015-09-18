package com.uugty.app.domain;

import java.io.Serializable;

public class TAccessLog implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -1723245934313866193L;

	private int id;   //主键id
	private String action; //请求url
	private String parameter; //请求的参数
	private String sessionId; //当前的sessionId
	private String userId;   //当期的用户id
	private String accessTime;  //访问时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
