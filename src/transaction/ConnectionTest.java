package transaction;

import java.sql.Connection;

public class ConnectionTest {
	public static void main(String[] args) throws Exception {
		Connection connection = util.JDBCUtil.getConnection();
	}
	
}
