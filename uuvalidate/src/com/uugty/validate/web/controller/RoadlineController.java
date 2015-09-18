package com.uugty.validate.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.Mark;
import com.uugty.validate.domain.Roadline;
import com.uugty.validate.service.IMarkService;
import com.uugty.validate.service.IRoadlineService;
import com.uugty.validate.utils.Page;
import com.uugty.validate.web.util.EasemobUtil;

@Controller("roadlineController")
@RequestMapping("/roadline")
public class RoadlineController {
	@Resource(name = IRoadlineService.SERVER_NAME)
	private IRoadlineService roadlineService;
	@Resource(name = IMarkService.SERVER_NAME)
	private IMarkService markService;

	/**
	 * @Title: top
	 * @Description: 显示要审核的路线
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/showAllRoadline.do")
	public ModelAndView showAllRoadline(Roadline roadline,
			Map<String, Object> model) {

		int totalSize = roadlineService.findAllRoadlineCount(roadline);
		Page page = new Page(totalSize, roadline.getCurrentPage());
		List<Roadline> roadlineList = roadlineService.findAllRoadline(roadline,
				page);
		model.put("roadlineList", roadlineList);
		model.put("roadlineStatus", roadline.getRoadlineStatus());
		model.put("page", page);
		return new ModelAndView("roadline/showAllRoadline", model);
	}

	/**
	 * @Title: roadlineAdopt
	 * @Description:
	 * @param @param roadline
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/roadlineAdopt")
	public ModelAndView roadlineAdopt(Roadline roadline,
			Map<String, Object> model) {
		roadline.setStatus("success");
		roadlineService.dropRoadline(roadline);
		EasemobUtil.sendMessage(roadline.getUserId(), "你发布的路线已经通过审核！");
		return showAllRoadline(roadline, model);
	}

	/**
	 * @Title: roadlineAdopt
	 * @Description:
	 * @param @param roadline
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/roadlineReject")
	public ModelAndView roadlineReject(Roadline roadline,
			Map<String, Object> model) {
		roadline.setStatus("failure");
		roadlineService.dropRoadline(roadline);
		EasemobUtil.sendMessage(roadline.getUserId(), "你发布的路线未通过审核！");
		return showAllRoadline(roadline, model);
	}

	/**
	 * @Title: showDescribeImages
	 * @Description: 显示图片
	 * @param @param roadline
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/showDescribeImages")
	public ModelAndView showDescribeImages(Roadline roadline,
			Map<String, Object> model) {
		String describeImages = roadline.getDescribeImages();

		List<String> list = new ArrayList<String>();
		if (describeImages != null && !"".equals(roadline.getDescribeImages())) {
			String[] describeImage = describeImages.split(",");
			for (String image : describeImage) {
				list.add("http://www.uugty.com:100/images/roadlineDescribe/"
						+ image);
			}
		}
		model.put("describeImages", list);
		return new ModelAndView("roadline/showDescribeImages", model);
	}

	@RequestMapping("/roadlineCheck")
	public ModelAndView roadlineCheck(Roadline roadline,
			Map<String, Object> model) {
		model.put("roadline", roadline);
		return new ModelAndView("roadline/roadlineCheck", model);
	}

	/**
	 * @Title: validateCheck
	 * @Description: 路线审核
	 * @param @param roadline
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/validateCheck")
	public ModelAndView validateCheck(Roadline roadline,
			Map<String, Object> model) {
		switch (roadline.getType()) {
		case "审核成功":
			roadline.setStatus("success");
			String roadlineIsHot = getRoadlineIsHot(roadline);
			roadline.setRoadlineIsHot(roadlineIsHot);
			EasemobUtil.sendMessage(roadline.getUserId(), "你发布的路线已经通过审核！");
			break;
		case "审核失败":
			roadline.setStatus("failure");
			EasemobUtil.sendMessage(roadline.getUserId(), "你发布的路线未通过审核！");
			break;
		}
		roadlineService.dropRoadline(roadline);

		roadline.setRoadlineStatus("review");
		return showAllRoadline(roadline, model);
	}

	private String getRoadlineIsHot(Roadline roadline) {
		
		roadline=roadlineService.findRoadlineById(roadline);
		
		String roadlineTitle = roadline.getRoadlineTitle();
		String roadlineGoalArea = roadline.getRoadlineGoalArea();
		String roadlineContent = roadline.getRoadlineContent();
		/** 获取标签的数据 */
		List<Mark> markList = markService.getAllMark();

		List<Integer> args = new ArrayList<Integer>();
		boolean is_mark = false;
		String is_hot = Roadline.ROADLINE_IS_HOT_NO;
		for (Mark mark : markList) {
			is_mark = false;
			int markId = mark.getMarkId();
			String markTitle = mark.getMarkTitle();
			if (!"".equals(roadlineTitle) && roadlineTitle.contains(markTitle)) {
				is_mark = true;
				is_hot = Roadline.ROADLINE_IS_HOT_YES;
			}
			if (!"".equals(roadlineGoalArea)
					&& roadlineGoalArea.contains(markTitle)) {
				is_mark = true;
				is_hot = Roadline.ROADLINE_IS_HOT_YES;
			}
			if (!"".equals(roadlineContent)
					&& roadlineContent.contains(markTitle)) {
				is_mark = true;
				is_hot = Roadline.ROADLINE_IS_HOT_YES;
			}
			if (is_mark) {
				args.add(markId);
			}
		}
		if (args.size() > 0) {
			markService.addMarkContent(args);
		}
		return is_hot;
	}
}
