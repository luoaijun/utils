package com.luoaijun.utils.system;

import java.util.*;

/**

	
*/
public class CMUtility {
	private static Scanner scanner = new Scanner(System.in);

	/**
	 * 功能：读取键盘输入的一个菜单选项，值：1——5的范围
	 * 
	 * @return  return 1——5
	 */
	public static char readMenuSelection() {
		char c;
		for (;;) {
			String str = readKeyBoard(1, false);// 包含一个字符的字符串
			c = str.charAt(0);// 将字符串转换成字符char类型
			if (c != '1' && c != '2' && c != '3' && c != '4' && c != '5') {
				System.out.print("选择错误，请重新输入：");
			} else
				break;
		}
		return c;
	}

	/**
	 * 功能：读取键盘输入的一个字符
	 * 
	 * @return  return 一个字符
	 */
	public static char readChar() {
		String str = readKeyBoard(1, false);// 就是一个字符
		return str.charAt(0);
	}

	/**
	 * 功能：读取键盘输入的一个字符，如果直接按回车，则返回指定的默认值；否则返回输入的那个字符
	 * 
	 * @param defaultValue
	 *            指定的默认值
	 * @return  return 默认值或输入的字符
	 */

	public static char readChar(char defaultValue) {
		String str = readKeyBoard(1, true);// 要么是空字符串，要么是一个字符
		return (str.length() == 0) ? defaultValue : str.charAt(0);
	}

	/**
	 * 功能：读取键盘输入的整型，长度小于2位
	 * 
	 * @return  return 整数
	 */
	public static int readInt() {
		int n;
		for (;;) {
			String str = readKeyBoard(2, false);// 一个整数，长度<=2位
			try {
				n = Integer.parseInt(str);// 将字符串转换成整数
				break;
			} catch (NumberFormatException e) {
				System.out.print("数字输入错误，请重新输入：");
			}
		}
		return n;
	}

	/**
	 * 功能：读取键盘输入的 整数或默认值，如果直接回车，则返回默认值，否则返回输入的整数
	 * 
	 * @param defaultValue
	 *            指定的默认值
	 * @return  return 整数或默认值
	 */
	public static int readInt(int defaultValue) {
		int n;
		for (;;) {
			String str = readKeyBoard(2, true);
			if (str.equals("")) {
				return defaultValue;
			}

			try {
				n = Integer.parseInt(str);
				break;
			} catch (NumberFormatException e) {
				System.out.print("数字输入错误，请重新输入：");
			}
		}
		return n;
	}

	/**
	 * 功能：读取键盘输入的指定长度的字符串
	 * 
	 * @param limit
	 *            限制的长度
	 * @return  return 指定长度的字符串
	 */

	public static String readString(int limit) {
		return readKeyBoard(limit, false);
	}

	/**
	 * 功能：读取键盘输入的指定长度的字符串或默认值，如果直接回车，返回默认值，否则返回字符串
	 * 
	 * @param limit
	 *            限制的长度
	 * @param defaultValue
	 *            指定的默认值
	 * @return  return 指定长度的字符串
	 */

	public static String readString(int limit, String defaultValue) {
		String str = readKeyBoard(limit, true);
		return str.equals("") ? defaultValue : str;
	}

	/**
	 * 功能：读取键盘输入的确认选项，y或n
	 * 
	 * @return  return Y或N
	 */
	public static char readConfirmSelection() {
		char c;
		for (;;) {
			String str = readKeyBoard(1, false).toUpperCase();
			c = str.charAt(0);
			if (c == 'Y' || c == 'N') {
				break;
			} else {
				System.out.print("选择错误，请重新输入：");
			}
		}
		return c;
	}

	private static String readKeyBoard(int limit, boolean blankReturn) {
		String line = "";

		while (scanner.hasNextLine()) {
			line = scanner.nextLine();// input.next(); String line
										// =input.next();//abc def
			if (line.length() == 0) {
				if (blankReturn)
					return line;
				else
					continue;
			}

			if (line.length() < 1 || line.length() > limit) {
				System.out.print("输入长度（不大于" + limit + "）错误，请重新输入：");
				continue;
			}
			break;
		}

		return line;
	}
}
