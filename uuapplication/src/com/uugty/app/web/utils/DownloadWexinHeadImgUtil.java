package com.uugty.app.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/** 下载用户的微信图像 将其保存在服务器中 设置为微信用户的默认头像   */
public class DownloadWexinHeadImgUtil extends Thread {

	private String path;
	private String url;

	private static final Logger log = Logger
			.getLogger(DownloadWexinHeadImgUtil.class);

	private static final HttpClient client = new HttpClient();

	public DownloadWexinHeadImgUtil(String path, String url) {
		super();
		this.path = path;
		this.url = url;
	}

	@Override
	public void run() {
		GetMethod method = new GetMethod(url);
		try {
			int executeMethod = client.executeMethod(method);
			if (executeMethod != HttpStatus.SC_OK) {
				System.err.println("异步下载微信头像失败");
			}
			byte[] responseBody = method.getResponseBody();

			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			out.write(responseBody);
			out.flush();
			out.close();
		} catch (HttpException e) {
			log.error("异步下载微信头像出现异常", e);
			throw new RuntimeException("异步下载微信头像出现异常", e);
		} catch (IOException e) {
			log.error("异步下载微信头像出现异常", e);
			throw new RuntimeException("异步下载微信头像出现异常", e);
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
