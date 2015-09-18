package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TTempUser
 * @Description: 临客
 * @author ganliang
 * @date 2015年7月9日 上午9:39:45
 */
public class TTempUser implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = -1854346680285129529L;

	private int tempId;// 临客id
	private String tempUuid;// 临客的手机uuid
	private String tempShout;// 临客大喊一声
	private Date tempCreateDate;// 临客登录的时间

	public int getTempId() {
		return tempId;
	}

	public void setTempId(int tempId) {
		this.tempId = tempId;
	}

	public String getTempUuid() {
		return tempUuid;
	}

	public void setTempUuid(String tempUuid) {
		this.tempUuid = tempUuid;
	}

	public String getTempShout() {
		return tempShout;
	}

	public void setTempShout(String tempShout) {
		this.tempShout = tempShout;
	}

	public Date getTempCreateDate() {
		return tempCreateDate;
	}

	public void setTempCreateDate(Date tempCreateDate) {
		this.tempCreateDate = tempCreateDate;
	}

}
