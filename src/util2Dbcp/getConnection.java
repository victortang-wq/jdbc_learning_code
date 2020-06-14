package util2Dbcp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class getConnection {

	private static DataSource source;
	static {
		try {
			Properties properties = new Properties();
			FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
			properties.load(is);
//		创建DBCP数据库连接池
			source = BasicDataSourceFactory.createDataSource(properties);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getConnection2() {
		Connection connection = null;
		try {
			connection = source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
//	使用druid数据库连接池技术
	private static DataSource source1;
	static {
		try {
			Properties properties = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
			properties.load(is);
			source1 = DruidDataSourceFactory.createDataSource(properties);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection3() throws SQLException {
		Connection connection = source.getConnection();
		return connection;
	}
}
