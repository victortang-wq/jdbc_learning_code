package exercise;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Exer2Test {
	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("四级/六级：4/6:");
//		int type = scanner.nextInt();
//		System.out.println("身份证号:");
//		String IDcard = scanner.next();
//		System.out.println("准考证号:");
//		String examCard = scanner.next();
//		System.out.println("学生姓名:");
//		String studentName = scanner.next();
//		System.out.println("所在城市:");
//		String location = scanner.next();
//		System.out.println("考试成绩:");
//		int grade = scanner.nextInt();
//		String sql = "insert into examStudent(type,IDcard,examCard,studentName,location,grade)values(?,?,?,?,?,?)";
//		int insertCount = update(sql,type,IDcard,examCard,studentName,location,grade);
//		if(insertCount > 0) {
//			System.out.println("添加成功");
//		}else {
//			System.out.println("添加失败");
//		}
//		System.out.println("请选择您要输入的类型：a.准考证号；b.身份证号");
//		Scanner scanner = new Scanner(System.in);
//		String selection = scanner.next();
//		if ("a".equalsIgnoreCase(selection)) {
//			System.out.println("请输入准考证号");
//			String examCard = scanner.next();
//			String sql = "select FlowID flowID,Type type,IDcard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where examCard = ?";
//			Student student = getInstance(Student.class, sql, examCard);
//			System.out.println(student);
//		} else if ("b".equalsIgnoreCase(selection)) {
//			System.out.println("请输入身份证号");
//			String IDCard = scanner.next();
//			String sql = "select FlowID flowID,Type type,IDcard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where IDCard = ?";
//			Student student = getInstance(Student.class, sql, IDCard);
//			System.out.println(student);
//
//		} else {
//			System.out.println("输入错误");
//		}
//		testDeleteByExamCard1();
		System.out.println(System.getProperty("user.dir"));
	}

	public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
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
			util.JDBCUtil.closeResource(connection, prepareStatement, resultSet);
		}
		return null;

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

	public static void testDeleteByExamCard() {
		System.out.println("请输入考生的学号");
		Scanner scanner = new Scanner(System.in);
		String examCard = scanner.next();
//		查询指定准考证号的学生
		String sql = "select FlowID flowID,Type type,IDcard,ExamCard examCard,StudentName name,Location location,Grade grade from examstudent where examCard = ?";
		Student student = getInstance(Student.class, sql, examCard);
		if (student == null) {
			System.out.println("查无此人，请重新输入");
		} else {
			String sql1 = "delete from examstudent where examCard = ?";
			int deleteCount = update(sql1, examCard);
			if (deleteCount > 0) {
				System.out.println("删除成功");
			} else {
				System.out.println("删除失败");
			}
		}

	}
	
	public static void testDeleteByExamCard1() {
		System.out.println("请输入考生的学号");
		Scanner scanner = new Scanner(System.in);
		String examCard = scanner.next();
		String sql = "delete from examstudent where examCard = ?";
		int deleteCount = update(sql, examCard);
		if(deleteCount > 0) {
			System.out.println("删除成功");
		}else {
			System.out.println("删除失败");
		}
	}
}
