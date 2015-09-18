package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.EasemobUtil;

/**
 * @ClassName: EasemobUserServlet
 * @Description: 获取到当前用户的环信好友
 * @author ganliang
 * @date 2015年8月6日 下午2:32:34
 */
@WebServlet(urlPatterns = { "/easemobUser.do" }, asyncSupported = false)
public class EasemobUserServlet extends HttpServlet {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 5567482583019376653L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 封装
		TUser sessionUser = WebUtil.getUserFromSession(request);
		IUserService userService = new UserServiceImpl();

		// 找到用户的好友
		String userId = sessionUser.getUserId();
		String friends = EasemobUtil.getFriends(userId);

		JSONObject friendsJSON = JSONObject.fromObject(friends);
		JSONArray jsonArray = friendsJSON.getJSONArray("data");
		if (jsonArray.size() > 0) {
			List<Object> userIds = new ArrayList<Object>();
			for (Object json : jsonArray) {
				if (!easemobList.contains(json.toString())) {
					userIds.add(json);
				}
			}
			// 根据用户id 来获取到环信的好友信息
			List<Object> entity = userService.getEasemobUser(userIds);
			ResponseEntity.printlns(response, entity);
		} else {
			ResponseEntity.println(response);
		}
	}

	/** 环信系统账号 不需要查看这个人的用户信息 */
	private List<String> easemobList = new ArrayList<String>();

	@Override
	public void init() throws ServletException {
		easemobList.add("admin");
	}
}
