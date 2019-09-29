package com.luoaijun.utils.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * TODO
 * 
 *  罗爱军
 *  2018年3月8日
 *   aijun.luo@outlook.com
 *  Coolibcom.luoaijun.tool.ioIOByteFunction.java
 *  TODO:
 *
 * TODO :
 */
public class IOByteFunction {
	/**
	 * TODO 获取文件中某行的字符串
	 * @param fileReader
	 * @param index
	 * @return  return Sting
	 * @throws IOException
	 */
	public static String readALine(FileReader fileReader, int index) throws IOException {
		String str = null;
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		reader = new BufferedReader(fileReader);
		while ((str = reader.readLine()) != null) {
			list.add(str);
		}
		reader.close();
		return list.get(index);
	}
}
