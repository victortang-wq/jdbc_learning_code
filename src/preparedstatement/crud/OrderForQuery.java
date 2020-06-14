package preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import bean.Order;

public class OrderForQuery {
	public static void main(String[] args) {
		String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
		Order orderForQuery = orderForQuery(sql, 2);
		System.out.println(orderForQuery);
//		testQuery1();
	}
	public static Order orderForQuery(String sql, Object... args) {
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
				Order order = new Order();
				for (int i = 0; i < columnCount; i++) {
					// 获取每个列的列值:通过ResultSet
					Object columnValue = resultSet.getObject(i + 1);
					// 获取每个列的列名：通过ResultSetMetaData
//					String columnName = rsmd.getColumnName(i + 1); 获取列的别名
					String columnLabel = rsmd.getColumnLabel(i + 1);
					// 通过反射，将对象指定列名columnName的属性赋值为指定的值columnValue
					Field field = Order.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(order, columnValue);
				}
				return order;
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
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		try {
			connection = util.JDBCUtil.getConnection();
			String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setObject(1, 1);
			rs = prepareStatement.executeQuery();
			if (rs.next()) {
				int id = (int) rs.getObject(1);
				String name = (String) rs.getObject(2);
				Date date = (Date) rs.getObject(3);
				Order order = new Order(id, name, date);
				System.out.println(order);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, prepareStatement, rs);
		}
	}

}
