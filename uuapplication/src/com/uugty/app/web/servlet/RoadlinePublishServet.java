package com.uugty.app.web.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.uugty.app.constant.EncodeConstant;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.service.impl.RoadlineServiceImpl;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.form.RoadLineDescribeForm;
import com.uugty.app.web.form.RoadLineMarkForm;
import com.uugty.app.web.form.RoadLinePublishForm;

/**
 * @ClassName: RoadlinePublishServet
 * @Description: 路线发布的接口，在这个接口中把标签一起插入到数据库
 * @author ganliang
 * @date 2015年6月13日 上午10:30:24
 */
@WebServlet(urlPatterns = { "/roadlinePublish.do" }, asyncSupported = true)
public class RoadlinePublishServet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(RoadlinePublishServet.class);

	private static RoadLinePublishForm roadLinePublishForm = null;
	private static final String IOS = "ios";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		final IRoadlineService roadlineService = new RoadlineServiceImpl();

		// 根据用户id来获取该用户发布的路线
		// List<TRoadline> roadlines = roadlineService
		// .findRoadLineByUser(sessionUser);
		// // 如果用户没有发布路线
		// if (roadlines != null) {
		// ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
		// "你已经发布了路线");
		// return;
		// }

		String key = request.getParameter("key");
		String type = request.getParameter("type");
		if (IOS.equalsIgnoreCase(type)) {
			key = URLDecoder.decode(key, "UTF-8");
			key = key.replaceAll("\r|\n", "");
			key = key.replaceAll(" ", "");
			key = key.replaceAll(";", ",");
			key = key.replace("(", "[");
			key = key.replace(")", "]");
			key = key.replace("\r\n", "");
			// key = key.replace(",}", "}");
			key = key.replace("{", "{\"");
			key = key.replace("=", "\":");
			key = key.replace(",", ",\"");
			key = key.replace(",\"}", "}");
			key = key.replace(",\"{", ",{");
		}
		roadLinePublishForm = parseContentFromString(key);
		// 异步保存路线信息
		new Thread(new Runnable() {
			@Override
			public void run() {
				roadlineService.saveRoadLine(roadLinePublishForm, sessionUser);
			}
		}).start();
		ResponseEntity.println(response);

	}

	public static RoadLinePublishForm parseContentFromString(String json) {

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
		if (StringUtil.isNotEmpty(json)) {
			roadLinePublishForm = new RoadLinePublishForm();
			roadlineJSON = JSONObject.fromObject(json);
			roadLinePublishForm.setRoadlineTitle(roadlineJSON
					.getString("roadlineTitle"));
			String price = roadlineJSON.getString("roadlinePrice");
			roadLinePublishForm.setRoadlinePrice((price != null && !""
					.equals(price)) ? Float.parseFloat(price) : 0);
			roadLinePublishForm.setRoadlineContent(roadlineJSON
					.getString("roadlineContent"));
			roadLinePublishForm.setRoadlineGoalArea(roadlineJSON
					.getString("roadlineGoalArea"));
			roadLinePublishForm.setRoadlineDays(roadlineJSON
					.getString("roadlineDays"));
			roadLinePublishForm.setRoadlineBackground(roadlineJSON
					.getString("roadlineBackground"));
			Object roadlineId = roadlineJSON.get("roadlineId");
			if (roadlineId != null && !"".equals(roadlineId.toString())) {
				roadLinePublishForm.setRoadlineId(Integer.parseInt(roadlineId
						.toString()));
			}
			roadlineDescribes = new ArrayList<RoadLineDescribeForm>();
			roadlineDescribesJSON = roadlineJSON
					.getJSONArray("roadlineDescribes");
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
							.getJSONArray("describeMarks");
					if (roadlineMarksJSON != null
							&& roadlineMarksJSON.size() > 0) {
						for (int j = 0; j < roadlineMarksJSON.size(); j++) {
							roadLineMarkForm = new RoadLineMarkForm();
							roadlineMarkJSON = roadlineMarksJSON
									.getJSONObject(j);
							roadLineMarkForm.setMarkContent(roadlineMarkJSON
									.getString("markContent"));
							Object markX = roadlineMarkJSON.get("markX");
							if (markX != null && !"".equals(markX.toString())) {
								roadLineMarkForm.setMarkX(Double
										.parseDouble(markX.toString()));
							}
							Object markY = roadlineMarkJSON.get("markY");
							if (markY != null && !"".equals(markY.toString())) {
								roadLineMarkForm.setMarkY(Double
										.parseDouble(markY.toString()));
							}
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

	/**
	 * @Title: parseContent
	 * @Description: 解析json数据,封装到对象中去
	 * @param @param in
	 * @param @return
	 * @return RoadLinePublishForm 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private RoadLinePublishForm parseContentFromInputStream(InputStream in) {
		StringBuilder builder = new StringBuilder();
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(in));
			String readline = null;
			while ((readline = buffer.readLine()) != null) {
				builder.append(readline);
			}
		} catch (IOException e) {
			log.error("解析异常", e);
		} finally {
			try {
				in.close();
				buffer.close();
			} catch (IOException e) {
				log.error("流关闭异常", e);
			}
		}

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
			String json = builder.deleteCharAt(0).toString();
			try {
				System.out.println(json);
				json = URLDecoder.decode(json, EncodeConstant.UTF8);
				System.out.println(json);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			roadLinePublishForm = new RoadLinePublishForm();
			roadlineJSON = JSONObject.fromObject(json);
			roadLinePublishForm.setRoadlineTitle(roadlineJSON
					.getString("roadlineTitle"));
			String price = roadlineJSON.getString("roadlinePrice");
			roadLinePublishForm.setRoadlinePrice((price != null && !""
					.equals(price)) ? Float.parseFloat(price) : 0);
			roadLinePublishForm.setRoadlineContent(roadlineJSON
					.getString("roadlineContent"));
			roadLinePublishForm.setRoadlineGoalArea(roadlineJSON
					.getString("roadlineGoalArea"));
			roadLinePublishForm.setRoadlineDays(roadlineJSON
					.getString("roadlineDays"));
			roadlineDescribes = new ArrayList<RoadLineDescribeForm>();
			roadlineDescribesJSON = roadlineJSON
					.getJSONArray("roadlineDescribes");
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
							.getJSONArray("describeMarks");
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
}