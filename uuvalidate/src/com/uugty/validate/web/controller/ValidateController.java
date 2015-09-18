package com.uugty.validate.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.glassfish.hk2.utilities.reflection.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uugty.validate.domain.User;
import com.uugty.validate.service.IUserService;
import com.uugty.validate.utils.Page;
import com.uugty.validate.web.util.EasemobUtil;

/**
 * @ClassName: ValidateController
 * @Description: 验证图片的控制类
 * @author ganliang
 * @date 2015年6月30日 上午11:18:17
 */
@Controller("validateController")
@RequestMapping("/validate")
public class ValidateController {

	private static final Logger log = Logger.getLogger();
	@Resource(name = IUserService.SERVER_NAME)
	private IUserService userService;

	/**
	 * @Title: validateCertificate
	 * @Description: 跳转到身份证审核的页面,获取到所有的身份证
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/identity.do")
	public ModelAndView validateIdentity(User user, Map<String, Object> model) {
		user.setType("identity");
		user.setUserIdValidate(1);
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize, user.getCurrentPage());
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userIdValidate", 1);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		model.put("model", "身 份 证 审 核");
		return new ModelAndView("validate/identity", model);
	}

	/**
	 * @Title: validateCertificate
	 * @Description: 跳转到学历证审核的页面,并获取到所有的要审核的学历证
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/certificate.do")
	public ModelAndView validateCertificate(User user, Map<String, Object> model) {
		user.setType("certificate");
		user.setUserCertificateValidate(1);
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize, user.getCurrentPage());
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userCertificateValidate", 1);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		model.put("model", "学 历 证 审 核");
		return new ModelAndView("validate/certificate", model);
	}

	/**
	 * @Title: validateCertificate
	 * @Description: 跳转到导游证审核的页面
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/tourcard.do")
	public ModelAndView validateTourcard(User user, Map<String, Object> model) {
		user.setType("tourcard");
		user.setUserTourValidate(1);
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize, user.getCurrentPage());
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userTourValidate", 1);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		model.put("model", "导 游 证 审 核");
		return new ModelAndView("validate/tourcard", model);
	}

	/**
	 * @Title: validateCertificate
	 * @Description: 跳转到头像审核的页面
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/avatar.do")
	public ModelAndView validateAvatar(User user, Map<String, Object> model) {
		user.setType("avatar");
		user.setUserAvatarValidate(1);
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize, user.getCurrentPage());
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userAvatarValidate", 1);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		model.put("model", " 头 像 审 核   ");
		return new ModelAndView("validate/avatar", model);
	}

	/**
	 * @Title: validate
	 * @Description: 审核车辆的图片
	 * @param @param user
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/car.do")
	public ModelAndView validateCar(User user, Map<String, Object> model) {
		user.setType("car");
		user.setUserCarValidate(1);
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize, user.getCurrentPage());
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userCarValidate", 1);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		model.put("model", " 车 辆  审  核   ");
		return new ModelAndView("validate/car", model);
	}

	/**
	 * @Title: validateReject
	 * @Description:操作拒绝审核
	 * @param @param user
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/validateReject.do")
	public ModelAndView validateReject(User user, Map<String, Object> model) {
		userService.validateReject(user);
		String msg = "";
		switch (user.getType()) {
		case "identity":
			msg = "你的身份证审核已经通过！";
			break;
		case "certificate":
			msg = "你的学历证审核已经通过！";
			break;
		case "tourcard":
			msg = "你的导游证审核已经通过！";
			break;
		case "avatar":
			msg = "你的头像审核已经通过！";
			break;
		case "car":
			msg = "你的车辆审核已经通过！";
			break;
		}
		log.debug(msg);
		EasemobUtil.sendMessage(user.getUserId(), msg);
		return init(user, model);
	}

	/**
	 * @Title: validateAdopt
	 * @Description:操作同意审核
	 * @param @param user
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/validateAdopt.do")
	public ModelAndView validateAdopt(User user, Map<String, Object> model) {
		userService.validateAdopt(user);
		String msg = "";
		switch (user.getType()) {
		case "identity":
			msg = "你的身份证审核未通过！";
			break;
		case "certificate":
			msg = "你的学历证审核未通过！";
			break;
		case "tourcard":
			msg = "你的导游证审核未通过！";
			break;
		case "avatar":
			msg = "你的头像审核未通过！";
			break;
		case "car":
			msg = "你的车辆审核未通过！";
			break;
		}
		log.debug(msg);
		EasemobUtil.sendMessage(user.getUserId(), msg);
		return init(user, model);
	}

	/**
	 * 
	 * @Title: validateSearch
	 * @Description:更具条件搜索
	 * @param @param user
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/validateSearch.do")
	public ModelAndView validateSearch(User user, Map<String, Object> model) {
		return init(user, model);
	}

	/**
	 * @Title: showImage
	 * @Description: 双击放大图片
	 * @param @param imageURL
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/showImage.do")
	public ModelAndView showImage(String imageURL, Map<String, Object> model) {
		model.put("imageURL", imageURL);
		return new ModelAndView("validate/showImage", model);
	}

	/**
	 * @Title: init
	 * @Description:
	 * @param @param user
	 * @param @param model
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	private ModelAndView init(User user, Map<String, Object> model) {
		ModelAndView modelAndView = null;
		// String userName = user.getUserName();
		// if (StringUtils.isNotBlank(userName)) {
		// try {
		// userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
		// user.setUserName(userName);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// }
		int totalSize = userService.findAllUserCount(user);
		Page page = new Page(totalSize,
				user.getCurrentPage() != 0 ? user.getCurrentPage() : 1);
		List<User> userList = userService.findAllUser(user, page);
		model.put("userlist", userList);
		model.put("page", page);
		model.put("userName", user.getUserName());
		model.put("userTel", user.getUserTel());
		switch (user.getType()) {
		case "identity":
			model.put("model", "身 份 证 审 核");
			model.put("userIdValidate", user.getUserIdValidate());
			modelAndView = new ModelAndView("validate/identity", model);
			break;
		case "certificate":
			model.put("model", "学 历 证 审 核");
			model.put("userCertificateValidate",
					user.getUserCertificateValidate());
			modelAndView = new ModelAndView("validate/certificate", model);
			break;
		case "tourcard":
			model.put("model", "导 游 证 审 核");
			model.put("userTourValidate", user.getUserTourValidate());
			modelAndView = new ModelAndView("validate/tourcard", model);
			break;
		case "avatar":
			model.put("model", "头 像 审 核");
			model.put("userAvatarValidate", user.getUserAvatarValidate());
			modelAndView = new ModelAndView("validate/avatar", model);
			break;
		case "car":
			model.put("model", " 车 辆  审  核   ");
			model.put("userCarValidate", user.getUserCarValidate());
			modelAndView = new ModelAndView("validate/car", model);
			break;
		}
		return modelAndView;
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
	public ModelAndView showDescribeImages(User user, Map<String, Object> model) {
		String describeImages = user.getUserCar();

		List<String> list = new ArrayList<String>();
		if (!"".equals(describeImages)) {
			String[] describeImage = describeImages.split(",");
			for (String image : describeImage) {
				list.add("http://www.uugty.com:100/" + image);
			}
		}
		model.put("describeImages", list);
		return new ModelAndView("validate/describe", model);
	}

	/** 跳转到身份证审核页面 */
	@RequestMapping("/toIdentifyAdopt")
	public ModelAndView toIdentifyAdopt(User user, Map<String, Object> model) {
		model.put("user", user);
		return new ModelAndView("validate/identifyAdopt", model);
	}

	/** 跳转到身份证审核页面 */
	@RequestMapping("/validateIdentifyAdopt")
	public ModelAndView validateIdentifyAdopt(User user,
			Map<String, Object> model) {
		userService.validateIdentifyAdopt(user);
		return init(user, model);
	}
}
