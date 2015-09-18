package com.uugty.app.domain;

import java.io.Serializable;

public class TSessionUnbound implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 715393066551476131L;

	private int id;      //主键id
	private String userId;  //用户id
	private String sessionId; //sessionId
	private String boundTime; //绑定日期
	private String unboundTime;// 解绑日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getBoundTime() {
		return boundTime;
	}

	public void setBoundTime(String boundTime) {
		this.boundTime = boundTime;
	}

	public String getUnboundTime() {
		return unboundTime;
	}

	public void setUnboundTime(String unboundTime) {
		this.unboundTime = unboundTime;
	}

}
