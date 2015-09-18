package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.uugty.app.constant.StringConstant;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.DateUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;

/**
 * 
 * @ClassName: UserModifyMessageServlet
 * @Description: 用户编辑修改信息
 * @author ganliang
 * @date 2015年6月11日 下午5:51:04
 */
@WebServlet(urlPatterns = { "/userModifyMessage.do" }, asyncSupported = true)
public class UserModifyMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 4081048013399129969L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 获取封装数据
		final TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		TUser sessionUser = (TUser) WebUtil.getUserFromSession(request);
		String userId = sessionUser.getUserId();
		user.setUserId(userId);
		switch (user.getType()) {
		// 改变用户名称
		case TUser.CHANGE_USER_NAME:
			user.setUserName(user.getContent());
			break;
		// 改变用户的语言
		case TUser.CHANGE_USER_LANGUAGE:
			user.setUserLanguage(user.getContent());
			break;
		// 改变用户的性别
		case TUser.CHANGE_USER_SEX:
			user.setUserSex(Integer.parseInt(user.getContent()));
			break;
		// 改变用户的星座
		case TUser.CHANGE_USER_CONSTELLATIO:
			user.setUserConstellation(user.getContent());
			break;
		// 改变用户的生日
		case TUser.CHANGE_USER_BIRTHDAY:
			Date birthday = DateUtil.shortDateFormat(user.getContent());
			user.setUserBirthday(birthday);
			user.setUserPost(StringUtil.getUserBirthdayPost(birthday));
			break;
		// 改变用户的额邮箱
		case TUser.CHANGE_USER_EMAIL:
			user.setUserEmail(user.getContent());
			break;
		// 验证手机号码
		case TUser.VALIDATE_USER_TEL:
			String content = user.getContent();
			if (content != null) {
				String[] con = content.split(",");
				String userTel = con[0];
				String veryCode = con[1];
				String verycodeFromSession = WebUtil
						.getVerycodeFromSession(request);
				if (veryCode.equals(verycodeFromSession)) {
					user.setUserTel(userTel);
					user.setUserTelValidate(1);
					user.setUserTelValidateDate(new Date());
				} else {
					ResponseEntity.println(response,
							ResponseEntity.WARN_STATUS, "验证码不正确");
					return;
				}
			}
			break;
		// 修改个人说明
		case TUser.CHANGE_USER_DESCRIPTION:
			user.setUserDescription(user.getContent());
			break;
		// 修改城市
		case TUser.CHANGE_USER_CITY:
			user.setUserCity(user.getContent());
			break;
		// 修改学校
		case TUser.CHANGE_USER_SCHOOL:
			user.setUserSchool(user.getContent());
			break;
		// 修改工作
		case TUser.CHANGE_USER_WORK:
			user.setUserWork(user.getContent());
			break;
		// 修改生活照
		case TUser.CHANGE_USER_LIFE_PHOTO:
			String photo = sessionUser.getUserLifePhoto();
			String photo2 = user.getUserLifePhoto();
			if (StringUtil.isNotEmpty(photo) && StringUtil.isNotEmpty(photo2)) {
				String[] photos = photo.split(StringConstant.QUOTA);
				String[] photos2 = photo2.split(StringConstant.QUOTA);
				StringBuilder builder = new StringBuilder("  ");
				for (String p : photos) {
					for (String p2 : photos2) {
						if (!p.trim().equals(p2)) {
							builder.append(p + StringConstant.QUOTA);
						}
					}
				}
				int lastIndexOf = builder.lastIndexOf(StringConstant.QUOTA);
				if (lastIndexOf != -1) {
					builder.deleteCharAt(lastIndexOf);
				}
				user.setUserLifePhoto(builder.toString());
			}
			break;
		// 切换用户身份
		case TUser.SWITCH_USER_STATUS:
			user.setUserStatus(user.getContent());
			break;
		// 修改经纬度
		case TUser.MODIFY_LNG_LAT:
			String lngLat = user.getContent();
			JSONObject json = JSONObject.fromObject(lngLat);
			user.setUserLastLoginLat(Float.parseFloat(json
					.getString("userLastLoginLat")));
			user.setUserLastLoginLng(Float.parseFloat(json
					.getString("userLastLoginLng")));
			break;
		default:
			break;
		}
		// 更新session域中的值
		BeanUtil.setPropertys(user, sessionUser);
		WebUtil.putUserToSession(request, sessionUser);
		// 异步更新数据
		new Thread(new Runnable() {
			@Override
			public void run() {
				IUserService userService = new UserServiceImpl();
				userService.updateUser(user);
			}
		}).start();
		ResponseEntity.println(response);
	}
}
