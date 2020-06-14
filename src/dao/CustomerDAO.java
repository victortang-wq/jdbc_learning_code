package dao;
//此接口用于规范对于customers表的常用操作

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import bean.Customer;

public interface CustomerDAO {
//	添加customer对象到数据库
	void insert(Connection connection,Customer customer);
//	删除指定id的一条记录
	void deleteById(Connection connection,int id);
//	针对内存中的customer对象，修改数据表中的数据
	void update(Connection connection,Customer customer);
//	根据id 查询customer对象
	Customer getCustomerById(Connection connection,int id);
//	根据id 查询customer对象数组
	List<Customer> getAll(Connection connection);
//	返回数据表中数据的条目数
	Long getCount(Connection connection);
//	返回数据表中数据的条目数
	Date getMaxBirth(Connection connection);

}
