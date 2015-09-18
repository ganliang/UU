package com.uugty.uuvalidate.util;

public class Main {
	public static void main(String[] args) {// 这里用cmd传入参数用

		String apk = "D:/usr/local/uu/apk/GTY_UU_Android.apk";
		String keyFile = "";
		String keyPasswd = "";

		SplitApk sp = new SplitApk(apk, keyFile, keyPasswd);
		sp.mySplit();
	}
}