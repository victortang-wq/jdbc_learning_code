package blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Customer;

//使用preparedstatement操作blob数据
public class BlobTest {
	public static void main(String[] args) throws Exception {
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		InputStream is = null;
		FileOutputStream fileOutputStream = null;
		try {
			connection = util.JDBCUtil.getConnection();
			String sql = "select id,name,birth,photo from customers where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, 24);
			ResultSet resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				Customer customer = new Customer(id,name,email,birth);
				System.out.println(customer);
//			将blob类型的字段下载下来，以文件的形式下载到本地
				Blob photo = resultSet.getBlob("photo");
				is = photo.getBinaryStream();
				fileOutputStream = new FileOutputStream("photo.jpg");
				byte[] buffer = new byte[1024];
				int len;
				while((len = is.read(buffer)) != -1) {
					fileOutputStream.write(buffer,0,len);
				}
			}else {
				System.out.println("13423");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			try {
				if(fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}
	}
	
//	插入blob类型的数据
	public static void testInsert() throws Exception {
		Connection connection = util.JDBCUtil.getConnection();
		String sql = "insert into customers(name,email,birth,photo)values(?,?,?,?)";
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setObject(1, "张宇豪");
		prepareStatement.setObject(2, "1223@qq.com");
		prepareStatement.setObject(3, "1997-01-13");
		FileInputStream is = new FileInputStream(new File("C:\\Users\\victor\\eclipse-workspace\\jdbc1\\flower.jpg"));
		prepareStatement.setObject(4, is);
		prepareStatement.execute();
		util.JDBCUtil.closeResource(connection, prepareStatement);
	}
//	查询blob类型的数据
	public static void testQuery(){
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		InputStream is = null;
		FileOutputStream fileOutputStream = null;
		try {
			connection = util.JDBCUtil.getConnection();
			String sql = "select id,name,birth,photo from customers where id = ?";
			prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setInt(1, 24);
			ResultSet resultSet = prepareStatement.executeQuery();
			if(resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				Customer customer = new Customer(id,name,email,birth);
				System.out.println(customer);
//			将blob类型的字段下载下来，以文件的形式下载到本地
				Blob photo = resultSet.getBlob("photo");
				is = photo.getBinaryStream();
				fileOutputStream = new FileOutputStream("photo.jpg");
				byte[] buffer = new byte[1024];
				int len;
				while((len = is.read(buffer)) != -1) {
					fileOutputStream.write(buffer,0,len);
				}
			}else {
				System.out.println("13423");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(is != null) {
					is.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			try {
				if(fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}
		
	}

}
