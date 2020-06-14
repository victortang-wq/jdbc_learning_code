package dbutils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import bean.Customer;

//dbutils封装了对数据库的增删改查操作
public class QueryRunnerTest {
	public static void main(String[] args) throws SQLException {
		testQuery7();
	}

	public static void testInsert() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "insert into customers(name,email,birth)values(?,?,?)";
			int insertCount = runner.update(connection, sql, "蔡徐坤", "caixunkun@126.com", "1997-09-08");
			System.out.println("添加了" + insertCount + "条记录");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

//	测试查询
//	beanHander:是resultSetHandler接口的实现类，用于封装表中的一条记录
	public static void testQuery() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select id,name,email,birth from customers where id = ?";
			BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
			Customer customer = runner.query(connection, sql, handler, 1);
			System.out.println(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}

	}

	public static void testQuery3() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select id,name,email,birth from customers where id = ?";
			MapHandler handler = new MapHandler();
			Map<String, Object> customer = runner.query(connection, sql, handler, 2);
			System.out.println(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}

	}

//	测试查询
//	BeanListHandler:是resultSetHandler接口的实现类，用于封装表中的多条记录构成的集合
	public static <T> void testQuery2() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select id,name,email,birth from customers where id < ?";
			BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
			List<Customer> list = runner.query(connection, sql, handler, 10);
			list.forEach(System.out::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}

	}

	public static <T> void testQuery4() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select id,name,email,birth from customers where id < ?";
			MapListHandler handler = new MapListHandler();
			List<Map<String, Object>> list = runner.query(connection, sql, handler, 10);
			list.forEach(System.out::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}

	}

	public static <T> void testQuery5() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select count(*) from customers";
			ScalarHandler handler = new ScalarHandler();
			Long count = (Long) runner.query(connection, sql, handler);
			System.out.println(count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	public static <T> void testQuery6() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select max(birth) from customers";
			ScalarHandler handler = new ScalarHandler();
			Object query = runner.query(connection, sql, handler);
			System.out.println(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

//	自定义resultSetHandler的实现类
	public static <T> void testQuery7() {
		Connection connection = null;
		try {
			QueryRunner runner = new QueryRunner();
			connection = util2Dbcp.getConnection.getConnection3();
			String sql = "select id,name,birth from customers where id =?";
			ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {

				@Override
				public Customer handle(ResultSet rs) throws SQLException {
					// TODO Auto-generated method stub
					if(rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String email = rs.getString("email");
						Date birth = rs.getDate("birth");
						Customer customer = new Customer(id,name,email,birth);
						return customer;
					}
					return null;
				}
				
			};
			Customer customer = runner.query(connection, sql, handler, 23);
			System.out.println(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}
}
