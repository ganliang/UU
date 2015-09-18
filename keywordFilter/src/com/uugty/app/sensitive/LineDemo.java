package com.uugty.app.sensitive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class LineDemo {

	public static void main(String[] args) {
		InputStream inputStream = null;
		BufferedReader buf = null;

		try {
			inputStream = LineDemo.class.getClassLoader().getResourceAsStream(
					"key.txt");
			buf = new BufferedReader(new InputStreamReader(inputStream));
			String readline = buf.readLine();
			String replace = readline.replace("|", "\r\n");
			System.out.println(replace);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buf.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
