package com.uugty.app.web.jni;

/**
 * JNIProcess.java
 * com.jerome.jni
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-6-6 		Jerome Song
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
 */

/**
 * ClassName:JNIProcess
 * 
 * @author Jerome Song
 * @version
 * @Date 2013-6-6 下午10:07:03
 * 
 * @see
 */
public class JNIProcess {
	/**
	 * getInfoMD5: native方法，在C代码里实现
	 */
	public static native String getInfoMD5(String info);
}
