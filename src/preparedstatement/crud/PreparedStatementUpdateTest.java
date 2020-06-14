package preparedstatement.crud;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import connection.ConnectionTest;

public class PreparedStatementUpdateTest {

	public static void main(String[] args) throws Exception {
//		String sql = "delete from customers where id = ?";
//		update(sql, 3);
		String sql = "update `order` set order_name = ? where order_id = ?";
		update(sql,"DD","2");	
	}

//	通用的增删改操作
	public static void update(String sql, Object... args) {
//		sql中占位符的个数与可变形参的长度相同
		Connection connection = null;
// 2。预编译sql语句，返回prepareestatement的
		PreparedStatement prepareStatement = null;
		try {
			connection = util.JDBCUtil.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			// 3填充占位符
			for (int i = 0; i < args.length; i++) {
				// 与数据库的操作中，序号是从1开始
				prepareStatement.setObject(i + 1, args[i]);
			}
			// 4执行
			prepareStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 5资源的关闭
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}

	}

	public static void testUpdate() throws Exception {
//		1.获取数据库连接
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = util.JDBCUtil.getConnection();
			// 2。预编译sql语句，返回prepareestatement的
			String sql = "update customers set name = ? where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			// 3填充占位符
			prepareStatement.setObject(1, "莫扎特");
			prepareStatement.setObject(2, 18);
			// 4执行
			prepareStatement.execute();
			// 5资源的关闭
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}
	}

	public static void testInsert() {
//		3.获取连接
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			// 1.读取配置文件中的4个基本信息
			InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(is);
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			String url = properties.getProperty("url");
			String driverClass = properties.getProperty("driverClass");
			// 2.加载驱动
			Class.forName(driverClass);
			connection = DriverManager.getConnection(url, user, password);
			System.out.println(connection);

			// 4.预编译sql语句，返回PreparedStatement的实例
			String sql = "insert into customers(name,email,birth)values(?,?,?)";// ?表示占位符
			prepareStatement = connection.prepareStatement(sql);
			// 5.填充占位符
			prepareStatement.setString(1, "哪吒");
			prepareStatement.setString(2, "nezha@gmail.com");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = simpleDateFormat.parse("2020-10-12");
			prepareStatement.setDate(3, new java.sql.Date(date.getTime()));
			// 6.执行操作
			boolean execute = prepareStatement.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//		7.资源的关闭
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
