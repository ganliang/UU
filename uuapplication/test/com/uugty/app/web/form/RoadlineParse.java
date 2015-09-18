package com.uugty.app.web.form;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.uugty.app.utils.StringUtil;

public class RoadlineParse {

	public static RoadLinePublishForm parseContent(String builder) {
		List<RoadLineDescribeForm> roadlineDescribes = null;
		List<RoadLineMarkForm> roadlineMarks = null;

		JSONObject roadlineJSON = null;
		JSONObject roadlinDescribeJSON = null;
		JSONObject roadlineMarkJSON = null;

		RoadLinePublishForm roadLinePublishForm = null;
		RoadLineDescribeForm roadLineDescribeForm = null;
		RoadLineMarkForm roadLineMarkForm = null;

		JSONArray roadlineDescribesJSON = null;
		JSONArray roadlineMarksJSON = null;

		if (StringUtil.isNotEmpty(builder.toString())) {
			roadLinePublishForm = new RoadLinePublishForm();
			roadlineJSON = JSONObject.fromObject(builder.toString());
			roadLinePublishForm.setRoadlineTitle(roadlineJSON
					.getString("roadlineTitle"));
			roadLinePublishForm.setRoadlinePrice(roadlineJSON
					.getDouble("roadlinePrice"));
			roadLinePublishForm.setRoadlineContent(roadlineJSON
					.getString("roadlineContent"));
			roadLinePublishForm.setRoadlineGoalArea(roadlineJSON
					.getString("roadlineGoalArea"));

			roadlineDescribes = new ArrayList<RoadLineDescribeForm>();
			roadlineDescribesJSON = roadlineJSON
					.getJSONArray("roadLineDecribes");
			if (roadlineDescribesJSON != null
					&& roadlineDescribesJSON.size() > 0) {
				for (int i = 0; i < roadlineDescribesJSON.size(); i++) {
					roadLineDescribeForm = new RoadLineDescribeForm();
					roadlinDescribeJSON = roadlineDescribesJSON
							.getJSONObject(i);
					roadLineDescribeForm.setDescribeImage(roadlinDescribeJSON
							.getString("describeImage"));
					roadLineDescribeForm.setDescribeTime(roadlinDescribeJSON
							.getString("describeTime"));
					roadLineDescribeForm.setDescribeArea(roadlinDescribeJSON
							.getString("describeArea"));
					roadlineMarks = new ArrayList<RoadLineMarkForm>();
					roadlineMarksJSON = roadlinDescribeJSON
							.getJSONArray("decribeMarks");
					if (roadlineMarksJSON != null
							&& roadlineMarksJSON.size() > 0) {
						for (int j = 0; j < roadlineMarksJSON.size(); j++) {
							roadLineMarkForm = new RoadLineMarkForm();
							roadlineMarkJSON = roadlineMarksJSON
									.getJSONObject(j);
							roadLineMarkForm.setMarkContent(roadlineMarkJSON
									.getString("markContent"));
							roadLineMarkForm.setMarkX(roadlineMarkJSON
									.getDouble("markX"));
							roadLineMarkForm.setMarkY(roadlineMarkJSON
									.getDouble("markY"));
							roadlineMarks.add(roadLineMarkForm);
						}
					}
					roadLineDescribeForm.setDescribeMarks(roadlineMarks);
					roadlineDescribes.add(roadLineDescribeForm);
				}
			}
			roadLinePublishForm.setRoadlineDescribes(roadlineDescribes);
		}
		return roadLinePublishForm;
	}

	public static void main(String[] args) {
		RoadLinePublishForm publishForm = new RoadLinePublishForm();
		publishForm.setRoadlineContent("roadline1");
		publishForm.setRoadlineGoalArea("郑州");
		publishForm.setRoadlinePrice(100);
		publishForm.setRoadlineTitle("爱人");
		List<RoadLineDescribeForm> roadLineDecribes = new ArrayList<RoadLineDescribeForm>();
		for (int i = 0; i < 4; i++) {
			RoadLineDescribeForm roadLineDescribeForm = new RoadLineDescribeForm();
			roadLineDescribeForm.setDescribeArea("北京" + i);
			roadLineDescribeForm.setDescribeImage("image" + i);
			roadLineDescribeForm.setDescribeTime("12:15");
			List<RoadLineMarkForm> decribeMarks = new ArrayList<RoadLineMarkForm>();
			for (int j = 0; j < 3; j++) {
				RoadLineMarkForm roadLineMarkForm = new RoadLineMarkForm();
				roadLineMarkForm.setMarkContent("标签" + i);
				roadLineMarkForm.setMarkX(12 + i);
				roadLineMarkForm.setMarkY(10 + i);
				decribeMarks.add(roadLineMarkForm);
			}
			roadLineDescribeForm.setDescribeMarks(decribeMarks);
			roadLineDecribes.add(roadLineDescribeForm);
		}
		publishForm.setRoadlineDescribes(roadLineDecribes);
		JSONObject fromObject = JSONObject.fromObject(publishForm);
		System.out.println(fromObject);

		RoadLinePublishForm parseContent = parseContent(fromObject.toString());
		parseContent.getRoadlineContent();
	}
}
