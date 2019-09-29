package com.luoaijun.utils.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlDriver {

	/**
	 * TODO 获取一个通用的数据库连接
	 * @param str
	 * @return  return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	public Connection getConnection(String str)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {

		String driverClass = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;
		InputStream is = getClass().getClassLoader().getResourceAsStream("SqlDriver.properties");

		Properties properties = new Properties();
		properties.load(is);

		if (str.equals("mysql")) {
			driverClass = properties.getProperty("driver");
			jdbcUrl = properties.getProperty("jdbcUrl");
			user = properties.getProperty("user");
			password = properties.getProperty("password");

		} else if (str.equals("sql")) {

		} else if (str.equals("oracle")) {

		} else {
			System.err.println("null");
		}

		Driver driver = (Driver) Class.forName(driverClass).newInstance();
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);

		Connection connection = driver.connect(jdbcUrl, info);
		return connection;

	}

	/**
	 * TODO 获取一个通用的数据库连接
	 * @param str
	 * @return  return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Connection getConnectionManage(String str)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String driverClass = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;
		InputStream is = getClass().getClassLoader().getResourceAsStream("SqlDriver.properties");

		Properties properties = new Properties();
		properties.load(is);

		if (str.equals("mysql")) {
			driverClass = properties.getProperty("driver");
			jdbcUrl = properties.getProperty("jdbcUrl");
			user = properties.getProperty("user");
			password = properties.getProperty("password");

		} else if (str.equals("sql")) {

		} else if (str.equals("oracle")) {

		} else {
			System.err.println("null");
		}

		// 加载数据库驱动程序
		Class.forName(driverClass).newInstance();
		// 通过driverManage下的getConnection获取connection，获取数据库的连接
		Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
		return connection;

	}

}
