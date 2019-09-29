package com.luoaijun.utils.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 *  罗爱军
 *  2018-3-6
 *   aijun.luo@outlook.com
 *  Coolibcom.luoaijun.tool.ioIOStreamFuction.java
 *  TODO:
 * TODO BufferedReader BufferedWriter FileReader FileWriter FileInputStream
 *           FileOutputStream BufferedInputStream BufferedOutputStream
 *           ObjectInputStream ObjectOutputStream
 */
public class IOStreamFuction {

	/**
	 * TODO 创建一个文件
	 * @param str
	 * @return  return
	 * @throws IOException
	 */
	public static File createFile(String str) throws IOException {
		File file = new File(str);

		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * TODO 读取文件中第index个字符
	 * @param file
	 * @param index
	 * @return  return
	 * @throws IOException
	 */
	public static char readAChar(File file, int index) throws IOException {
		int len;
		FileReader reader = null;
		reader = new FileReader(file);
		len = reader.read();
		while (len != -1) {
			return (char) (reader.read());
		}
		reader.close();
		return 0;

	}

	/**
	 * TODO 递归打印文件和目录名
	 * @param file
	 */
	public static void list(File file) {
		File[] files = file.listFiles();

		for (File sub : files) {
			System.out.println(sub.getName());
			if (sub.isDirectory()) {
				list(sub);
			}
		}
	}

	/**
	 * TODO 递归打印含str后缀的文件和目录名
	 * @param file
	 * @param str
	 */
	public static void list(File file, String str) {
		File[] files = file.listFiles();

		for (File sub : files) {
			if (sub.getName().endsWith(str)) {
				System.out.println(sub.getName());
				continue;
			}
			if (sub.isDirectory()) {
				list(sub);
			}
		}
	}

	/**
	 * TODO 文件复制
	 * @param file
	 * @return  return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static File fileCopy(File... file) throws IOException {
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		inputStream = new FileInputStream(file[0]);
		outputStream = new FileOutputStream(file[1]);
		byte[] b = new byte[1024];
		int length;
		while ((length = inputStream.read(b)) != -1) {
			outputStream.write(b, 0, length);
		}
		inputStream.close();
		outputStream.close();
		return file[1];

	}

	/**
	 * TODO 获取文件中某行的字符串
	 * @param fileReader
	 * @param index
	 * @return  return Sting
	 * @throws IOException
	 */
	public static String readALine(FileReader fileReader, int index)
			throws IOException {
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

	/**
	 * TODO 打印文件中的内容
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void displayFile(File file) throws IOException {
		FileInputStream inputStream = null;
		inputStream = new FileInputStream(file);
		byte[] b = new byte[1024];
		int lenght;
		while ((lenght = inputStream.read(b)) != -1) {
			System.err.println(new String(b, 0, lenght));
		}
		inputStream.close();
	}

	/**
	 * TODO 写入文件 ，文件序列化
	 * @param file
	 * @param str
	 * @return  return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static File fileWrite(File file, String str) throws IOException {
		ObjectOutputStream outputStream = null;
		outputStream = new ObjectOutputStream(new FileOutputStream(file));
		int i = str.length();
		while (i-- >= 0) {
			outputStream.write(str.charAt(str.length() - i));
		}
		return file;
	}

	/**
	 * TODO 对象序列化
	 * @param file file
	 * @param t t
	 * @return  return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static <T> File fileWrite(File file, T... t) throws IOException {

		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new FileOutputStream(file));
		int i = 0;
		for (T t2 : t) {
			objectOutputStream.writeObject(t[i++]);
		}
		return file;
	}

	public static File fileUpdate(File file, String... str) {

		return file;
	}

	public static File fileDeleteAll(File file) {

		return file;

	}

	public static void fileDeleteFile(File file) {
	}

	/**
	 * 
	 * @param is
	 * @return  return
	 * @throws Exception
	 */
	public static byte[] streamToByteArray(InputStream is) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		byte[] b = new byte[1024];
		int len;
		while ((len = is.read(b)) != -1) {
			bos.write(b, 0, len);

		}

		byte[] array = bos.toByteArray();

		bos.close();
		return array;

	}

	/**
	 * 
	 * @param is
	 * @return  return
	 * @throws Exception
	 */

	public static String streamToString(InputStream is) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line + "\r\n");

		}

		return builder.toString();

	}

}
