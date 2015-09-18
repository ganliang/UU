package com.easemob.server.uu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpsClient {
	private static String httpUrl = "https://192.168.1.113:8443/uuapplication/security/userRegister.do?veryCode=123456";
	// 客户端密钥库
	private static String sslKeyStorePath;
	private static String sslKeyStorePassword;
	private static String sslKeyStoreType;
	// 客户端信任的证书
	private static String sslTrustStore;
	private static String sslTrustStorePassword;

	public static void setUp() {
		sslKeyStorePath = "C:/mykey/server.keystore";
		sslKeyStorePassword = "123456";
		sslKeyStoreType = "JKS"; // 密钥库类型，有JKS PKCS12等
		sslTrustStore = "C:/mykey/server.keystore";
		sslTrustStorePassword = "123456";
		System.setProperty("javax.net.ssl.keyStore", sslKeyStorePath);
		System.setProperty("javax.net.ssl.keyStorePassword",
				sslKeyStorePassword);
		System.setProperty("javax.net.ssl.keyStoreType", sslKeyStoreType);
		// 设置系统参数
		System.setProperty("javax.net.ssl.trustStore", sslTrustStore);
		System.setProperty("javax.net.ssl.trustStorePassword",
				sslTrustStorePassword);
	}

	public static void testHttpsClient() {
		SSLContext sslContext = null;
		try {

			KeyStore kstore = KeyStore.getInstance("bks");
			kstore.load(new FileInputStream(sslKeyStorePath),
					sslKeyStorePassword.toCharArray());
			KeyManagerFactory keyFactory = KeyManagerFactory
					.getInstance("sunx509");
			keyFactory.init(kstore, sslKeyStorePassword.toCharArray());
			KeyStore tstore = KeyStore.getInstance("bks");// jks
			tstore.load(new FileInputStream(sslTrustStore),
					sslTrustStorePassword.toCharArray());
			TrustManager[] tm;
			TrustManagerFactory tmf = TrustManagerFactory
					.getInstance("sunx509");
			tmf.init(tstore);
			tm = tmf.getTrustManagers();
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(keyFactory.getKeyManagers(), tm, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HttpClient httpClient = new DefaultHttpClient();
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext);
			socketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());

			// SSLSocketFactory.getSocketFactory().setHostnameVerifier(
			// new AllowAllHostnameVerifier());

			Scheme sch = new Scheme("https", 8443, socketFactory);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
			HttpPost httpPost = new HttpPost(httpUrl);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("user", "abin"));
			nvps.add(new BasicNameValuePair("pwd", "abing"));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			String spt = System.getProperty("line.separator");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			StringBuffer stb = new StringBuffer();
			String line = null;
			while ((line = buffer.readLine()) != null) {
				stb.append(line);
			}
			buffer.close();
			String result = stb.toString();
			System.out.println("result=" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		setUp();
		testHttpsClient();
	}
}
