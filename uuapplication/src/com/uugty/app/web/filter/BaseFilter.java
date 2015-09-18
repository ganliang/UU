package com.uugty.app.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.uugty.app.constant.EncodeConstant;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.log.AccessLog;
import com.uugty.app.log.LogUtil;
import com.uugty.app.utils.WebUtil;
import com.uugty.app.web.wrap.MyHttpRequestWrapper;

/**
 * 
 * @ClassName: PageEncodingAndExceptionFilter
 * @Description: 设置服务器的编码，和在这里统一处理异常bug
 * @Description: 次拦截器只拦截servlet
 * @author ganliang
 * @date 2015年6月12日 上午9:47:17
 */
@WebFilter(filterName = "filter0", urlPatterns = { "*.do", "*.jsp" }, asyncSupported = true)
public class BaseFilter implements Filter {

	private Logger LOG = Logger.getLogger(BaseFilter.class);

	private static List<String> actions = new ArrayList<String>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// 强转
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 编码
		request.setCharacterEncoding(EncodeConstant.UTF8);
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding(EncodeConstant.UTF8);
		// 将每次servlet的请求都接口名称,参数都保存到日志文件中去

		LogUtil.log(request);
		LOG.info("sessionID---->>>>" + request.getSession().getId());

		// 记录访问日志
		AccessLog.write(request);

		// 拦截请求，用户未登录就不允许登录
		String servletPath = request.getServletPath();
		TUser userFromSession = WebUtil.getUserFromSession(request);
		if (userFromSession == null && !actions.contains(servletPath)) {
			LOG.warn("用户未登录!");
			// request.getSession().setAttribute("no_login", new Object());
			ResponseEntity.println(response, ResponseEntity.NO_LOGIN, "用户未登录！");
			return;
		}

//		// session 过期
//		if (!actions.contains(servletPath)) {
//			if (SessionUtil.isExpire(request)) {
//				ResponseEntity.println(response, ResponseEntity.NO_LOGIN,
//						"用户过期！");
//				return;
//			}
//		}
		if (userFromSession != null) {
			LOG.info("userTel---->>>>" + userFromSession.getUserTel());
		}
		// 异常处理
		try {
			MyHttpRequestWrapper myrequest = new MyHttpRequestWrapper(request);
			myrequest.setResponse(response);
			chain.doFilter(myrequest, response);
			// chain.doFilter(request, response);
		} catch (Exception e) {
			LOG.error("服务器出现异常,请查看日志!", e);
			ResponseEntity.println(response, ResponseEntity.ERROR_STATUS,
					"SERVICE ERROR");
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		/**
		 * 不用登陆的接口
		 */
		actions.add("/security/userWeChartLogin.do");
		actions.add("/security/userRegister.do");
		actions.add("/security/userLogin.do");

		actions.add("/userVerificationCode.do");

		actions.add("/roadlineMarkIndex.do");
		actions.add("/roadlineSearch.do");
		actions.add("/roadlineDetailMessage.do");
		actions.add("/userDetailMessage.do");// 获取用户详情

		actions.add("/userTempLogin.do");// 临客登录
		actions.add("/userCheckVersion.do");// 检测版本号
		actions.add("/userForgetPassword.do");// 用户忘记密码

		actions.add("/weixin_notify_url.jsp");// 微信支付回调
		actions.add("/userDownload.do");// 用户下载
		actions.add("/recharge.do");// 测试充钱

		actions.add("/index.jsp");
		actions.add("/test.do");
	}
}
