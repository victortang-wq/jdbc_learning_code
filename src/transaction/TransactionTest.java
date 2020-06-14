package transaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class TransactionTest {

	public static void main(String[] args) throws Exception {
		testTransactionSelect();
	}
	
//	一个事务
	public static void testTransactionSelect() throws Exception {
		Connection connection = util.JDBCUtil.getConnection();
		System.out.println(connection.getTransactionIsolation());
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		String sql = "select user,password,balance from user_table where user = ?";
		User user = getInstance(connection, User.class, sql, "CC");
		System.out.println(user);
	}
//	一个事务
	public static void testTransactionUpdate() throws Exception {
		Connection connection = util.JDBCUtil.getConnection();
		String sql = "update user_table set balance = ? where user = ?";
		update(connection,sql,500,"CC");
		Thread.sleep(15000);
		System.out.println("修改成功");
	}
	public static void testUpdateWithTx() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			System.out.println(connection.getAutoCommit());
//			取消数据自动提交
			connection.setAutoCommit(false);
			String sql = "update user_table set balance = balance - 100 where user = ?";
			update(connection, sql,"AA");
			int i = 10 / 0;
			String sql2 = "update user_table set balance = balance + 100 where user = ?";
			update(connection, sql2,"BB");
			System.out.println("ok");
//			提交数据
			connection.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			回滚数据
			try {
				connection.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

//	通用的增删改操作 version 1.0
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
//	通用的增删改操作 version 2.0（考虑上事务）
	public static void update(Connection connection, String sql, Object... args) {
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
			util.JDBCUtil.closeResource(null, prepareStatement);
		}
	}
	
	
//	通用的查询操作，用于返回数据表中的一条记录
//		获取连接
	public static <T> T getInstance(Connection connection,Class<T> clazz, String sql, Object... args) {
		
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
			util.JDBCUtil.closeResource(null, prepareStatement, resultSet);
		}
		return null;

	}
}
