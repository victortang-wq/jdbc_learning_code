package dao_improve_junit;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import util2Dbcp.getConnection;
import dao_improve.CustomerDAOImpl;
import org.junit.jupiter.api.Test;

import bean.Customer;

class CustomerDAOImplTest {
	private CustomerDAOImpl dao = new CustomerDAOImpl();

	@Test
	void testInsert() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			Customer customer = new Customer(1, "于小飞", "xiaofei@126.com", new Date(2345365656L));
			dao.insert(connection, customer);
			System.out.println("添加成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testDeleteById() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			dao.deleteById(connection, 24);
			System.out.println("删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testUpdateConnectionCustomer() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			Customer customer = new Customer(18,"贝多芬","1277842@qq.com",new Date(45686754854L));
			dao.update(connection, customer);
			System.out.println("修改成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testGetCustomerById() {
		Connection connection = null;
		try {
			connection = util2Dbcp.getConnection.getConnection3();
			Customer customer = dao.getCustomerById(connection, 10);
			System.out.println(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testGetAll() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			List<Customer> list = dao.getAll(connection);
			list.forEach(System.out::println);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testGetCount() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			Long count = dao.getCount(connection);
			System.out.println("表中的记录数为：" + count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

	@Test
	void testGetMaxBirth() {
		Connection connection = null;
		try {
			connection = util.JDBCUtil.getConnection();
			Date maxBirth = dao.getMaxBirth(connection);
			System.out.println("最大生日:" +  maxBirth);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			util.JDBCUtil.closeResource(connection, null);
		}
	}

}
