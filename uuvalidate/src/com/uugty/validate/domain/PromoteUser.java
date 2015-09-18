package com.uugty.validate.domain;

import java.io.Serializable;
import java.util.Date;

public class PromoteUser implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */

	private static final long serialVersionUID = 5239280522472624680L;
	private String promoteUserId;
	private String promoteUserName;
	private Date createDate;
	private Date lastRegisterDate;
	private Date lastDownloadDate;
	private int registerCount;
	private int downloadCount;

	private String qrCode;
	private String apkDest;

	public String getPromoteUserId() {
		return promoteUserId;
	}

	public void setPromoteUserId(String promoteUserId) {
		this.promoteUserId = promoteUserId;
	}

	public String getPromoteUserName() {
		return promoteUserName;
	}

	public void setPromoteUserName(String promoteUserName) {
		this.promoteUserName = promoteUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastRegisterDate() {
		return lastRegisterDate;
	}

	public void setLastRegisterDate(Date lastRegisterDate) {
		this.lastRegisterDate = lastRegisterDate;
	}

	public Date getLastDownloadDate() {
		return lastDownloadDate;
	}

	public void setLastDownloadDate(Date lastDownloadDate) {
		this.lastDownloadDate = lastDownloadDate;
	}

	public int getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(int registerCount) {
		this.registerCount = registerCount;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getApkDest() {
		return apkDest;
	}

	public void setApkDest(String apkDest) {
		this.apkDest = apkDest;
	}

	/**
	 * 非持久化字段
	 */
	public int currentPage = 1;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
