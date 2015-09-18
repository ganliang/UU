package com.uugty.app.entity;

/**
 * @ClassName: UserVersionEntity
 * @Description:返回实体
 * @author ganliang
 * @date 2015年6月16日 下午2:20:04
 */
public class UserVersionEntity {

	private String STRATERY;

	private String CURRVERSION;

	private String REDIRECTLOCATION;

	public UserVersionEntity() {
		super();
	}

	public UserVersionEntity(String sTRATERY, String cURRVERSION,
			String rEDIRECTLOCATION) {
		super();
		STRATERY = sTRATERY;
		CURRVERSION = cURRVERSION;
		REDIRECTLOCATION = rEDIRECTLOCATION;
	}

	public String getSTRATERY() {
		return STRATERY;
	}

	public void setSTRATERY(String sTRATERY) {
		STRATERY = sTRATERY;
	}

	public String getCURRVERSION() {
		return CURRVERSION;
	}

	public void setCURRVERSION(String cURRVERSION) {
		CURRVERSION = cURRVERSION;
	}

	public String getREDIRECTLOCATION() {
		return REDIRECTLOCATION;
	}

	public void setREDIRECTLOCATION(String rEDIRECTLOCATION) {
		REDIRECTLOCATION = rEDIRECTLOCATION;
	}
}
