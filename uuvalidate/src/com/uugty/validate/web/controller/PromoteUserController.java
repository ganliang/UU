package com.uugty.validate.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.zxing.WriterException;
import com.uugty.validate.domain.PromoteUser;
import com.uugty.validate.service.IPromoteUserService;
import com.uugty.validate.utils.APKLinuxUtil;
import com.uugty.validate.utils.APKWindowUtil;
import com.uugty.validate.utils.Page;
import com.uugty.validate.utils.QRCodeUtil;

@Controller("PromoteUserController")
@RequestMapping("/promote")
public class PromoteUserController {
	@Resource(name = IPromoteUserService.SERVER_NAME)
	private IPromoteUserService promoteUserService;

	/** 获取所有的推广人员 */
	@RequestMapping("/getAllPromoteUser")
	public ModelAndView getAllPromoteUser(PromoteUser promoteUser,
			Map<String, Object> model) {
		int totalSize = promoteUserService.findAllPromoteUserCount(promoteUser);
		Page page = new Page(totalSize, promoteUser.getCurrentPage());
		List<PromoteUser> promoteUserList = promoteUserService
				.findAllPromoteUser(promoteUser, page);
		model.put("promoteUserList", promoteUserList);
		model.put("page", page);
		return new ModelAndView("promote/showAllPromoteUser", model);
	}

	/** 进入添加推广人员页面 */
	@RequestMapping("/toAddPromoteUser")
	public ModelAndView toAddPromoteUser(PromoteUser promoteUser,
			Map<String, Object> model) {
		return new ModelAndView("promote/toAddPromoteUser", model);
	}

	/**
	 * 添加推广人员
	 * 
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping("/addPromoteUser")
	public synchronized ModelAndView addPromoteUser(PromoteUser promoteUser,
			Map<String, Object> model) throws WriterException, IOException {

		String promoteUserId = UUID.randomUUID().toString().replace("-", "");
		// 生成二维码
		String qrCode = QRCodeUtil.encode(promoteUserId);
		// 打包apk文件
		String system = System.getProperty("os.name");
		String apkDest = "";
		if (system.contains("Windows")) {
			apkDest = APKWindowUtil.getAPK(promoteUserId);
		} else {
			apkDest = APKLinuxUtil.getAPK(promoteUserId);
		}
		// 将数据存入到数据库
		promoteUser.setQrCode(qrCode);
		promoteUser.setApkDest(apkDest);
		promoteUser.setPromoteUserId(promoteUserId);

		promoteUserService.savePromoteUser(promoteUser);

		return getAllPromoteUser(promoteUser, model);
	}

	/**
	 * 更新二维码 apk
	 * 
	 * @throws IOException
	 * @throws WriterException
	 */
	@RequestMapping("/refresh")
	public ModelAndView refresh(PromoteUser promoteUser,
			Map<String, Object> model) throws WriterException, IOException {
		String promoteUserId = promoteUser.getPromoteUserId();
		// 生成二维码
		String qrCode = QRCodeUtil.encode(promoteUserId);
		// 打包apk文件
		String system = System.getProperty("os.name");
		String apkDest = "";
		if (system.contains("Windows")) {
			apkDest = APKWindowUtil.getAPK(promoteUserId);
		} else {
			apkDest = APKLinuxUtil.getAPK(promoteUserId);
		}
		// 将数据更新到数据库
		promoteUser.setQrCode(qrCode);
		promoteUser.setApkDest(apkDest);

		promoteUserService.updatePromoteUser(promoteUser);

		return getAllPromoteUser(promoteUser, model);
	}

	@RequestMapping("/showImage.do")
	public ModelAndView showImage(String imageURL, Map<String, Object> model) {
		model.put("imageURL", imageURL);
		return new ModelAndView("promote/showImage", model);
	}
}
