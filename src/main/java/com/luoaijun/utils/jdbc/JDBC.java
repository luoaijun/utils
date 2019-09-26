package com.luoaijun.utils.jdbc;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 
 * TODO JDBC 相关工具类
 * 
 * @author 罗爱军
 * @date 2018年3月8日
 * @email aijun.luo@outlook.com
 * @package JDBC-StudentInfoSystemcom.luoaijun.libraryJDBC.java
 * @describe TODO:
 * @include :
 * @category :
 */
public class JDBC {
	static Statement statement = null;

	public static Statement getStatement() {
		return statement;
	}

	public static void setStatement(Statement statement) {
		JDBC.statement = statement;
	}

	/**
	 * TODO 使用prepareStatement 执行添加删除操作
	 * 
	 * @param conn
	 * @param sql
	 * @param obj
	 */
	public static void excutePreUpdate(Connection conn, String sql, Object... obj) {
		PreparedStatement pps = null;
		pps = getPreStatement(conn, sql, obj);
		try {

			pps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * TODO 使用prepareStatement 执行查询修改操作
	 * 
	 * @param conn
	 * @param sql
	 * @param obj
	 * @return resultSet
	 */
	public static ResultSet executPreQuery(Connection conn, String sql, Object... obj) {
		PreparedStatement pps = null;
		pps = getPreStatement(conn, sql, obj);
		ResultSet resultSet = null;
		try {
			resultSet = pps.executeQuery();
			return resultSet;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * TODO 获取一个prepareStatement
	 * 
	 * @param conn
	 * @param sql
	 * @param obj
	 * @return
	 */
	public static PreparedStatement getPreStatement(Connection conn, String sql, Object... obj) {
		PreparedStatement pps = null;
		try {
			pps = (PreparedStatement) conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pps.setObject(i + 1, obj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pps;
	}

	/**
	 * TODO 获取一个jdbc连接
	 * 
	 * @param str
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnection(String str) throws Exception {
		String driver = null;
		String jdbcUrl = null;
		String user = null;
		String password = null;

		try {
			InputStream inStream = getClass().getClassLoader().getResourceAsStream("mysql.properties");
			Properties properties = new Properties();
			properties.load(inStream);
			driver = properties.getProperty("driver");
			jdbcUrl = str;
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			Class.forName(driver);// 加载驱动 Java项目中可以不加载，Javaweb中必须加载
			Connection connection = (Connection) DriverManager.getConnection(jdbcUrl, user, password);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public Connection getInjectConnect(String connStr) {
		return null;
	}

	/**
	 * TODO 使用statemen 执行删除添加操作
	 * 
	 * @return 无返回
	 * @param connection
	 * @param sql
	 */
	public static void execteUpdateSql(Connection connection, String sql) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * TODO 使用statement 获取一个更新查询操作的resultSet
	 * 
	 * @param connection
	 * @param sql
	 * @return ResultSet
	 */
	public static ResultSet execteQuerySql(Connection connection, String sql) {
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultSet;
	}

	/**
	 * TODO 关闭jdbc相关连接
	 * 
	 * @param statement
	 * @param c
	 */
	public static void close(Statement statement, Connection c) {
		try {
			if (statement != null) {
				statement.close();
			}
			if (c != null) {
				c.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * TODO 判断jdbc连接是否存在，并返回一个connection
	 * 
	 * @param connection
	 * @param connectionStr
	 * @return connection
	 * @throws Exception
	 */
	public Connection isConnect(Connection connection, String connectionStr) throws Exception {
		if (connection == null) {
			return connection = (new JDBC()).getConnection(connectionStr);
		}
		return connection;
	}

	/**
	 * TODO 获取一个list<T>
	 * 
	 * @param connectStr
	 * @param clazz
	 * @param sql
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static <T> List<T> getBeanList(Connection conn, Class<T> clazz, String sql, Object... para)
			throws Exception {
		List<T> list = new ArrayList<T>();
		PreparedStatement pps = JDBC.getPreStatement(conn, sql, para);
		ResultSet resultSet = pps.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columCount = metaData.getColumnCount();

		while (resultSet.next()) {
			T t = clazz.newInstance();
			for (int i = 0; i < columCount; i++) {
				String columName = metaData.getColumnLabel(i + 1);
				Object values = resultSet.getObject(i + 1);
				Field field = clazz.getDeclaredField(columName);
				field.setAccessible(true);
				field.set(t, values);
			}
			list.add(t);
		}
		return list;

	}

	/**
	 * TODO 获取总列数
	 *
	 * @return
	 * @throws SQLException
	 */
	public static int getCount(ResultSet resultSet) throws SQLException {
		int count = 0;
		if (resultSet.next()) {
			count = resultSet.getInt(1);
		}
		return count;

	}

	public static void printSys(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			System.out.println();
		}
	}

	public static ResultSet getResultSet(Connection conn, String sql, Object... obj) throws SQLException {
		PreparedStatement pps = JDBC.getPreStatement(conn, sql, obj);
		ResultSet resultSet = pps.executeQuery();
		return resultSet;
	}

}
