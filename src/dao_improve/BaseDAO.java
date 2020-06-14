package dao_improve;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//DAO:data(base) access object
//封装了对数据表的通用查询
public abstract class BaseDAO<T> {
	private Class<T> clazz = null;
	//通用的查询操作，用于返回数据表中的一条记录构成的集合（考虑上事务）
	{
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) genericSuperclass;
		Type[] typeArguments = paramType.getActualTypeArguments();
		clazz  = (Class<T>) typeArguments[0];//泛型的第一个参数
	}
	public int update(Connection connection, String sql, Object... args) {
		PreparedStatement ps = null;
		try {
//			预编译sql语句
			ps = connection.prepareStatement(sql);
//			填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
//			执行
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			资源的关闭
			util.JDBCUtil.closeResource(null, ps);
		}
		return 0;
	}
//通用的查询操作，用于返回数据表中的多条记录构成的集合（考虑上事务）
	public List<T> getForList(Connection connection, String sql, Object... args) {
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

			while (resultSet.next()) {
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
			util.JDBCUtil.closeResource(null, prepareStatement, resultSet);
		}
		return null;
	}
	
	public <E> E getValue(Connection connection,String sql,Object...args){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++) {
				ps.setObject(i + 1, args[i]);
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				return (E) rs.getObject(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			util.JDBCUtil.closeResource(null, ps, rs);
		}
		return null;
	}
	
	public  T getInstance(Connection connection, String sql, Object... args) {
//		预编译sql语句
		PreparedStatement prepareStatement = null;
//		执行，获取结果集
		ResultSet resultSet = null;
		try {
			connection = util.JDBCUtil.getConnection();
			prepareStatement = (PreparedStatement) connection.prepareStatement(sql);
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
