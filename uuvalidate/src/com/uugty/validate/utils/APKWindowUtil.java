package com.uugty.validate.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class APKWindowUtil {
	// D:/usr/local/uu/apk/GTY_UU_Android.apk
	private static final String apk_path = "D:/usr/local/uu/apk/";
	private static final String aapt = "D:/aapt";

	private static final String apk_name = "GTY_UU_Android.apk";
	private static final String UMENG_CHANNEL = "UMENG_CHANNEL";

	private static final String AndroidManifest = "AndroidManifest.xml";
	private static final Logger log = Logger.getLogger(APKWindowUtil.class);

	public static String getAPK(String promoteUserId) throws IOException {

		String manifestDest = apk_path + AndroidManifest;
		File file = new File(manifestDest);
		if (!file.exists()) {
			log.error("{" + manifestDest + "} 文件不存在！");
			throw new RuntimeException();
		}
		String readline = null;
		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		while ((readline = bufferedReader.readLine()) != null) {
			if (readline.contains(UMENG_CHANNEL)) {
				builder.append(readline + "\n");
				readline = bufferedReader.readLine();
				builder.append("            android:value=\"" + promoteUserId
						+ "\">\n");
			} else {
				builder.append(readline + "\n");
			}
		}
		bufferedReader.close();
		FileWriter fileWriter = new FileWriter(file, false);
		fileWriter.write(builder.toString());
		fileWriter.close();
		log.info("修改AndroidManifest.xml " + UMENG_CHANNEL + "{" + promoteUserId
				+ "}");
		runCmd("cmd /c  dir");

		// 复制apk
		String new_apk_name = promoteUserId + "_" + apk_name;
		String copyCmd = "cmd /c " + " copy " + apk_name + " " + new_apk_name;
		runCmd(copyCmd);

		// 删除 apk中的 AndroidManifest.xml
		String removeCmd = "cmd /c " + aapt + " remove " + new_apk_name + "  "
				+ AndroidManifest;
		runCmd(removeCmd);

		// 将新的AndroidManifest.xml导入到apk中
		String addCmd = "cmd /c " + aapt + " add " + new_apk_name + "  "
				+ AndroidManifest;
		runCmd(addCmd);

		return new_apk_name;
	}

	/**
	 * 执行指令
	 */
	public static void runCmd(String cmd) {
		Runtime rt = Runtime.getRuntime();
		BufferedReader br = null;
		InputStreamReader isr = null;
		InputStreamReader error = null;
		try {
			Process p = rt.exec(cmd);
			// p.waitFor();
			// 输出正确信息
			isr = new InputStreamReader(p.getInputStream(), "gbk");
			br = new BufferedReader(isr);
			String msg = null;
			while ((msg = br.readLine()) != null) {
				log.info(msg);
			}
			// 输出错误信息
			error = new InputStreamReader(p.getErrorStream(), "gbk");
			br = new BufferedReader(error);
			while ((msg = br.readLine()) != null) {
				log.info(msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (isr != null) {
					isr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			getAPK("123456789");
			// runCmd("cmd /c  dir");
			// runCmd("cmd /c   D:/aapt remove GTY_UU_Android.apk AndroidManifest.xml ");
			// runCmd("cmd /c D:/aapt add GTY_UU_Android.apk AndroidManifest.xml ");
			// ("cmd /c D:/aapt l GTY_UU_Android.apk ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
