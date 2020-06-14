package exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Exer1Test {
	public static void main(String[] args) {
		testInsert();
	}

	public static void testInsert() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入用户名");
		String name = scanner.next();
		System.out.println("请输入邮箱");
		String email = scanner.next();
		System.out.println("请输入生日");
		String birth = scanner.next();
		String sql = "insert into customers(name,email,birth)values(?,?,?)";
		int update = update(sql, name, email, birth);
		if(update > 0) {
			System.out.println("插入成功");
		}else {
			System.out.println("插入失败");
		}
	}

//	通用的增删改操作
	public static int update(String sql, Object... args) {
//		sql中占位符的个数与可变形参的长度相同
		Connection connection = null;
// 2。预编译sql语句，返回prepareestatement的
		PreparedStatement prepareStatement = null;
		try {
			connection = util.JDBCUtil.getConnection();
			prepareStatement = connection.prepareStatement(sql);
			// 3填充占位符
			for (int i = 0; i < args.length; i++) {
				// 与数据库的操作中，序号是从1开始
				prepareStatement.setObject(i + 1, args[i]);
			}
			// 4执行
//			prepareStatement.execute();如果执行的是查询操作，有返回结果，则返回true；
//			如果是增、删、改操作，无返回结果，则返回false
			return prepareStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 5资源的关闭
			util.JDBCUtil.closeResource(connection, prepareStatement);
		}
		return 0;
	}

}
