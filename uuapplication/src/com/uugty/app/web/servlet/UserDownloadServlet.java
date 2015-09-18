package com.uugty.app.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uugty.app.entity.ResponseEntity;
import com.uugty.app.utils.PropertiesUtil;

/**
 * @ClassName: UserDownloadServlet
 * @Description: 下载系统的sdk
 * @author ganliang
 * @date 2015年6月16日 上午11:57:24
 */
@WebServlet(urlPatterns = { "/userDownload.do" }, asyncSupported = false)
public class UserDownloadServlet extends HttpServlet {

	private String contentType = "application/x-msdownload";
	private String enc = "utf-8";

	private static final long serialVersionUID = -5075634591214584603L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/* 读取文件 */
		Properties properties = PropertiesUtil
				.getProperties("versionUpdate.properties");
		String fileDest = properties.getProperty("android_apk");
		File file = new File(fileDest);
		/* 如果文件存在 */
		try {
			if (file.exists()) {
				String filename = URLEncoder.encode(file.getName(), enc);
				// response.reset();
				response.setContentType(contentType);
				response.addHeader("Content-Disposition",
						"attachment; filename=\"" + filename + "\"");
				int fileLength = (int) file.length();
				response.setContentLength(fileLength);
				/* 如果文件长度大于0 */
				if (fileLength != 0) {
					/* 创建输入流 */
					InputStream inStream = new FileInputStream(file);
					byte[] buf = new byte[4096];
					/* 创建输出流 */
					PrintWriter writer = response.getWriter();
					int readLength;
					while (((readLength = inStream.read(buf)) != -1)) {
						writer.write(new String(buf, 0, readLength));
					}
					inStream.close();
					writer.flush();
					// writer.close();
				}
			}
		} catch (Exception e) {
			ResponseEntity.println(response, ResponseEntity.ERROR_STATUS,
					"下载出现异常！");
		}
	}
}
