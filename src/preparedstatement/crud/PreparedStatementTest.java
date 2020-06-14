package preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;
//preparedStatement更高效，可以实现批量操作；preparedStatement可以操作blob数据

import connection.User;

public class PreparedStatementTest {
	public static void main(String[] args) {
		testLogin();
	}
	public static void testLogin() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入用户名");
		String user = scanner.nextLine();
		System.out.println("请输入密码");
		String password = scanner.nextLine();
		String sql = "select user,password from user_table where user = ? and password = ?";
		User user2 = getInstance(User.class, sql, user,password);
		System.out.println(user2);
	}
	
	public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
//		获取连接
		Connection connection = null;
//		预编译sql语句
		PreparedStatement prepareStatement = null;
//		执行，获取结果集
		ResultSet resultSet = null;
		try {
			connection = util.JDBCUtil.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				prepareStatement.setObject(i + 1, args[i]);
			}
			resultSet = prepareStatement.executeQuery();
			// 获取结果集的元数据
			ResultSetMetaData rsmd = resultSet.getMetaData();
			// 获取列数
			int columnCount = rsmd.getColumnCount();
			if (resultSet.next()) {
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {
					// 获取每个列的列值:通过ResultSet
					Object columnValue = resultSet.getObject(i + 1);
					// 获取每个列的列名：通过ResultSetMetaData
//					String columnName = rsmd.getColumnName(i + 1); 获取列的别名
					String columnLabel = rsmd.getColumnLabel(i + 1);
					// 通过反射，将对象指定列名columnName的属性赋值为指定的值columnValue
					Field field = t.getClass().getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columnValue);
				}
				return t;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, prepareStatement, resultSet);
		}
		return null;
	}

}
