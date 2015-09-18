package com.uugty.app.service;

import java.util.List;

import com.uugty.app.domain.TCollectRoadline;
import com.uugty.app.domain.TMark;
import com.uugty.app.domain.TRoadline;
import com.uugty.app.domain.TUser;
import com.uugty.app.web.form.RoadLinePublishForm;
import com.uugty.app.web.servlet.RoadlineModifyServet.ModifyRoadlineEntity;

public interface IRoadlineService {

	public String saveRoadLine(RoadLinePublishForm roadLinePublishForm,
			TUser sessionUser);

	public List<TRoadline> findRoadLineByUser(TUser sessionUser);

	public void roadlineDelete(TRoadline roadline);

	public List<RoadLinePublishForm> findAllRoadLine(TUser sessionUser);

	public TRoadline getRoadlineById(int orderRoadlineId);

	public List<Object> searchRoadlineList(TMark mark);

	public RoadLinePublishForm getRoadlineDetailById(int roadlineId);

	/*
	 * 路线收藏
	 */
	public void deleteCollectRoadlineById(int collectId);

	public int saveCollectRoadline(TCollectRoadline collectRoadline);

	public List<Object> getCollectRoadlineByUserId(String userId);

	public boolean getRoadlineReviewedById(int roadlineId);

	public void deleteRoadlineByUserId(String userId);

	public ModifyRoadlineEntity getRoadlineReviewedByUserId(String userId);

	public void dropRoadlineByUserId(int roadlineId);

}
