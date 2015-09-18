package com.uugty.app.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: TAttention
 * @Description: 收藏路线的实体
 * @author ganliang
 * @date 2015年6月13日 下午2:11:37
 */
public class TCollectRoadline implements Serializable {

	private static final long serialVersionUID = 7753165033811412874L;

	private int collectId;// 关注的主键
	private String collectUserId;// 关注的用户id
	private String collectRoadlineId;// 被关注的用户id
	private String collectTitle;// 被关注的用户id
	private Date collectCreateDate;// 关注创建的日期

	public int getCollectId() {
		return collectId;
	}

	public void setCollectId(int collectId) {
		this.collectId = collectId;
	}

	public String getCollectUserId() {
		return collectUserId;
	}

	public void setCollectUserId(String collectUserId) {
		this.collectUserId = collectUserId;
	}

	public String getCollectRoadlineId() {
		return collectRoadlineId;
	}

	public void setCollectRoadlineId(String collectRoadlineId) {
		this.collectRoadlineId = collectRoadlineId;
	}

	public String getCollectTitle() {
		return collectTitle;
	}

	public void setCollectTitle(String collectTitle) {
		this.collectTitle = collectTitle;
	}

	public Date getCollectCreateDate() {
		return collectCreateDate;
	}

	public void setCollectCreateDate(Date collectCreateDate) {
		this.collectCreateDate = collectCreateDate;
	}
}
