package com.uugty.validate.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: LayoutController
 * @Description: 布局控制器
 * @author ganliang
 * @date 2015年6月30日 上午10:02:24
 */

@Controller("layoutController")
@RequestMapping("/layout")
public class LayoutController {

	/**
	 * @Title: top
	 * @Description: 加载上边边框
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/top.do")
	public ModelAndView top() {
		return new ModelAndView("main/top");
	}

	/**
	 * @Title: left
	 * @Description: 加载左边的边框
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/left.do")
	public ModelAndView left() {
		return new ModelAndView("main/left");
	}

	/**
	 * @Title: main
	 * @Description: 加载主边框
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/main.do")
	public ModelAndView main() {
		return new ModelAndView("main/main");
	}

	/**
	 * @Title: bottom
	 * @Description: 记载下边框
	 * @param @return
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping("/bottom.do")
	public ModelAndView bottom() {
		return new ModelAndView("main/bottom");
	}
}
