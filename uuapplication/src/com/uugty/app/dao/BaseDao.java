package com.uugty.app.dao;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.constant.StringConstant;
import com.uugty.app.utils.BeanUtil;

/**
 * 
 * 
 * @author uu
 *
 */
public class BaseDao {
	private static final Logger log = Logger.getLogger(BaseDao.class);

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public Connection openConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("proxool.myData");
		} catch (SQLException e) {
			try {
				Thread.currentThread().sleep(500);
				conn = DriverManager.getConnection("proxool.myData");
			} catch (Exception e1) {
				try {
					Thread.currentThread().sleep(500);
					conn = DriverManager.getConnection("proxool.myData");
				} catch (Exception e2) {
					throw new RuntimeException("数据库连接失败", e2);
				}
			}
		}
		return conn;
	}

	/**
	 * 关闭数据库资源
	 * 
	 * @param ps
	 * @param st
	 * @param rs
	 * @param conn
	 */
	public void close(PreparedStatement ps, Statement st, ResultSet rs,
			Connection conn) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			log.error("关闭PreparedStatement异常异常!", e);
			throw new RuntimeException("关闭PreparedStatement异常异常!", e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (Exception e1) {
				log.error("关闭Statement异常!", e1);
				throw new RuntimeException("关闭Statement异常!", e1);
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch (Exception e1) {
					log.error("关闭ResultSet异常!", e1);
					throw new RuntimeException("关闭ResultSet异常!", e1);
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (Exception e1) {
						log.error("关闭连接异常!", e1);
						throw new RuntimeException("关闭连接异常!", e1);
					} finally {
					}
				}
			}
		}
	}

	/**
	 * 向数据库添加数据
	 * 
	 * @param sql
	 * @param list
	 * @return
	 */
	public int executeInsert(String sql, List<Object> list) throws Exception {
		log(sql, list);
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = openConnection();
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					pst.setString(i + 1, list.get(i) != null ? list.get(i)
							.toString() : "");
				}
			}
			pst.executeUpdate();
			rs = pst.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
		} finally {
			close(pst, null, rs, conn);
		}
	}

	/**
	 * 
	 * @param sql
	 * @param list
	 * @throws SQLException
	 */
	public void Insert(String sql, List<Object> list) throws SQLException {
		log(sql, list);
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			conn = openConnection();
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					pst.setString(i + 1, list.get(i) != null ? list.get(i)
							.toString() : "");
				}
			}
			pst.executeUpdate();
		} finally {
			close(pst, null, rs, conn);
		}
	}

	/**
	 * 更新数据库数据
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public boolean update(String sql, List<Object> args) throws SQLException {
		log(sql, args);
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = openConnection();
			ps = conn.prepareStatement(sql);
			if (args != null && args.size() > 0) {
				for (int i = 0; i < args.size(); i++) {
					ps.setObject(i + 1, args.get(i));
				}
			}
			return ps.execute();
		} finally {
			close(ps, null, null, conn);
		}
	}

	/**
	 * 执行存储过程
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public List<Object[]> execProcs(String sql, List<Object> args)
			throws SQLException {
		log(sql, args);
		List<Object[]> list = new ArrayList<Object[]>();
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = openConnection();
			cs = conn.prepareCall(sql);
			if (args != null) {
				for (int i = 0; i < args.size(); i++) {
					cs.setObject(i + 1, args.get(i));
				}
			}
			if (cs.execute()) {
				rs = cs.getResultSet();
				while (rs.next()) {
					int count = rs.getMetaData().getColumnCount();
					Object[] obj = new Object[count];
					for (int i = 0; i < count; i++) {
						obj[i] = rs.getObject(i + 1);
					}
					list.add(obj);
				}
			}
			return list;
		} finally {
			cs.close();
			close(null, null, rs, conn);
		}
	}

	/**
	 * 查询数量
	 * 
	 * @param sql
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public int count(String sql, List<Object> args) throws SQLException {
		log(sql, args);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = openConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.size(); i++) {
					ps.setObject(i + 1, args.get(i));
				}
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} finally {
			close(ps, null, rs, conn);
		}
		return count;
	}

	/**
	 * @throws SQLException
	 * @Title: query
	 * @Description: 查询数据库数据
	 * @param @param sql
	 * @param @param args
	 * @param @return
	 * @return List<Object[]> 返回类型
	 * @throws
	 */
	public List<Object[]> query(String sql, List<Object> args)
			throws SQLException {
		log(sql, args);
		Connection conn = openConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
				ps.setObject(i + 1, args.get(i));
			}
		}
		ResultSet rs = ps.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData();
		List<Object[]> lists = new ArrayList<Object[]>();
		int count = metaData.getColumnCount();
		if (rs != null && !rs.isClosed()) {
			while (rs.next()) {
				Object[] obj = new Object[count];
				for (int i = 0; i < count; i++) {
					obj[i] = rs.getObject(i + 1);
				}
				lists.add(obj);
			}
		}
		close(ps, null, rs, conn);
		return lists;
	}

	/**
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 * @Title: query
	 * @Description: 查询数据库数据，并封装数据
	 * @param @param sql sql查询语句
	 * @param @param args 查询参数
	 * @param @param cla 字节码文件
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> query(String sql, List<Object> args, Class cla)
			throws SQLException, InstantiationException, IllegalAccessException {
		log(sql, args);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = openConnection();
			ps = conn.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.size(); i++) {
					ps.setObject(i + 1, args.get(i));
				}
			}
			rs = ps.executeQuery();
			ResultSetMetaData metaData = rs.getMetaData();
			List<Object[]> lists = new ArrayList<Object[]>();
			int count = metaData.getColumnCount();
			if (rs != null && !rs.isClosed()) {
				while (rs.next()) {
					Object[] obj = new Object[count];
					for (int i = 0; i < count; i++) {
						obj[i] = rs.getObject(i + 1);
					}
					lists.add(obj);
				}
			}
			List<Object> list = getBeanList(cla, lists, metaData);
			return list;
		} finally {
			close(ps, null, rs, conn);
		}
	}

	/**
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 * @Title: getBeanList
	 * @Description: 将数据库查询出来的结果封装到对象的集合中去
	 * @param @param cla
	 * @param @param lists
	 * @param @param metaData
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@SuppressWarnings("rawtypes")
	public List<Object> getBeanList(Class cla, List<Object[]> lists,
			ResultSetMetaData metaData) throws SQLException,
			InstantiationException, IllegalAccessException {
		List<Object> list = new ArrayList<Object>();
		Field[] fields = BeanUtil.getFileds(cla);
		for (Object[] objects : lists) {
			Object bean = cla.newInstance();
			for (int i = 0; i < metaData.getColumnCount(); i++) {
				String columnName = metaData.getColumnLabel(i + 1);
				// String columnName = metaData.getColumnName(i + 1);
				String columnValue = String.valueOf(objects[i]);
				// 将字段名称和字段值封装到bean对象中去
				for (Field field : fields) {
					field.setAccessible(true);
					String fieldName = field.getName();
					// 将表的"_"去除
					columnName = columnName.replace("_", "");
					if (fieldName.equalsIgnoreCase(columnName)) {
						BeanUtil.setProperty(field, bean, columnValue);
						break;
					}
				}
			}
			list.add(bean);
		}
		return list;
	}

	/**
	 * @throws SQLException
	 * @Title: executeInsertBatch
	 * @Description: 批处理添加
	 * @param @param sql
	 * @param @param lists
	 * @param @return
	 * @return int[] 主键id
	 * @throws
	 */
	public int[] executeInsertBatch(String sql, List<List<Object>> lists)
			throws SQLException {
		for (List<Object> list : lists) {
			log(sql, list);
		}
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int[] ids = new int[lists.size()];
		int count = 0;
		try {
			conn = openConnection();
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (List<Object> list : lists) {
				for (int i = 0; i < list.size(); i++) {
					pst.setString(i + 1, list.get(i) != null ? list.get(i)
							.toString() : "");
				}
				pst.addBatch();
			}
			pst.executeBatch();
			rs = pst.getGeneratedKeys();
			while (rs != null && rs.next()) {
				ids[count] = rs.getInt(1);
				count++;
			}
			return ids;
		} finally {
			close(pst, null, rs, conn);
		}
	}

	/**
	 * @Title: log
	 * @Description: 打印出sql和参数
	 * @param @param sql
	 * @param @param list
	 * @return void 返回类型
	 * @throws
	 */
	public static void log(String sql, List<Object> list) {
		StringBuffer buffer = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (Object object : list) {
				buffer.append(object + StringConstant.QUOTA);
			}
			log.info("SQL:" + sql + "参数:" + buffer.toString());
		}
	}
}
