package com.uugty.uuvalidate.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class Test {
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test = new Test();
		test.encode();
		test.decode();
	}

	// 编码
	/**
	 * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的 static final String
	 * DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";修改为UTF-8，否则中文编译后解析不了
	 */
	@SuppressWarnings("unused")
	public void encode() {
		try {
			String str = "姓名：曾驰文，性别：男，年龄：27，籍贯：湖南长沙，";// 二维码内容
			String path = "D://test.png";
			BitMatrix bitMatrix = null;
			BitMatrix encode = new MultiFormatWriter().encode(str,
					BarcodeFormat.QR_CODE, 200, 200);
			File file = new File(path);
			writeToFile(bitMatrix, "png", file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeToFile(BitMatrix bitMatrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(bitMatrix);
		ImageIO.write(image, format, file);
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	// 解码
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public void decode() {
		try {
			Reader reader = new MultiFormatReader();
			String imgPath = "D://test.png";
			File file = new File(imgPath);
			BufferedImage image;
			try {
				image = ImageIO.read(file);
				if (image == null) {
					System.out.println("Could not decode image");
				}
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(
						source));
				Result result;
				Hashtable hints = new Hashtable();
				hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
				// 解码设置编码方式为：utf-8，
				result = new MultiFormatReader().decode(bitmap, hints);
				String resultStr = result.getText();
				System.out.println("解析后内容：" + resultStr);

			} catch (IOException ioe) {
				System.out.println(ioe.toString());
			} catch (ReaderException re) {
				System.out.println(re.toString());
			}

		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

}