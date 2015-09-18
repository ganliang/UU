package com.uugty.uuvalidate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SplitApk {
	HashMap<String, String> qudao = new HashMap<String, String>();// 渠道号，渠道名
	String curPath;// 当前文件夹路径
	String apkName;
	String keyFile;
	String keyPasswd;

	public SplitApk(String apkName, String keyFile, String keyPasswd) {// 构造函数接受参数
		this.curPath = new File("").getAbsolutePath();
		this.apkName = apkName;
		this.keyFile = keyFile;
		this.keyPasswd = keyPasswd;
	}

	public void mySplit() {
		modifyXudao();// 解包 - 打包 - 签名
	}

	/**
	 * apktool解压apk，替换渠道值
	 * 
	 * @throws Exception
	 */
	private void modifyXudao() {
		// 解压 /C 执行字符串指定的命令然后终断
		String cmdUnpack = "cmd.exe /C java -jar apktool.jar d -f -s "
				+ apkName;
		runCmd(cmdUnpack);
		System.out.println("解压apk成功!");

		// 备份AndroidManifest.xml
		// 获取解压的apk文件名
		String[] apkFilePath = apkName.split("\\\\");
		String shortApkName = apkFilePath[apkFilePath.length - 1];
		String dir = shortApkName.split(".apk")[0];
		File packDir = new File(dir);// 获得解压的apk目录

		String f_mani = packDir.getAbsolutePath() + "\\AndroidManifest.xml";
		String f_mani_bak = curPath + "\\AndroidManifest.xml";
		File manifest = new File(f_mani);
		File manifest_bak = new File(f_mani_bak);

		// 拷贝文件 -- 此方法慎用，详见http://xiaoych.iteye.com/blog/149328
		manifest.renameTo(manifest_bak);

		for (int i = 0; i < 10; i++) {
			if (manifest_bak.exists()) {
				break;
			}
		}

		if (manifest_bak.exists()) {
			System.out.println("==INFO 3.==移动文件成功======");
		} else {
			System.out.println("==ERROR==移动文件失败======");
		}

		// 创建生成结果的目录
		File f = new File("apk");
		if (!f.exists()) {
			f.mkdir();
		}

		/*
		 * 遍历map，复制manifese进来，修改后打包，签名，存储在对应文件夹中
		 */
		for (Map.Entry<String, String> entry : qudao.entrySet()) {
			String id = entry.getKey();
			System.out.println("==INFO 4.1. == 正在生成包: " + entry.getValue()
					+ " ======");
			BufferedReader br = null;
			FileReader fr = null;
			FileWriter fw = null;
			try {
				fr = new FileReader(manifest_bak);
				br = new BufferedReader(fr);
				String line = null;
				StringBuffer sb = new StringBuffer();
				while ((line = br.readLine()) != null) {
					if (line.contains("\"ads-2.0\"")) {
						line = line.replaceAll("ads-2.0", id);
					}
					sb.append(line + "\n");
				}

				// 写回文件
				fw = new FileWriter(f_mani);
				fw.write(sb.toString());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fr != null) {
						fr.close();
					}
					if (br != null) {
						br.close();
					}
					if (fw != null) {
						fw.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			System.out.println("==INFO 4.2. == 准备打包: " + entry.getValue()
					+ " ======");

			// 打包 - 生成未签名的包
			String unsignApk = id + "_" + dir + "_un.apk";
			String cmdPack = String.format(
					"cmd.exe /C java -jar apktool.jar b %s %s", dir, unsignApk);
			runCmd(cmdPack);

			System.out.println("==INFO 4.3. == 开始签名: " + entry.getValue()
					+ " ======");
			// 签名
			String signApk = "./apk/" + id + "_" + dir + ".apk";
			String cmdKey = String
					.format("cmd.exe /C jarsigner -digestalg SHA1 -sigalg MD5withRSA -verbose -keystore %s -signedjar %s %s %s -storepass  %s",
							keyFile, signApk, unsignApk, keyFile, keyPasswd);
			runCmd(cmdKey);
			System.out.println("==INFO 4.4. == 签名成功: " + entry.getValue()
					+ " ======");
			// 删除未签名的包
			File unApk = new File(unsignApk);
			unApk.delete();
		}

		// 删除中途文件
		String cmdKey = String.format("cmd.exe /C rd /s/q %s", dir);
		runCmd(cmdKey);
		manifest_bak.delete();

		System.out.println("==INFO 5 == 完成 ======");
	}

	/**
	 * 执行指令
	 * 
	 * @param cmd
	 */
	public void runCmd(String cmd) {
		Runtime rt = Runtime.getRuntime();
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			Process p = rt.exec(cmd);
			// p.waitFor();
			isr = new InputStreamReader(p.getInputStream());
			br = new BufferedReader(isr);
			String msg = null;
			while ((msg = br.readLine()) != null) {
				System.out.println(msg);
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
}