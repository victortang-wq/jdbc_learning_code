package connection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.imageio.stream.FileImageInputStream;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBCP {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		testGetConnection2();
	}
	
//	方式一：使用硬编码
	public static void testGetConnection() throws SQLException {
//		创建DBCP数据库的连接池
		BasicDataSource source = new BasicDataSource();
//		设置基本的信息
		source.setDriverClassName("com.mysql.jdbc.Driver");
		source.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
		source.setUsername("victor");
		source.setPassword("136019");
//		设置数据库连接池管理的相关属性
		source.setInitialSize(10);
		source.setMaxActive(10);
		
		Connection connection = source.getConnection();
		System.out.println(connection);
	}
//	方式二：使用配置文件
	public static void testGetConnection2() throws Exception {
		Properties properties = new Properties();
//		方式一
//		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
		properties.load(is);
		DataSource source = BasicDataSourceFactory.createDataSource(properties);
		Connection connection = source.getConnection();
		System.out.println(connection);
	}

}
