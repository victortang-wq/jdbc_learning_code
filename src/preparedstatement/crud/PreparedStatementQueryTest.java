package preparedstatement.crud;
//实现针对不同表的通用查询操作

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import bean.Customer;
import bean.Order;

public class PreparedStatementQueryTest {
	public static void main(String[] args) {
		String sql = "SELECT id,name,email FROM customers WHERE id > ?";
		List<Customer> list = getForList(Customer.class, sql, 2);
		list.forEach(System.out::println);
		sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id < ?";
		List<Order> list2 = getForList(Order.class, sql, 4);
		list2.forEach(System.out::println);
	}
	
	public static <T> List<T> getForList(Class<T> clazz,String sql,Object...args){
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
//			创建集合对象
			ArrayList<T> list = new ArrayList<T>();
			
			 while(resultSet.next()) {
				T t = clazz.newInstance();
//				处理结果集一行数据中的每一列：给t对象指定的属性赋值
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
				list.add(t);
			}
			 return list;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, prepareStatement, resultSet);
		}
		return null;

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
