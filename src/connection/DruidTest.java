package connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest {
	public static void main(String[] args) throws Exception {
		getConnection();
	}
	public static void getConnection() throws Exception {
		Properties properties = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
		properties.load(is);
		DataSource source = DruidDataSourceFactory.createDataSource(properties);
		Connection connection = source.getConnection();
		System.out.println(connection);
	}
}
