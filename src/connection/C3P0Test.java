package connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Test {
	public static void main(String[] args) throws Exception {
		testGetConnection2();
	}

	public static void testGetConnection() throws Exception {
//		获取C3P0数据库连接池
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass("com.mysql.jdbc.Driver"); // loads the jdbc driver
		cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
		cpds.setUser("victor");
		cpds.setPassword("136019");
//		通过设置相关的参数，从而对数据库连接池进行管理
//		设置初始时数据库连接池中的连接数
		cpds.setInitialPoolSize(10);
		Connection connection = cpds.getConnection();
		System.out.println(connection);
	}
	public static void testGetConnection2() throws Exception {
		ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
		Connection connection = cpds.getConnection();
		System.out.println(connection);
	}
}
