package com.tencent;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.tencent.protocol.accessToken_protocol.AccessTokenReqData;
import com.tencent.protocol.accessToken_protocol.AccessTokenResData;
import com.tencent.protocol.auth_protocol.AuthReqData;
import com.tencent.protocol.auth_protocol.AuthResData;
import com.tencent.protocol.paycall_protocol.PayCallReqData;
import com.tencent.protocol.paycall_protocol.PayCallResData;
import com.tencent.protocol.refreshToken_protocol.RefreshTokenReqData;
import com.tencent.protocol.refreshToken_protocol.RefreshTokenResData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderReqData;
import com.tencent.protocol.unifiedorder_protocol.UnifiedorderResData;
import com.tencent.protocol.userinfo_protocol.UserInfoReqData;
import com.tencent.protocol.userinfo_protocol.UserInfoResData;
import com.tencent.service.AccessTokenService;
import com.tencent.service.AuthService;
import com.tencent.service.PayCallService;
import com.tencent.service.RefreshTokenService;
import com.tencent.service.UnifiedorderService;
import com.tencent.service.UserInfoService;

/**
 * @ClassName: WxpayUtil
 * @Description: 微信支付接口
 * @author ganliang
 * @date 2015年7月29日 下午3:01:04
 */
public class WXPayUtil {

	public static UnifiedorderResData requestUnifiedorder(String body,
			String out_trade_no, int total_fee, String attach,
			String spbill_create_ip) {

		UnifiedorderReqData unifiedorderReqData = new UnifiedorderReqData(body,
				out_trade_no, total_fee, attach, spbill_create_ip);
		try {
			return new UnifiedorderService().request(unifiedorderReqData);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("请求统一下单服务出现异常", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("请求统一下单服务出现异常", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("请求统一下单服务出现异常", e);
		} catch (Exception e) {
			throw new RuntimeException("请求统一下单服务出现异常", e);
		}
	}

	/**
	 * @Title: requestAccessToken
	 * @Description: 通过code获取access_token的接口
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static AccessTokenResData requestAccessToken(String code) {
		AccessTokenReqData accessTokenReqData = new AccessTokenReqData(code);
		try {
			return new AccessTokenService().request(accessTokenReqData);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("请求Access_token服务出现异常", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("请求Access_token服务出现异常", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("请求Access_token服务出现异常", e);
		} catch (Exception e) {
			throw new RuntimeException("请求Access_token服务出现异常", e);
		}
	}

	public static RefreshTokenResData requestRefreshToken(String refresh_token) {
		RefreshTokenReqData refreshTokenReqData = new RefreshTokenReqData(
				refresh_token);
		try {
			return new RefreshTokenService().request(refreshTokenReqData);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("请求Refresh_token服务出现异常", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("请求Refresh_token服务出现异常", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("请求Refresh_token服务出现异常", e);
		} catch (Exception e) {
			throw new RuntimeException("请求Refresh_token服务出现异常", e);
		}
	}

	/**
	 * @Title: requestAuthAccessToken
	 * @Description: 检验授权凭证（access_token）是否有效
	 * @param @param access_token
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public static AuthResData requestAuthAccessToken(String access_token,
			String openid) {
		AuthReqData authReqData = new AuthReqData(access_token, openid);
		try {
			return new AuthService().request(authReqData);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("请求检验授权凭证服务出现异常", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("请求检验授权凭证服务出现异常", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("请求检验授权凭证服务出现异常", e);
		} catch (Exception e) {
			throw new RuntimeException("请求检验授权凭证服务出现异常", e);
		}
	}

	/**
	 * @Title: requestUserInfo
	 * @Description: 
	 *               此接口用于获取用户个人信息。开发者可通过OpenID来获取用户基本信息。特别需要注意的是，如果开发者拥有多个移动应用、网站应用和公众帐号
	 *               ，可通过获取用户基本信息中的unionid来区分用户的唯一性，因为只要是同一个微信开放平台帐号下的移动应用、
	 *               网站应用和公众帐号
	 *               ，用户的unionid是唯一的。换句话说，同一用户，对同一个微信开放平台下的不同应用，unionid是相同的。
	 * @param @param access_token
	 * @param @return
	 * @return UserInfoResData 返回类型
	 * @throws
	 */
	public static UserInfoResData requestUserInfo(String access_token,
			String openid) {
		UserInfoReqData userInfoReqData = new UserInfoReqData(access_token,
				openid);
		try {
			return new UserInfoService().request(userInfoReqData);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("请求微信用户信息服务出现异常", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("请求微信用户信息服务出现异常", e);
		} catch (InstantiationException e) {
			throw new RuntimeException("请求微信用户信息服务出现异常", e);
		} catch (Exception e) {
			throw new RuntimeException("请求微信用户信息服务出现异常", e);
		}
	}

	/**
	 * @Title: requestPayCall
	 * @Description: 将回调返回的xml解析成实体对象
	 * @param @param xml
	 * @param @return
	 * @return PayCallResData 返回类型
	 * @throws
	 */
	public PayCallResData requestPayCallResData(String xml) {
		try {
			return new PayCallService().requestPayCallResData(xml);
		} catch (InstantiationException e) {
			throw new RuntimeException("封装微信回调实体出现异常", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("封装微信回调实体出现异常", e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("封装微信回调实体出现异常", e);
		} catch (IOException e) {
			throw new RuntimeException("封装微信回调实体出现异常", e);
		} catch (SAXException e) {
			throw new RuntimeException("封装微信回调实体出现异常", e);
		}
	}

	/**
	 * @Title: requestPayCallReqData
	 * @Description: 微信支付确定回调返回的数据
	 * @param @param payCallReqData
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	public String requestPayCallReqData(PayCallReqData payCallReqData) {
		try {
			return new PayCallService().requestPayCallReqData(payCallReqData);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("返回回调出现异常", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("返回回调出现异常", e);
		}
	}
}
