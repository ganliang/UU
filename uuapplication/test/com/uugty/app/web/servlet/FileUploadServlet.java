package com.uugty.app.web.servlet;

import net.sf.json.JSONObject;

import com.uugty.app.entity.ResponseEntity;

public class FileUploadServlet {

	public static void main(String[] args) {
		JSONObject fromObject = JSONObject.fromObject(new ResponseEntity(
				ResponseEntity.ERROR_STATUS, "文件上传出现异常"));
		System.out.println(fromObject.toString());
	}
}
