package dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import bean.Customer;

public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

	@Override
	public void insert(Connection connection, Customer customer) {
		// TODO Auto-generated method stub
		String sql = "insert into customers(name,email,birth)values(?,?,?)";
		update(connection, sql, customer.getName(), customer.getEmail(), customer.getBirth());

	}

	@Override
	public void deleteById(Connection connection, int id) {
		// TODO Auto-generated method stub
		String sql = "delete from customers where id = ?";
		update(connection, sql, id);
	}

	@Override
	public void update(Connection connection, Customer customer) {
		// TODO Auto-generated method stub
		String sql = "update customers set name = ?,email = ?,birth = ? where id = ?";
		int update = update(connection, sql , customer.getName(),customer.getEmail(),customer.getBirth(),customer.getId());
	}

	@Override
	public Customer getCustomerById(Connection connection, int id) {
		// TODO Auto-generated method stub
		String sql = "select id,name,email,birth from customers where id = ?";
		Customer customer = getInstance(connection, Customer.class, sql, id);
		return customer;
	}

	@Override
	public List<Customer> getAll(Connection connection) {
		// TODO Auto-generated method stub
		String sql = "select id,name,email,birth from customers";
		List<Customer> list = getForList(connection, Customer.class, sql);
		return list;
	}

	@Override
	public Long getCount(Connection connection) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from customers";
		 return getValue(connection, sql);
	}

	@Override
	public Date getMaxBirth(Connection connection) {
		// TODO Auto-generated method stub
		String sql = "select max(birth) from customers";
		return getValue(connection, sql);
	}

}
