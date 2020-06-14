package blob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//preparedstatemet实现批量操作
public class InsertTest {
//	批量插入两万条数据
	public static void main(String[] args) {
		testInsert1();
	}
	public static void testInsert1()  {
		int n = 20000;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			long start = System.currentTimeMillis();
			connection = util.JDBCUtil.getConnection();
			String sql = "insert into goods(name)values(?)";
			prepareStatement = connection.prepareStatement(sql);
			for(int i = 1;i < n;i++) {
				prepareStatement.setObject(1, "name_" + i);
				prepareStatement.execute();
			}
			long end = System.currentTimeMillis();
			System.out.println("所花费的时间为：" + (end - start));
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}
	}

}
