package com.uugty.app.utils;

import static java.lang.Math.*;

public class CalculationUtil {
	/**
	 * 
	 * @Title: calculate
	 * @Description: 计算两点之间的距离
	 * @param @param startLat 起点维度
	 * @param @param startLng 起点经度
	 * @param @param endLat 终点维度
	 * @param @param endLng 终点经度
	 * @param @return 两点的距离
	 * @return double 返回类型
	 * @throws
	 */
	public static double calculate(double startLat, double startLng,
			double endLat, double endLng) {
		double res = 2 * 6378.137 * asin(sqrt(pow(
				sin(0.008726646259971648 * (startLat - endLat)), 2)
				+ cos(0.17453292519943296 * startLat)
				* cos(0.17453292519943296 * endLat)
				* pow(sin(0.008726646259971648 * (startLng - endLng)), 2)));
		return res;
	}

	public static void main(String[] args) {
		double calculate = calculate(43.5792090000, 87.3782140000,
				43.5729964355, 87.3717926749);
		System.out.println(calculate);

		double calculate1 = calculate(43.5792090000, 87.3782140000,
				43.5729951753, 87.3717947932);
		System.out.println(calculate1);

		double xinyang_to_beijing = calculate(32.1341730000, 114.0886130000,
				39.9097790000, 116.4337380000);
		System.out.println("信阳到北京的距离:" + xinyang_to_beijing + "km");

		double beijing_to_beijing = calculate(40.0981670000, 116.3816310000,
				40.0699650000, 116.3463930000);
		System.out.println("小辛庄村到育新:" + beijing_to_beijing + "km");
	}
}
