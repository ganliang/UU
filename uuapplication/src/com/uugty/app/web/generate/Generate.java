package com.uugty.app.web.generate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Generate {
	// 数据库连接
	private static final String URL = "jdbc:mysql://192.168.1.123:3306/uugty";
	private static final String NAME = "root";
	private static final String PASS = "123456";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static String[] colnames; // 列名数组
	private static String[] colTypes; // 列名类型数组
	private static int[] colSizes; // 列名大小数组
	private static String[] comments; // 列名的注释
	private boolean f_util = false; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*

	private static String mypackage;// 包的全限定名称
	private static String classname;// 类的名称

	// 获取数据库的连接
	private Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, NAME, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 关闭数据库资源
	private void close(PreparedStatement pst, Connection connection) {
		try {
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
			}
		}
	}

	// 初始化表的数据
	private void init(String tableName) {
		try {
			Connection connection = getConnection();
			String sql = "SELECT *FROM " + tableName;
			PreparedStatement pst = connection.prepareStatement(sql);
			ResultSetMetaData metaData = pst.getMetaData();

			int size = metaData.getColumnCount();
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			comments = new String[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = removeUnderline(metaData.getColumnName(i + 1));
				colTypes[i] = metaData.getColumnTypeName(i + 1);
				comments[i] = metaData.getColumnLabel(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")
						|| colTypes[i].equalsIgnoreCase("date")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = metaData.getColumnDisplaySize(i + 1);
			}
			close(pst, connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 拼接类的字符串
	private String contents(String tableName) {
		init(tableName);
		StringBuffer buffer = new StringBuffer();
		mypackage = this.getClass().getPackage().toString();
		buffer.append(mypackage + ";\r\n");
		if (f_sql) {
			buffer.append("import java.sql.*;\r\n");
		}
		if (f_util) {
			buffer.append("import java.util.Date;\r\n");
		}
		buffer.append("\r\n");
		// 根据表名获取到类型
		classname = removeUnderline(tableName);
		classname = initcap(tableName);
		classname = removeUnderline(classname);
		buffer.append("public class " + classname + "{\r\n");
		processAllAttrs(buffer);// 属性
		buffer.append("\r\n");
		processAllMethod(buffer);// get set方法
		buffer.append("}\r\n");
		return buffer.toString();
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")
				|| sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		}

		return null;
	}

	// 将表名的首字母变为大写
	private String initcap(String tableName) {
		if (tableName != null && !"".equals(tableName)) {
			char[] ch = tableName.toCharArray();
			if (ch[0] >= 'a' && ch[0] <= 'z') {
				ch[0] = (char) (ch[0] - 32);
			}
			return new String(ch);
		}
		return null;
	}

	/**
	 * 功能：生成所有属性
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		int count = 0;
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
					+ colnames[i] + ";\r\n");
			count++;
			if (count % 4 == 0) {
				sb.append("\r\n");
			}
		}

	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	// 将表的"_"变为大写 user_name ---> userName
	private String removeUnderline(String tableName) {
		while (tableName.contains("_")) {
			int size = tableName.indexOf("_");
			char index = tableName.charAt(size + 1);
			if (index >= 'a' && index <= 'z') {
				index = (char) (index - 32);
			}
			String begin = tableName.substring(0, size);
			String end = tableName.substring(size + 2);
			tableName = begin + index + end;
		}
		return tableName;
	}

	/**
	 * 将contents的内容写入到文件中去
	 * 
	 * @param tableName
	 */
	public void writeContents(String tableName) {
		try {
			String contents = contents(tableName);
			// System.out.println(contents);
			// 将内容写入到文件中去
			String replace = mypackage.substring(8).replace(".", "/");
			String path = this.getClass().getClassLoader().getResource(replace)
					.getPath();
			String replace2 = path.replace("WebContent/WEB-INF/classes", "src");
			File file = new File(replace2, classname + ".java");
			FileWriter writer = new FileWriter(file);
			PrintWriter pw = new PrintWriter(writer);
			pw.println(contents);
			pw.flush();
			pw.close();
			System.out.println(file.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取该数据库所有的表
	 * 
	 * @return
	 */
	public List<String> showtables() {
		try {
			Connection connection = getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery("show tables");
			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				list.add(rs.getString(1));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		Generate generate = new Generate();
		List<String> showtables = generate.showtables();
		for (String string : showtables) {
			generate.writeContents(string);
		}
	}
}
