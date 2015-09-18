package com.uugty.app.web.servlet;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.utils.PropertiesUtil;

/**
 * @ClassName: GetPrivateTokenKeyServlet
 * @Description: 获取私钥
 * @author ganliang
 * @date 2015年6月17日 下午5:21:37
 */
@WebServlet(urlPatterns = { "/getPrivateTokenKey.do" }, asyncSupported = false)
public class GetPrivateTokenKeyServlet extends HttpServlet {

	private static final long serialVersionUID = 5120471038247489153L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Properties properties = PropertiesUtil
				.getProperties("system.properties");
		String privateKey = properties.getProperty("private_key");
		ResponseEntity.println(response, new PrivateTokenKey(privateKey));
	}

	public static class PrivateTokenKey {

		private String privateKey;

		public PrivateTokenKey(String privateKey) {
			super();
			this.privateKey = privateKey;
		}

		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}

	}
}
