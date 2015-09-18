package com.uugty.app.web.jni;

public class JNIProcessCall {

	static {
		System.out.println("before.....................");
		System.loadLibrary("process");
		System.out.println("after.....................");
		System.out.println(System.getProperty("java.library.path"));
		// Runtime.getRuntime().loadLibrary("process");
	}

	public static void call() {
		System.out.println(JNIProcess.getInfoMD5("123"));
	}

	public static void main(String[] args) {
		call();
	}
}
