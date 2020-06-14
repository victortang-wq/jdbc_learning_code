package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;


//	操作数据库的工具类
public class JDBCUtil {
//	获取数据库的链接
	public static Connection getConnection() throws Exception{
//		1.读取配置文件中的4个基本信息
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(is);
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		String url = properties.getProperty("url");
		String driverClass = properties.getProperty("driverClass");
//		2.加载驱动
		Class.forName(driverClass);
//		3.获取连接
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);
		return connection;
	}

//	关闭资源
	public static void closeResource(Connection connection, PreparedStatement prepareStatement) {
		try {
			if(prepareStatement != null) {
				prepareStatement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	关闭资源
	public static void closeResource(Connection connection, PreparedStatement prepareStatement,ResultSet rs) {
		try {
			if(prepareStatement != null) {
				prepareStatement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	使用dbutils工具类，实现资源的关闭
	public static void closeResource3(Connection connection, PreparedStatement prepareStatement,ResultSet rs) {
		DbUtils.closeQuietly(connection);
		DbUtils.closeQuietly(prepareStatement);
		DbUtils.closeQuietly(rs);
	}
}
