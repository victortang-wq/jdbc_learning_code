package preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import bean.Customer;

//针对Customer表的查询
public class CustomerForQuery {
	public static void main(String[] args) {
		String sql = "select id,name,email,birth from customers where id= ?";
		Customer customer = queryForCustomers(sql, 13);
		System.out.println(customer);
		sql = "select name,email from customers where name = ?";
		customer = queryForCustomers(sql, "周杰伦");
		System.out.println(customer);
		
	}

//	实现对表的通用查询
	public static Customer queryForCustomers(String sql, Object... args) {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = util.JDBCUtil.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				prepareStatement.setObject(i + 1, args[i]);
			}
			resultSet = prepareStatement.executeQuery();
//		获取结果集的元数据ResultSetMetaData，从而获取结果集的列数
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (resultSet.next()) {
				Customer customer = new Customer();
//			处理结果集一行数据中的每一列
				for (int i = 0; i < columnCount; i++) {
//				获取列值
					Object columnValue = resultSet.getObject(i + 1);
//				获取每个列的列名
					String columnName = rsmd.getColumnName(i + 1);
//				给customer对象指定的columnName属性，赋值为columnValue：通过反射
					Field field = Customer.class.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(customer, columnValue);
				}
				return customer;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, prepareStatement, resultSet);
		}
		return null;
	}

	public static void testQuery1() {
//		1.建立连接
		Connection connection = null;
//		2 预编译sql语句
		PreparedStatement prepareStatement = null;
//		执行，并返回结果集
		ResultSet resultSet = null;
		try {
			connection = util.JDBCUtil.getConnection();
			String sql = "select id,name,email,birth from customers where id= ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, 1);
			resultSet = prepareStatement.executeQuery();
			// 处理结果集
			if (resultSet.next()) {// 判断结果集的下一条是否有数据，如果有，返回true
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				// 将数据封装为一个对象
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
			}
			// 关闭资源
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			util.JDBCUtil.closeResource(connection, prepareStatement, resultSet);
		}

	}

}
