package com.uugty.app.web.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.LoginEntity;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.service.IUserService;
import com.uugty.app.service.impl.CommentServiceImpl;
import com.uugty.app.service.impl.UserServiceImpl;
import com.uugty.app.utils.BeanUtil;
import com.uugty.app.utils.Page;
import com.uugty.app.utils.PropertiesUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.utils.DownloadWexinHeadImgUtil;
import com.uugty.app.web.utils.EasemobUtil;
import com.uugty.app.web.utils.WXPayUtil;

/**
 * 
 * @ClassName: WXLoginServlet
 * @Description: 微信登录 根据微信的账号和密码来登录uu平台
 * @author ganliang
 * @date 2015年6月6日 下午3:47:41
 */
@WebServlet(urlPatterns = { "/security/userWeChartLogin.do" }, asyncSupported = false, loadOnStartup = 9)
public class UserWeChartLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 8014959381884403178L;
	private IUserService userService = new UserServiceImpl();
	private ICommentService commentService = new CommentServiceImpl();

	private static final Logger LOG = Logger
			.getLogger(UserWeChartLoginServlet.class);

	// private static final String init_password = "123456";

	private static String IMAGEURI = "";// 图片的地址
	private static String FILEUPLOAD_WEXIN_HEADIMG_DEST = "";// 微信头像上传

	@Override
	public void init(ServletConfig config) throws ServletException {
		Properties properties = PropertiesUtil.getProperties("file.properties");
		File dir = null;
		// 头像
		IMAGEURI = properties.getProperty("images_uri");

		FILEUPLOAD_WEXIN_HEADIMG_DEST = properties
				.getProperty("fileupload_wexin_headimg_dest");
		if (FILEUPLOAD_WEXIN_HEADIMG_DEST != null) {
			dir = new File(IMAGEURI + FILEUPLOAD_WEXIN_HEADIMG_DEST);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		LOG.info("微信头像上传路径------>>>>" + dir.getAbsolutePath());
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		TUser user = (TUser) BeanUtil.populate(request, TUser.class);
		List<Object> comments = null;
		LoginEntity entity = null;

		String access_token = user.getAccess_token();
		String openid = user.getOpenid();
		// 根据oepnid 来查看数据库是否存在这条数据
		TUser weChartUser = userService.weChartLogin(user);
		// 数据库不存在这条数据
		if (weChartUser == null) {
			// 调用微信服务器 来获取到这个用户的个人信息
			UserInfoResData requestUserInfo = WXPayUtil.requestUserInfo(
					access_token, openid);
			int errcode = requestUserInfo.getErrcode();
			// 获取用户信息失败 返回失败原因
			if (errcode > 0) {
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						requestUserInfo.getErrmsg());
				return;
			}
			// 调用成功 将微信信息写入到数据库中,登陆 且将登陆更新到数据库
			user.setWxOpenid(requestUserInfo.getOpenid());
			user.setWxCity(requestUserInfo.getCity());
			user.setWxCountry(requestUserInfo.getCountry());
			user.setWxNickname(requestUserInfo.getNickname());
			user.setWxHeadimgurl(requestUserInfo.getHeadimgurl());
			user.setWxPrivilege(requestUserInfo.getPrivilege());
			user.setWxProvince(requestUserInfo.getProvince());
			user.setWxSex(String.valueOf(requestUserInfo.getSex()));
			user.setWxUnionid(requestUserInfo.getUnionid());
			user.setUserCity(requestUserInfo.getCity());
			user.setUserArea(requestUserInfo.getCountry());
			String headImg = StringUtil.getFileName();
			headImg = FILEUPLOAD_WEXIN_HEADIMG_DEST + headImg + ".jpg";
			user.setUserAvatar(headImg);
			user.setUserSex(requestUserInfo.getSex());
			user.setUserName(requestUserInfo.getNickname());
			user.setUserId(StringUtil.getUUID());
			user.setUserRegisterDate(new Date());
			user.setUserLastLoginDate(new Date());
			user.setUserLoginCount(1);
			user.setUserIsLogin(1);
			user.setUserLoginType(2);
			user.setUserStatus("1");
			String easemobPassword = EasemobUtil.getEasemobPassword(user
					.getUserId());
			user.setUserEasemobPassword(easemobPassword);

			// 将用户信息保存到session作用于中去
			WebUtil.putUserToSession(request, weChartUser);

			weChartUser = userService.saveUser(user);

			// 注册环信用户
			EasemobUtil.createIMUser(user.getUserId(), easemobPassword);
			// 环信添加系统用户
			EasemobUtil.addFriendSingle(user.getUserId());
			// 下载 微信头像 保存微信头像到本地磁盘(开启一个线程)
			new DownloadWexinHeadImgUtil(IMAGEURI + headImg,
					requestUserInfo.getHeadimgurl()).start();

			entity = (LoginEntity) BeanUtil.setPropertys(weChartUser,
					LoginEntity.class);
		}
		// 如果已经使用微信登录过，那么同步登录消息 如 最后登录时间，最后登录的经度，维度,经度
		else {
			weChartUser.setUserIsLogin(1);
			weChartUser.setUserLoginCount(weChartUser.getUserLoginCount() + 1);
			weChartUser.setUserLoginType(2);
			weChartUser.setUserStatus("1");
			weChartUser.setUserLastLoginDate(new Date());

			// 将用户信息保存到session作用于中去
			WebUtil.putUserToSession(request, weChartUser);

			weChartUser = userService.updateUser(weChartUser);

			// 获取该用户被评论的信息
			Page page = new Page(2, 1);
			comments = commentService.getCommentsByCommentedUserId(
					weChartUser.getUserId(), page);
			entity = userService.getLoginUserMessage(weChartUser.getUserId());
		}
		ResponseEntity.println(response, entity, comments);
	}
}
