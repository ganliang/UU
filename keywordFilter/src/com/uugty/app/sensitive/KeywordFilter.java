package com.uugty.app.sensitive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @title KeywordFilter
 * @author
 * @version 1.0
 */
public class KeywordFilter {
	/**
	 * 敏感词集合 {法={isEnd=0, 轮={isEnd=1}}, 中={isEnd=0, 国={isEnd=0, 人={isEnd=1},
	 * 男={isEnd=0, 人={isEnd=1}}}}}
	 * */
	private static HashMap keysMap = new HashMap();

	/**
	 * 添加敏感词
	 * 
	 * @param keywords
	 */
	public void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = keysMap;// 初始从最外层遍历
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(Character.toUpperCase(word));
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(Character.toUpperCase(word), newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
		System.out.println(keysMap);
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果没有，则返回0 如果有符合的keyword值，继续遍历，直至遇到isEnd =
	 * 1，返回匹配的keyword的长度，
	 */
	private int checkKeyWords(String txt, int begin) {
		HashMap nowhash = keysMap;
		int res = 0;
		for (int i = begin; i < txt.length(); i++) {
			char word = txt.charAt(i);
			Object wordMap = nowhash.get(Character.toUpperCase(word));// 得到该字符对应的HashMap
			if (wordMap == null) {
				return 0;// 如果该字符没有对应的HashMap，return 0
			}

			res++;// 如果该字符对应的HashMap不为null，说明匹配到了一个字符，+1
			nowhash = (HashMap) wordMap;// 将遍历的HashMap指向该字符对应的HashMap
			if (((String) nowhash.get("isEnd")).equals("1")) {// 如果该字符为敏感词的结束字符，直接返回
				return res;
			} else {
				continue;
			}
		}
		return 0;
	}

	/**
	 * 判断txt中是否有关键字
	 */
	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i);
			if (len > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 返回txt中关键字的列表
	 */
	public List<String> getTxtKeyWords(String txt) {
		List<String> list = new ArrayList<String>();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i);
			if (len > 0) {
				String tt = txt.substring(i, i + len);
				list.add(tt);
				i += len;
			} else {
				i++;
			}
		}
		return list;
	}

	/**
	 * 初始化敏感词列表
	 **/
	public void initfiltercode() {
		InputStream inputStream = null;
		BufferedReader buf = null;
		try {
			List<String> keywords = new ArrayList<String>();
			inputStream = this.getClass().getClassLoader()
					.getResourceAsStream("key.txt");
			buf = new BufferedReader(new InputStreamReader(inputStream));
			String readline = null;
			while ((readline = buf.readLine()) != null) {
				keywords.add(readline);
			}
			this.addKeywords(keywords);
		} catch (IOException e) {
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

	public static void main(String[] args) throws IOException {
		KeywordFilter filter = new KeywordFilter();
		filter.initfiltercode();
		String txt = "segcD.ru";
		boolean boo = filter.isContentKeyWords(txt);
		System.out.println(boo);
		List<String> set = filter.getTxtKeyWords(txt);
		System.out.println("包含的敏感词如下：" + set);
	}
}