package com.tencent.service;

import com.tencent.common.JSONParse;
import com.tencent.protocol.auth_protocol.AuthReqData;
import com.tencent.protocol.auth_protocol.AuthResData;
import com.uugty.app.config.Configure;

public class AuthService extends BaseService {

	public AuthService() throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		super("");
	}

	public AuthResData request(AuthReqData authReqData) throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Get请求到API地址
		// --------------------------------------------------------------------
		String apiURL = Configure.auth_url + "?access_token="
				+ authReqData.getAccess_token() + "&openid="
				+ authReqData.getOpenid();
		String responseString = sendGet(apiURL);

		Object authResData = JSONParse.getResDataFromJSON(responseString,
				AuthResData.class);

		return (AuthResData) authResData;
	}
}
