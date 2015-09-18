package com.uugty.app.web.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.uugty.app.web.form.RoadLineDescribeForm;
import com.uugty.app.web.form.RoadLineMarkForm;
import com.uugty.app.web.form.RoadLinePublishForm;

public class RoadLinePublishServletTest {
	private static final String driver = "com.mysql.jdbc.Driver";
	private static final String url = "jdbc:mysql://192.168.1.123:3306/uugty?useUnicode=true&amp;characterEncoding=utf8";
	private static final String user = "root";
	private static final String password = "123456";

	public static Connection openConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public boolean update(String sql, List<Object> args) {
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
		} catch (Exception e) {
			throw new RuntimeException("", e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int[] insertBatch(String sql, List<List<Object>> lists) {
		int[] ids = new int[120];
		int count = 0;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection conn = null;
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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}

	@Test
	public void insertBatchTest() {
		String sql = "INSERT INTO TEST_BATCH(name,password) values(?,?)";
		List<List<Object>> lists = new ArrayList<List<Object>>();
		for (int i = 0; i < 10; i++) {
			List<Object> list = new ArrayList<Object>();
			list.add("name" + i);
			list.add("password" + i);
			lists.add(list);
		}
		int[] insertBatch = insertBatch(sql, lists);
		for (int i : insertBatch) {
			if (i > 0)
				System.out.println(i);
		}
	}

	@Test
	public void deleteBatch() {
		String sql = "DELETE FROM T_ROADLINE WHERE ROADLINE_ID=?;"
				+ "   DELETE FROM T_ROADLINE_DESCRIBE WHERE DESCRIBE_ROADLINE_ID=?;"
				+ "   DELETE FROM T_MARK WHERE MARK_ROADLINE_ID=?";
		List<Object> args = new ArrayList<Object>();
		args.add(3);
		args.add(3);
		args.add(3);
		update(sql, args);
	}

	@Test
	public void roadlineDeleteStatement() {
		Connection conn = null;
		Statement st = null;
		long start = System.currentTimeMillis();
		try {
			conn = openConnection();
			st = conn.createStatement();
			int roadlineId = 3;

			String sql = "DELETE FROM T_ROADLINE WHERE ROADLINE_ID="
					+ roadlineId;
			st.addBatch(sql);

			sql = "DELETE FROM T_ROADLINE_DESCRIBE WHERE DESCRIBE_ROADLINE_ID="
					+ roadlineId;
			st.addBatch(sql);

			sql = "DELETE FROM T_MARK WHERE MARK_ROADLINE_ID=" + roadlineId;
			st.addBatch(sql);

			st.executeBatch();
			long end = System.currentTimeMillis();
			System.err.println((end - start));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void roadlineDeletePrepareStatement() {
		Connection conn = null;
		PreparedStatement pst = null;
		long start = System.currentTimeMillis();
		try {
			conn = openConnection();
			int roadlineId = 6;

			String sql = "DELETE FROM T_ROADLINE WHERE ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			pst.execute();
			pst.close();

			sql = "DELETE FROM T_ROADLINE_DESCRIBE WHERE DESCRIBE_ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			pst.execute();
			pst.close();

			sql = "DELETE FROM T_MARK WHERE MARK_ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			pst.execute();
			pst.close();

			long end = System.currentTimeMillis();
			System.err.println((end - start));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void generateMap() {

		RoadLinePublishForm roadLinePublishForm = new RoadLinePublishForm();
		RoadLineDescribeForm roadLineDescribeForm = null;
		RoadLineMarkForm roadLineMarkForm = null;

		List<RoadLineDescribeForm> roadLineDescribes = new ArrayList<RoadLineDescribeForm>();
		List<RoadLineMarkForm> roadLineMarks = new ArrayList<RoadLineMarkForm>();

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> roadlineMap = null;
		Map<String, Object> describeMap = null;
		Map<String, Object> markMap = null;
		List<Object[]> query = createMap();
		if (query != null && query.size() > 0) {
			for (Object[] obj : query) {
				String roadlineId = obj[0].toString();
				Object roadline = map.get(roadlineId);
				if (roadline != null) {
					roadlineMap = (Map<String, Object>) roadline;
					if (obj[5] != null) {
						String describeId = obj[5].toString();
						Object describe = roadlineMap.get(describeId);
						if (describe != null) {
							describeMap = (Map<String, Object>) describe;
							if (obj[9] != null) {
								String markId = obj[9].toString();
								Object mark = describeMap.get(markId);
								if (mark != null) {
									continue;
								} else {
									markMap = new HashMap<String, Object>();
									markMap.put("markId", obj[9]);
									markMap.put("markContent", obj[10]);
									markMap.put("markX", obj[11]);
									markMap.put("markY", obj[12]);
								}
								describeMap.put(markId, markMap);
							}
						} else {
							describeMap = new HashMap<String, Object>();
							describeMap.put("describeId", obj[5]);
							describeMap.put("describeImage", obj[6]);
							describeMap.put("describeTime", obj[7]);
							describeMap.put("describeArea", obj[8]);
							if (obj[9] != null) {
								markMap = new HashMap<String, Object>();
								markMap.put("markId", obj[9]);
								markMap.put("markContent", obj[10]);
								markMap.put("markX", obj[11]);
								markMap.put("markY", obj[12]);
								describeMap.put(obj[9].toString(), markMap);
							}
						}
						roadlineMap.put(describeId, describeMap);
					}
				} else {
					roadlineMap = new HashMap<String, Object>();
					roadlineMap.put("roadlineId", obj[0]);
					roadlineMap.put("roadlineTitle", obj[1]);
					roadlineMap.put("roadlinePrice", obj[2]);
					roadlineMap.put("roadlineContent", obj[3]);
					roadlineMap.put("roadlineGoalArea", obj[4]);
					if (obj[5] != null) {
						describeMap = new HashMap<String, Object>();
						describeMap.put("describeId", obj[5]);
						describeMap.put("describeImage", obj[6]);
						describeMap.put("describeTime", obj[7]);
						describeMap.put("describeArea", obj[8]);
						if (obj[9] != null) {
							markMap = new HashMap<String, Object>();
							markMap.put("markId", obj[9]);
							markMap.put("markContent", obj[10]);
							markMap.put("markX", obj[11]);
							markMap.put("markY", obj[12]);
							describeMap.put(obj[9].toString(), markMap);
						}
						roadlineMap.put(obj[5].toString(), describeMap);
					}
				}
				map.put(roadlineId, roadlineMap);
			}
		}
		// 遍历map
		for (String key : map.keySet()) {
			roadlineMap = (Map<String, Object>) map.get(key);
			roadLineDescribes = new ArrayList<RoadLineDescribeForm>();
			for (String roadlineMapKey : roadlineMap.keySet()) {
				Object roadlineMapValue = roadlineMap.get(roadlineMapKey);
				switch (roadlineMapKey) {
				case "roadlineId":
					roadLinePublishForm
							.setRoadlineId(roadlineMapValue != null ? Integer
									.parseInt(roadlineMapValue.toString()) : 0);
					break;
				case "roadlineTitle":
					roadLinePublishForm
							.setRoadlineTitle(roadlineMapValue != null ? roadlineMapValue
									.toString() : "");
					break;
				case "roadlinePrice":
					roadLinePublishForm
							.setRoadlinePrice(roadlineMapValue != null ? Float
									.parseFloat(roadlineMapValue.toString())
									: 0.0);
					break;
				case "roadlineContent":
					roadLinePublishForm
							.setRoadlineContent(roadlineMapValue != null ? roadlineMapValue
									.toString() : "");
					break;
				case "roadlineGoalArea":
					roadLinePublishForm
							.setRoadlineGoalArea(roadlineMapValue != null ? roadlineMapValue
									.toString() : "");
					break;
				default:
					describeMap = (Map<String, Object>) roadlineMapValue;
					// 遍历describe
					roadLineDescribeForm = new RoadLineDescribeForm();
					roadLineMarks = new ArrayList<RoadLineMarkForm>();
					for (String describeMapKey : describeMap.keySet()) {
						Object describeMapValue = describeMap
								.get(describeMapKey);
						switch (describeMapKey) {
						case "describeId":
							roadLineDescribeForm
									.setDescribeId(describeMapValue != null ? Integer
											.parseInt(describeMapValue
													.toString()) : 0);
							break;
						case "describeImage":
							roadLineDescribeForm
									.setDescribeImage(describeMapValue != null ? describeMapValue
											.toString() : "");
							break;
						case "describeTime":
							roadLineDescribeForm
									.setDescribeTime(describeMapValue != null ? describeMapValue
											.toString() : "");
							break;
						case "describeArea":
							roadLineDescribeForm
									.setDescribeArea(describeMapValue != null ? describeMapValue
											.toString() : "");
							break;
						default:
							markMap = (Map<String, Object>) describeMapValue;
							roadLineMarkForm = new RoadLineMarkForm();
							for (String markMapKey : markMap.keySet()) {
								Object markMapValue = markMap.get(markMapKey);
								switch (markMapKey) {
								case "markId":
									roadLineMarkForm
											.setMarkId(markMapValue != null ? Integer
													.parseInt(markMapValue
															.toString()) : 0);
									break;
								case "markContent":
									roadLineMarkForm
											.setMarkContent(markMapValue != null ? markMapValue
													.toString() : "");
									break;
								case "markX":
									roadLineMarkForm
											.setMarkX(markMapValue != null ? Double
													.parseDouble(markMapValue
															.toString()) : 0.0);
									break;
								case "markY":
									roadLineMarkForm
											.setMarkY(markMapValue != null ? Double
													.parseDouble(markMapValue
															.toString()) : 0.0);
									break;
								}
							}
							roadLineMarks.add(roadLineMarkForm);
							break;
						}
					}
					roadLineDescribeForm.setDescribeMarks(roadLineMarks);
					roadLineDescribes.add(roadLineDescribeForm);
					break;
				}
			}
			roadLinePublishForm.setRoadlineDescribes(roadLineDescribes);
			System.out.println(JSONObject.fromObject(roadLinePublishForm));
		}
	}

	private List<Object[]> createMap() {
		List<Object[]> query = new ArrayList<Object[]>();
		Object[] obj = new Object[13];
		obj[0] = "1";
		obj[1] = "我的路线";
		obj[2] = "123";
		obj[3] = "我的内容";
		obj[4] = "郑州";
		obj[5] = "1";
		obj[6] = "images/life/i.png";
		obj[7] = "09:36";
		obj[8] = "北京";
		obj[9] = "1";
		obj[10] = "爱";
		obj[11] = "23.12";
		obj[12] = "36.21";
		query.add(obj);

		obj = new Object[13];
		obj[0] = "1";
		obj[1] = "我的路线2";
		obj[2] = "121";
		obj[3] = "我的内容2";
		obj[4] = "高新区";
		obj[5] = "1";
		obj[6] = "images/life/ty.png";
		obj[7] = "09:37";
		obj[8] = "北京昌平";
		obj[9] = "2";
		obj[10] = "爱2";
		obj[11] = "23.12";
		obj[12] = "36.21";
		query.add(obj);

		obj = new Object[13];
		obj[0] = "1";
		obj[1] = "我的路线3";
		obj[2] = "121";
		obj[3] = "我的内容3";
		obj[4] = "高新区";
		obj[5] = "1";
		obj[6] = "images/life/ty.png";
		obj[7] = "09:37";
		obj[8] = "北京昌平";
		obj[9] = "3";
		obj[10] = "爱3";
		obj[11] = "23.12";
		obj[12] = "36.21";
		query.add(obj);

		obj = new Object[13];
		obj[0] = "1";
		obj[1] = "我的路线2";
		obj[2] = "121";
		obj[3] = "我的内容4";
		obj[4] = "高新区";
		obj[5] = "2";
		obj[6] = "images/life/ty.png";
		obj[7] = "09:37";
		obj[8] = "北京昌平";
		obj[9] = "4";
		obj[10] = "爱4";
		obj[11] = "23.12";
		obj[12] = "36.21";
		query.add(obj);

		obj = new Object[13];
		obj[0] = "1";
		obj[1] = "我的路线5";
		obj[2] = "121";
		obj[3] = "我的内容5";
		obj[4] = "高新区";
		obj[5] = "3";
		obj[6] = "images/life/ty2222222222.png";
		obj[7] = "09:37";
		obj[8] = "北京昌平";
		obj[9] = "4";
		obj[10] = "爱5";
		obj[11] = "23.12";
		obj[12] = "36.21";
		query.add(obj);
		return query;
	}

	/**
	 * @Title: generateMap
	 * @Description: 从对象数组中将记录生成map
	 * @param @param query
	 * @param @return
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> generateMap(List<Object[]> query) {
		Map<String, Object> map = null;
		Map<String, Object> roadlineMap = null;
		Map<String, Object> describeMap = null;
		Map<String, Object> markMap = null;
		if (query != null && query.size() > 0) {
			map = new HashMap<String, Object>();
			for (Object[] obj : query) {
				String roadlineId = obj[0].toString();
				Object roadline = map.get(roadlineId);
				if (roadline != null) {
					roadlineMap = (Map<String, Object>) roadline;
					if (obj[5] != null) {
						String describeId = obj[5].toString();
						Object describe = roadlineMap.get(describeId);
						if (describe != null) {
							describeMap = (Map<String, Object>) describe;
							if (obj[9] != null) {
								String markId = obj[9].toString();
								Object mark = describeMap.get(markId);
								if (mark != null) {
									continue;
								} else {
									markMap = new HashMap<String, Object>();
									markMap.put("markId", obj[9]);
									markMap.put("markContent", obj[10]);
									markMap.put("markX", obj[11]);
									markMap.put("markY", obj[12]);
								}
								describeMap.put(markId, markMap);
							}
						} else {
							describeMap = new HashMap<String, Object>();
							describeMap.put("describeId", obj[5]);
							describeMap.put("describeImage", obj[6]);
							describeMap.put("describeTime", obj[7]);
							describeMap.put("describeArea", obj[8]);
							if (obj[9] != null) {
								markMap = new HashMap<String, Object>();
								markMap.put("markId", obj[9]);
								markMap.put("markContent", obj[10]);
								markMap.put("markX", obj[11]);
								markMap.put("markY", obj[12]);
								describeMap.put(obj[9].toString(), markMap);
							}
						}
						roadlineMap.put(describeId, describeMap);
					}
				} else {
					roadlineMap = new HashMap<String, Object>();
					roadlineMap.put("roadlineId", obj[0]);
					roadlineMap.put("roadlineTitle", obj[1]);
					roadlineMap.put("roadlinePrice", obj[2]);
					roadlineMap.put("roadlineContent", obj[3]);
					roadlineMap.put("roadlineGoalArea", obj[4]);
					if (obj[5] != null) {
						describeMap = new HashMap<String, Object>();
						describeMap.put("describeId", obj[5]);
						describeMap.put("describeImage", obj[6]);
						describeMap.put("describeTime", obj[7]);
						describeMap.put("describeArea", obj[8]);
						if (obj[9] != null) {
							markMap = new HashMap<String, Object>();
							markMap.put("markId", obj[9]);
							markMap.put("markContent", obj[10]);
							markMap.put("markX", obj[11]);
							markMap.put("markY", obj[12]);
							describeMap.put(obj[9].toString(), markMap);
						}
						roadlineMap.put(obj[5].toString(), describeMap);
					}
				}
				map.put(roadlineId, roadlineMap);
			}
		}
		return map;
	}

	/**
	 * @Title: iteratorMap
	 * @Description: 遍历map集合 封装实体对象
	 * @param @param map
	 * @param @return
	 * @return RoadLinePublishForm 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private RoadLinePublishForm iteratorMap(Map<String, Object> map) {
		Map<String, Object> roadlineMap = null;
		Map<String, Object> describeMap = null;
		Map<String, Object> markMap = null;

		RoadLinePublishForm roadLinePublishForm = null;
		RoadLineDescribeForm roadLineDescribeForm = null;
		RoadLineMarkForm roadLineMarkForm = null;

		List<RoadLineDescribeForm> roadLineDescribes = new ArrayList<RoadLineDescribeForm>();
		List<RoadLineMarkForm> roadLineMarks = new ArrayList<RoadLineMarkForm>();
		if (map != null && map.size() > 0) {
			roadLinePublishForm = new RoadLinePublishForm();
			for (String key : map.keySet()) {
				roadlineMap = (Map<String, Object>) map.get(key);
				roadLineDescribes = new ArrayList<RoadLineDescribeForm>();
				for (String roadlineMapKey : roadlineMap.keySet()) {
					Object roadlineMapValue = roadlineMap.get(roadlineMapKey);
					switch (roadlineMapKey) {
					case "roadlineId":
						roadLinePublishForm
								.setRoadlineId(roadlineMapValue != null ? Integer
										.parseInt(roadlineMapValue.toString())
										: 0);
						break;
					case "roadlineTitle":
						roadLinePublishForm
								.setRoadlineTitle(roadlineMapValue != null ? roadlineMapValue
										.toString() : "");
						break;
					case "roadlinePrice":
						roadLinePublishForm
								.setRoadlinePrice(roadlineMapValue != null ? Float
										.parseFloat(roadlineMapValue.toString())
										: 0.0);
						break;
					case "roadlineContent":
						roadLinePublishForm
								.setRoadlineContent(roadlineMapValue != null ? roadlineMapValue
										.toString() : "");
						break;
					case "roadlineGoalArea":
						roadLinePublishForm
								.setRoadlineGoalArea(roadlineMapValue != null ? roadlineMapValue
										.toString() : "");
						break;
					default:
						describeMap = (Map<String, Object>) roadlineMapValue;
						// 遍历describe
						roadLineDescribeForm = new RoadLineDescribeForm();
						roadLineMarks = new ArrayList<RoadLineMarkForm>();
						for (String describeMapKey : describeMap.keySet()) {
							Object describeMapValue = describeMap
									.get(describeMapKey);
							switch (describeMapKey) {
							case "describeId":
								roadLineDescribeForm
										.setDescribeId(describeMapValue != null ? Integer
												.parseInt(describeMapValue
														.toString()) : 0);
								break;
							case "describeImage":
								roadLineDescribeForm
										.setDescribeImage(describeMapValue != null ? describeMapValue
												.toString() : "");
								break;
							case "describeTime":
								roadLineDescribeForm
										.setDescribeTime(describeMapValue != null ? describeMapValue
												.toString() : "");
								break;
							case "describeArea":
								roadLineDescribeForm
										.setDescribeArea(describeMapValue != null ? describeMapValue
												.toString() : "");
								break;
							default:
								markMap = (Map<String, Object>) describeMapValue;
								roadLineMarkForm = new RoadLineMarkForm();
								for (String markMapKey : markMap.keySet()) {
									Object markMapValue = markMap
											.get(markMapKey);
									switch (markMapKey) {
									case "markId":
										roadLineMarkForm
												.setMarkId(markMapValue != null ? Integer
														.parseInt(markMapValue
																.toString())
														: 0);
										break;
									case "markContent":
										roadLineMarkForm
												.setMarkContent(markMapValue != null ? markMapValue
														.toString() : "");
										break;
									case "markX":
										roadLineMarkForm
												.setMarkX(markMapValue != null ? Double
														.parseDouble(markMapValue
																.toString())
														: 0.0);
										break;
									case "markY":
										roadLineMarkForm
												.setMarkY(markMapValue != null ? Double
														.parseDouble(markMapValue
																.toString())
														: 0.0);
										break;
									}
								}
								roadLineMarks.add(roadLineMarkForm);
								break;
							}
						}
						roadLineDescribeForm.setDescribeMarks(roadLineMarks);
						roadLineDescribes.add(roadLineDescribeForm);
						break;
					}
				}
				roadLinePublishForm.setRoadlineDescribes(roadLineDescribes);
			}
		}
		return roadLinePublishForm;
	}

	@Test
	public void test() {
		List<Object[]> query = createMap();
		Map<String, Object> map = generateMap(query);
		RoadLinePublishForm roadLinePublishForm = iteratorMap(map);
		System.out.println(JSONObject.fromObject(roadLinePublishForm));
	}
}
