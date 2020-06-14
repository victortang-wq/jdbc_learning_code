package connection;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
	public static void main(String[] args) throws Exception {
		testConnection5();
	}

//	方式一
	public static void testConnection1() throws SQLException {
//		获取driver的实现类
		Driver driver = new com.mysql.jdbc.Driver();
//		jdbc:mysql:协议；localhost:类似于ip地址；3306：端口号；test:数据库；问号：切换一下编码格式
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
//		将用户名和密码封装在Properties里面
		Properties info = new Properties();
		info.setProperty("user", "victor");
		info.setProperty("password", "136019");
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}

//	方式二，使用反射获取driver的实现类对象
	public static void testConnection2() throws Exception {
//		使用反射获取driver的实现类对象
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver = (Driver) clazz.newInstance();
//		jdbc:mysql:协议；localhost:类似于ip地址；3306：端口号；test:数据库；问号：切换一下编码格式
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
//		将用户名和密码封装在Properties里面
		Properties info = new Properties();
		info.setProperty("user", "victor");
		info.setProperty("password", "136019");
		Connection conn = driver.connect(url, info);
		System.out.println(conn);
	}

//	方式三
	public static void testConnection3() throws Exception {
//		1.提供三个基本信息
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver driver = (Driver) clazz.newInstance();
		// 注册驱动
		DriverManager.registerDriver(driver);
//		获取连接
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
		String user = "victor";
		String password = "136019";
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);

	}

//	方式4 只加载驱动 不用显示的注册驱动
	public static void testConnection4() throws Exception {
//		1.提供三个基本信息
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
//		Driver driver = (Driver) clazz.newInstance();
		// 注册驱动
//		DriverManager.registerDriver(driver);
//		获取连接
		String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
		String user = "victor";
		String password = "136019";
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);

	}

//	方式5 将数据库连接需要的4个基本信息声明在配置文件中，通过读取配置文件的方式，获取连接
//	好处：实现了数据与代码的分离，实现了解耦；如果需要修改配置信息，可以避免程序重新打包，直接修改配置文件即可，不需要修改代码包
	public static void testConnection5() throws Exception {
//		1.读取配置文件中的4个基本信息
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
	}

}
