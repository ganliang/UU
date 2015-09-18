package com.tencent.service;

import com.tencent.common.JSONParse;
import com.tencent.protocol.userinfo_protocol.UserInfoReqData;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import com.uugty.app.config.Configure;

public class UserInfoService extends BaseService {

	public UserInfoService() throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		super("");
	}

	public UserInfoResData request(UserInfoReqData userInfoReqData)
			throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Get请求到API地址
		// --------------------------------------------------------------------
		String apiURL = Configure.userinfo_url + "?access_token="
				+ userInfoReqData.getAccess_token() + "&openid="
				+ userInfoReqData.getOpenid();
		String responseString = sendGet(apiURL);
		System.out.println(responseString);

		Object resDataFromXml = JSONParse.getResDataFromJSON(responseString,
				UserInfoResData.class);

		return (UserInfoResData) resDataFromXml;
	}
}
