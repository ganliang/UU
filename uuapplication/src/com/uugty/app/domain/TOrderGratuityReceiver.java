package com.uugty.app.domain;

import java.io.Serializable;

public class TOrderGratuityReceiver implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -2013362894436207694L;

	private int receiverId;// 红包接受者主键
	private int gratuityId;// 红包id
	private String receiverUserId;// 接受红包的用户id
	private String receiverDate;// 红包接受的时间

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getReceiverId() {
		return receiverId;
	}

	public int getGratuityId() {
		return gratuityId;
	}

	public void setGratuityId(int gratuityId) {
		this.gratuityId = gratuityId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverDate(String receiverDate) {
		this.receiverDate = receiverDate;
	}

	public String getReceiverDate() {
		return receiverDate;
	}
}
