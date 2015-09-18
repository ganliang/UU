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

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.utils.Token2Util;

/**
 * @ClassName: VerifyTokenFilter
 * @Description: 验证系统令牌的拦截器
 * @author ganliang "*.do", "*.jsp"
 * @date 2015年6月12日 下午2:54:06
 */
@WebFilter(filterName = "filter1", urlPatterns = { "*.do", "*.jsp" }, asyncSupported = true)
public class VerifyTokenFilter implements Filter {

	private static List<String> escapeToken = new ArrayList<String>();
	private Logger LOG = Logger.getLogger(VerifyTokenFilter.class);

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		String servletPath = request.getServletPath();
		// 验证令牌
		if (!escapeToken.contains(servletPath)) {
			if (!Token2Util.validateToken(request)) {
				LOG.info("Token error............");
				ResponseEntity.println(response, ResponseEntity.WARN_STATUS,
						"令牌错误");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		/**
		 * 不需要验证令牌
		 */
		escapeToken.add("/weixin_notify_url.jsp");// 微信回调
		escapeToken.add("/userDownload.do");// 用户下载
		escapeToken.add("/recharge.do");// 测试充钱
		escapeToken.add("/userFileupload.do");// 上传
		escapeToken.add("/userLocationSearch.do");// 路线搜索
		escapeToken.add("/test.do");// 路线搜索
		escapeToken.add("/index.jsp");// 首页
	}
}
