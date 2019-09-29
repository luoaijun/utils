package com.luoaijun.utils.math;

/**
 * 
 * TODO
 * 
 *  罗爱军
 *  2018年3月8日
 *   aijun.luo@outlook.com
 *  Coolibcom.luoaijun.tool.mathMathString.java
 *  TODO:
 *
 * TODO : 工具类
 */
public class MathString {

	/**
	 * TODO 随机生成指定长度内的字符串
	 */
	public static String randomString(int number) {
		String STR = "";
		char[] str = { 'a', 'A' };

		char[] strChar = new char[52];
		for (int i = 0; i < strChar.length; i++) {

			if (i > 25) {
				strChar[i] = str[0]++;

			} else if (i < 25) {
				strChar[i] = str[1]++;
			}
		}
		for (int i = 0; i < number; i++) {
			int index = (int) (Math.random() * 51 + 1);
			STR = STR + strChar[index];
		}
		return STR;
	}
}
