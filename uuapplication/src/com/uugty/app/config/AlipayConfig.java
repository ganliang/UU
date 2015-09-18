package com.uugty.app.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088511248020001";
	// 商户的私钥【经过编码】

	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK9c6utq4rKB/P+tG4ehklkwY9zhc1pUwut4XREZdye+gSZRM5WKyGI6krFh8v272N4puIYv02uY9HipYobFJVPhaUZcMf8VJbD/12304tAWjyerP25uZJv3T/1zRQK/KWcnOlr9jhFAfRxBq8kJmNynFCyRgeC4n1ug0BuMAO7BAgMBAAECgYBQskNwXdlAJ80Sosg+W72KyVfK9vwsH0ErNlItYjiQ03t+9HL2Z8zaVvXWJqq41E5xvWUqaquxocsuE1RBjEsmUL/Bnqe9RCiLraevyqFROsPtI1MjLTaOzR5HySGMbx1fS/lwKCmlJaqlLogUYf14Fq9S9GxHgsPpZWt+0QkcOQJBAOVmVEz9/9xxd2kizHpOFt8ObAmwwgJ3BMuOVSUx7nrpZdVnh3p0d0txJ2OIz8YONIVE4DZ2kr7k+ApNmflINJ8CQQDDsoVrH5dJocWmOmqOAq+1dtpdw8qE8BYHF+lD029lFe6ZaQ6Ln8/8E7mSutyFUBFOCl184uY3+Lpl9tlor8CfAkEAzIewIfVVava/wL2GEoYNXlM9gGZYlFoJrHP69m7Ockgmw17LLCMrOdhphhU1KyD/MtN2FRkjPZBolscZwWjfgQJAZoCgX27JmFiypRHd9Ao2LI9Xa1t9PUpfz1FX7h3WMBVfx1z+sGWB1CAkyFU14mIA3maRdzOFsHWIdJXqZGoCMwJASBJS78+VCvPz0YpkUhzyFqK6VSEhBh3fVOIXNquzhaEWECiS2sTNi8JVpoLCw1GdrB/iEI6DaPw1XymBM45EYg==";

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "/usr/local/tomcat/alipay/";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";

	// 卖家支付宝账号
	public static String seller_id = "2088511248020001";

	// 商户网站唯一订单号 ，支付宝合作商户网站唯一订单号。
	public static String out_trade_no = "416L1261D7699H7";

	// 服务器异步通知页面路径
	public static String notify_url = "http://221.174.22.15:9001/alipay/pc/notify_url.jsp";

	public static String return_url = "http://221.174.22.15:9001/alipay/pc/notify_url.jsp";

	public static String show_url = "http://221.174.22.15:9001/alipay/pc/notify_url.jsp";
	// 接口名称
	public static String service = "mobile.securitypay.pay";

	// 参数编码字符集
	public static String _input_charset = "utf-8";

	// 支付类型。默认值为：1（商品购买）
	public static String payment_type = "1";

	/**
	 * 未付款交易的超时时间设置未付款交易的超时时间， 一旦超时，该笔交易就会自动 被关闭。 取值范围：1m～15d。
	 * m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都 在0点关闭）。 该参数数值不接受小数点，如 1.5h，可转换为90m。
	 */
	public static String it_b_pay = "1d";

}
