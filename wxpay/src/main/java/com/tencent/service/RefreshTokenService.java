package com.tencent.service;

import com.tencent.common.JSONParse;
import com.tencent.protocol.refreshToken_protocol.RefreshTokenReqData;
import com.tencent.protocol.refreshToken_protocol.RefreshTokenResData;
import com.uugty.app.config.Configure;

public class RefreshTokenService extends BaseService {

	public RefreshTokenService() throws IllegalAccessException,
			InstantiationException, ClassNotFoundException {
		super(Configure.UNIFIEDORDER_API);
	}

	public RefreshTokenResData request(RefreshTokenReqData refreshTokenReqData)
			throws Exception {

		// --------------------------------------------------------------------
		// 发送HTTPS的Get请求到API地址
		// --------------------------------------------------------------------
		String apiURL = Configure.refresh_token_url + "?appid="
				+ refreshTokenReqData.getAppid() + "&grant_type="
				+ refreshTokenReqData.getGrant_type() + "&refresh_token="
				+ refreshTokenReqData.getRefresh_token();
		String responseString = sendGet(apiURL);

		Object resData = JSONParse.getResDataFromJSON(responseString,
				RefreshTokenResData.class);

		return (RefreshTokenResData) resData;
	}

}
