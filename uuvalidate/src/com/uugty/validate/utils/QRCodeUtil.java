package com.uugty.validate.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeUtil {

	private static final String qrcode = "/usr/local/uu/images/qrcode/";
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;
	private static final Logger log = Logger.getLogger(QRCodeUtil.class);

	/**
	 * 生成二维码
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	public static String encode(String promoteUserId) throws WriterException,
			IOException {

		// 目录不存在 则创建
		File qrcodeFile = new File(qrcode);
		if (!qrcodeFile.exists()) {
			qrcodeFile.mkdirs();
		}
		// 检测二维码是否存在 存在删除
		File oldfile = new File(qrcode + promoteUserId + "_GTY_UU_Android.apk");
		if (oldfile.exists()) {
			oldfile.delete();
		}
		String content = "http://www.uugty.com:100/apk/" + promoteUserId
				+ "_GTY_UU_Android.apk";
		int width = 200; // 图像宽度
		int height = 200; // 图像高度
		String format = "png";// 图像类型
		String fileName = StringUtil.getFileName() + "." + format;

		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
				BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵

		File file = new File(qrcode, fileName);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		ImageIO.write(image, format, file);
		log.info("二维码 {" + file.getAbsolutePath() + "} 生成");
		return fileName;
	}

	/**
	 * 解析图像
	 */
	public static void decode() {
		String filePath = qrcode + "58X4dmjsOqy.png";
		BufferedImage image;
		try {
			image = ImageIO.read(new File(filePath));
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = new MultiFormatReader().decode(binaryBitmap, hints);// 对图像进行解码
			System.out.println(result.getText());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			// encode();
			// decode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
