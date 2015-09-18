package com.uugty.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.dao.RoadlineDao;
import com.uugty.app.domain.TCollectRoadline;
import com.uugty.app.domain.TMark;
import com.uugty.app.domain.TRoadline;
import com.uugty.app.domain.TUser;
import com.uugty.app.entity.CollectRoadlineEntity;
import com.uugty.app.entity.RoadlineSearchEntity;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.service.IRoadlineService;
import com.uugty.app.utils.SQLUtil;
import com.uugty.app.utils.StringUtil;
import com.uugty.app.web.form.RoadLineDescribeForm;
import com.uugty.app.web.form.RoadLineMarkForm;
import com.uugty.app.web.form.RoadLinePublishForm;
import com.uugty.app.web.servlet.RoadlineModifyServet.ModifyRoadlineEntity;

public class RoadlineServiceImpl implements IRoadlineService {
	private static final Logger log = Logger
			.getLogger(RoadlineServiceImpl.class);
	private static BaseDao dao = new BaseDao();
	private static RoadlineDao roadlineDao = new RoadlineDao();

	/**
	 * @Title: saveRoadLine
	 * @Description: 保存路线信息
	 * @param @param roadlins
	 * @param @param sessionUser
	 * @return String 返回类型
	 * @throws
	 */
	@Override
	public String saveRoadLine(RoadLinePublishForm roadLinePublishForm,
			TUser sessionUser) {
		List<List<Object>> lists = new ArrayList<List<Object>>();
		List<Object> list = new ArrayList<Object>();
		int[] describeIds = null;
		String roadlineId = null;
		try {
			if (roadLinePublishForm != null) {
				// 首先保存路线
				String sql = "INSERT INTO T_ROADLINE(roadline_user_id,roadline_title,roadline_price,roadline_content,roadline_goal_area,roadline_days,roadline_background,roadline_status,roadline_is_hot,roadline_create_date) VALUES(?,?,?,?,?,?,?,?,?,NOW())";
				list.add(sessionUser.getUserId());
				list.add(roadLinePublishForm.getRoadlineTitle());
				list.add(roadLinePublishForm.getRoadlinePrice());
				list.add(roadLinePublishForm.getRoadlineContent());
				list.add(roadLinePublishForm.getRoadlineGoalArea());
				list.add(roadLinePublishForm.getRoadlineDays());
				list.add(roadLinePublishForm.getRoadlineBackground());
				list.add(TRoadline.STATUS_REVIEW);
				String roadlineIsHot = getRoadlineIsHot(
						roadLinePublishForm.getRoadlineContent(),
						roadLinePublishForm.getRoadlineTitle(),
						roadLinePublishForm.getRoadlineGoalArea());
				list.add(roadlineIsHot);
				roadlineId = String.valueOf(dao.executeInsert(sql, list));

				// 批量保存图片的描述
				List<RoadLineDescribeForm> roadLineDecribes = roadLinePublishForm
						.getRoadlineDescribes();
				sql = "INSERT INTO T_ROADLINE_DESCRIBE(describe_roadline_id,describe_image,describe_time,describe_area,describe_create_date) VALUES(?,?,?,?,NOW())";
				if (roadLineDecribes != null && roadLineDecribes.size() > 0) {
					for (RoadLineDescribeForm roadLineDescribeForm : roadLineDecribes) {
						list = new ArrayList<Object>();
						list.add(roadlineId);
						list.add(roadLineDescribeForm.getDescribeImage());
						list.add(roadLineDescribeForm.getDescribeTime());
						list.add(roadLineDescribeForm.getDescribeArea());
						lists.add(list);
					}
					describeIds = dao.executeInsertBatch(sql, lists);
					// 批量保存标签
					lists.clear();
					sql = "INSERT INTO T_ROADLINE_DESCRIBE_MARK(mark_roadline_id,mark_describe_id,mark_content,mark_x,mark_y,mark_create_date) VALUES(?,?,?,?,?,NOW())";
					if (roadLineDecribes != null && roadLineDecribes.size() > 0) {
						for (int i = 0; i < roadLineDecribes.size(); i++) {
							int markDescribeId = describeIds[i];
							List<RoadLineMarkForm> decribeMarks = roadLineDecribes
									.get(i).getDescribeMarks();
							if (decribeMarks != null && decribeMarks.size() > 0) {
								for (RoadLineMarkForm roadLineMarkForm : decribeMarks) {
									String markContent = roadLineMarkForm
											.getMarkContent();
									// 如果标签内容为空 就不保存标签
									if (StringUtil.isNotEmpty(markContent)) {
										list = new ArrayList<Object>();
										list.add(roadlineId);
										list.add(markDescribeId);
										list.add(markContent);
										list.add(roadLineMarkForm.getMarkX());
										list.add(roadLineMarkForm.getMarkY());
										lists.add(list);
									}
								}
							}
						}
						if (lists.size() > 0) {
							dao.executeInsertBatch(sql, lists);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("保存路线出现异常", e);
			throw new RuntimeException("保存路线出现异常", e);
		}
		return roadlineId;
	}

	/**
	 * @throws SQLException
	 * @Title: getRoadlineIsHot
	 * @Description: 判断这条路线是否是热门城市路线
	 * @param @param roadlineContent
	 * @param @param roadlineTitle
	 * @param @param roadlineGoalArea
	 * @param @return
	 * @return String 返回类型
	 * @throws
	 */
	private String getRoadlineIsHot(String roadlineContent,
			String roadlineTitle, String roadlineGoalArea) throws SQLException {
		List<Object[]> markTitles = dao.query("SELECT mark_title FROM T_MARK",
				null);
		String roadline_is_hot = TRoadline.ROADLINE_IS_HOT_NO;
		for (Object[] objects : markTitles) {
			Object object = objects[0];
			if (object != null) {
				String title = object.toString();
				if (roadlineContent != null && !"".equals(roadlineContent)
						&& roadlineContent.contains(title)) {
					roadline_is_hot = TRoadline.ROADLINE_IS_HOT_YES;
					break;
				}
				if (roadlineTitle != null && !"".equals(roadlineTitle)
						&& roadlineTitle.contains(title)) {
					roadline_is_hot = TRoadline.ROADLINE_IS_HOT_YES;
					break;
				}
				if (roadlineGoalArea != null && !"".equals(roadlineGoalArea)
						&& roadlineGoalArea.contains(title)) {
					roadline_is_hot = TRoadline.ROADLINE_IS_HOT_YES;
					break;
				}
			}
		}
		return roadline_is_hot;
	}

	/**
	 * @Title: findRoadLineByUser
	 * @Description: 根据用户id来获取该用户发布的路线
	 * @param @param sessionUser
	 * @param @return
	 * @return List<TRoadline> 返回类型
	 * @throws
	 */
	@Override
	public List<TRoadline> findRoadLineByUser(TUser sessionUser) {
		String sql = "SELECT *FROM T_ROADLINE WHERE ROADLINE_USER_ID=?";
		List<Object> args = new ArrayList<Object>();
		List<TRoadline> roadlines = null;
		try {
			args.add(sessionUser.getUserId());
			List<Object> query = dao.query(sql, args, TRoadline.class);
			if (query != null && query.size() > 0) {
				roadlines = new ArrayList<TRoadline>();
				for (Object obj : query) {
					roadlines.add((TRoadline) obj);
				}
			}
		} catch (Exception e) {
			log.error("根据用户id 找到路线信息 出现异常", e);
			throw new RuntimeException("根据用户id 找到路线信息 出现异常", e);
		}
		return roadlines;
	}

	/**
	 * @Title: roadlineDelete
	 * @Description: 根据路线id 修改路线状态为已删除
	 * @param @param roadline
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void roadlineDelete(TRoadline roadline) {
		String sql = "UPDATE T_ROADLINE SET roadline_status=? WHERE roadline_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(TRoadline.STATUS_DROP);
		args.add(roadline.getRoadlineId());
		try {
			roadlineDao.update(sql, args);
		} catch (SQLException e) {
			log.error("删除路线 出现异常", e);
			throw new RuntimeException("删除路线 出现异常", e);
		}
	}

	/**
	 * @Title: findAllRoadLine
	 * @Description: 找到所有的路线，图片的描述 ，标签
	 * @param @param sessionUser
	 * @param @return
	 * @return RoadLinePublishForm 返回类型
	 * @throws
	 */
	@Override
	public List<RoadLinePublishForm> findAllRoadLine(TUser sessionUser) {
		String sql = "SELECT roadline_id,roadline_title,roadline_price,roadline_content,roadline_goal_area,describe_id,describe_image,describe_time,describe_area,mark_id,mark_content,mark_x,mark_y,roadline_days,roadline_background,roadline_status FROM T_ROADLINE r LEFT JOIN T_ROADLINE_DESCRIBE d ON(r.ROADLINE_ID=d.DESCRIBE_ROADLINE_ID) LEFT JOIN T_ROADLINE_DESCRIBE_MARK m ON(m.MARK_ROADLINE_ID=r.ROADLINE_ID) WHERE r.roadline_user_id=? AND r.roadline_status in('success','review','failure')";
		List<Object> args = new ArrayList<Object>();
		args.add(sessionUser.getUserId());
		List<Object[]> query = null;
		try {
			query = dao.query(sql, args);
		} catch (SQLException e) {
			log.error("找到所有路线信息 出现异常", e);
			throw new RuntimeException("找到所有路线信息 出现异常", e);
		}
		Map<String, Object> map = generateMap(query);
		return iteratorMap(map);
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
			map = new LinkedHashMap<String, Object>();
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
									markMap = new LinkedHashMap<String, Object>();
									markMap.put("markId", obj[9]);
									markMap.put("markContent", obj[10]);
									markMap.put("markX", obj[11]);
									markMap.put("markY", obj[12]);
								}
								describeMap.put(markId, markMap);
							}
						} else {
							describeMap = new LinkedHashMap<String, Object>();
							describeMap.put("describeId", obj[5]);
							describeMap.put("describeImage", obj[6]);
							describeMap.put("describeTime", obj[7]);
							describeMap.put("describeArea", obj[8]);
							if (obj[9] != null) {
								markMap = new LinkedHashMap<String, Object>();
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
					roadlineMap = new LinkedHashMap<String, Object>();
					roadlineMap.put("roadlineId", obj[0]);
					roadlineMap.put("roadlineTitle", obj[1]);
					roadlineMap.put("roadlinePrice", obj[2]);
					roadlineMap.put("roadlineContent", obj[3]);
					roadlineMap.put("roadlineGoalArea", obj[4]);
					roadlineMap.put("roadlineDays", obj[13]);
					roadlineMap.put("roadlineBackground", obj[14]);
					roadlineMap.put("roadlineStatus", obj[15]);
					if (obj[5] != null) {
						describeMap = new LinkedHashMap<String, Object>();
						describeMap.put("describeId", obj[5]);
						describeMap.put("describeImage", obj[6]);
						describeMap.put("describeTime", obj[7]);
						describeMap.put("describeArea", obj[8]);
						if (obj[9] != null) {
							markMap = new LinkedHashMap<String, Object>();
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
	private List<RoadLinePublishForm> iteratorMap(Map<String, Object> map) {
		Map<String, Object> roadlineMap = null;
		Map<String, Object> describeMap = null;
		Map<String, Object> markMap = null;

		RoadLinePublishForm roadLinePublishForm = null;
		RoadLineDescribeForm roadLineDescribeForm = null;
		RoadLineMarkForm roadLineMarkForm = null;

		List<RoadLinePublishForm> roadLinePublishs = new ArrayList<RoadLinePublishForm>();
		List<RoadLineDescribeForm> roadLineDescribes = null;
		List<RoadLineMarkForm> roadLineMarks = null;
		if (map != null && map.size() > 0) {
			for (String key : map.keySet()) {
				roadLinePublishForm = new RoadLinePublishForm();
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
					case "roadlineDays":
						roadLinePublishForm
								.setRoadlineDays(roadlineMapValue != null ? roadlineMapValue
										.toString() : "");
						break;
					case "roadlineBackground":
						roadLinePublishForm
								.setRoadlineBackground(roadlineMapValue != null ? roadlineMapValue
										.toString() : "");
					case "roadlineStatus":
						roadLinePublishForm
								.setRoadlineStatus(roadlineMapValue != null ? roadlineMapValue
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
				roadLinePublishs.add(roadLinePublishForm);
			}
		}
		return roadLinePublishs;
	}

	/**
	 * @Title: getRoadlineById
	 * @Description: 根据路线id来获取路线信息
	 * @param @param orderRoadlineId
	 * @param @return
	 * @return TRoadline 返回类型
	 * @throws
	 */
	@Override
	public TRoadline getRoadlineById(int orderRoadlineId) {
		String sql = "SELECT *FROM T_ROADLINE WHERE roadline_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(orderRoadlineId);
		List<Object> query = null;
		try {
			query = dao.query(sql, args, TRoadline.class);
		} catch (InstantiationException e) {
			log.error("根据路线id来获取路线信息出现异常", e);
			throw new RuntimeException("根据路线id来获取路线信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据路线id来获取路线信息出现异常", e);
			throw new RuntimeException("根据路线id来获取路线信息出现异常", e);
		} catch (SQLException e) {
			log.error("根据路线id来获取路线信息出现异常", e);
			throw new RuntimeException("根据路线id来获取路线信息出现异常", e);
		}
		return query != null && query.size() > 0 ? (TRoadline) query.get(0)
				: null;
	}

	/**
	 * @Title: searchRoadlineList
	 * @Description: 路线检索
	 * @param @param mark
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> searchRoadlineList(TMark mark) {
		StringBuilder builder = new StringBuilder();
		String title = mark.getMarkTitle();
		List<Object> args = new ArrayList<Object>();
		String sql = "";

		int userTourValidate = mark.getUserTourValidate();
		if (userTourValidate == 1) {
			builder.append(" AND u.user_tour_validate=? ");
			args.add(userTourValidate);
		}

		int carValidate = mark.getUserCarValidate();
		if (carValidate == 1) {
			builder.append(" AND u.user_car_validate=? ");
			args.add(carValidate);
		}

		int userSex = mark.getUserSex();
		if (userSex == 1 || userSex == 2) {
			builder.append(" AND u.user_sex=?");
			args.add(userSex);
		}

		switch (mark.getMarkSearchType()) {
		case TMark.MARK_SEARCH_GOAL:
			builder.append(" AND r.roadline_goal_area LIKE ?");
			args.add("%" + title + "%");
			break;
		case TMark.MARK_SEARCH_TITLE:
			builder.append(" AND r.roadline_content LIKE ?");
			args.add("%" + title + "%");
			break;
		case TMark.MARK_SEARCH_CONTENT:
			builder.append(" AND r.roadline_title LIKE ?");
			args.add("%" + title + "%");
			break;
		case TMark.MARK_SEARCH_MIX:
			builder.append(" AND (r.roadline_title LIKE ? OR r.roadline_goal_area LIKE ? OR r.roadline_content LIKE ?)");
			args.add("%" + title + "%");
			args.add("%" + title + "%");
			args.add("%" + title + "%");
			break;
		default:
			throw new IllegalArgumentException("路线检索参数传递错误");
		}

		String sort = mark.getSort();
		switch (sort) {
		// 综合评分
		case "totalIndex":
			sql = "SELECT r.roadline_id,r.roadline_title,r.roadline_price,r.roadline_background,u.user_avatar,(SELECT GROUP_CONCAT(rd.describe_image) FROM T_ROADLINE_DESCRIBE rd WHERE rd.describe_roadline_id=r.roadline_id) roadline_images,(SELECT SUM(c.total_index)/COUNT(c.comment_id) FROM T_COMMENT c WHERE r.roadline_user_id=c.commented_user_id) totalIndex FROM T_ROADLINE r LEFT JOIN T_USER u ON(u.user_id=r.roadline_user_id) WHERE r.roadline_status='success' "
					+ builder.toString()
					+ " ORDER BY totalIndex,r.roadline_create_date DESC";
			break;
		// 成交量
		case "orderCount":
			sql = "SELECT r.roadline_id,r.roadline_title,r.roadline_price,r.roadline_background,u.user_avatar,(SELECT GROUP_CONCAT(rd.describe_image) FROM T_ROADLINE_DESCRIBE rd WHERE rd.describe_roadline_id=r.roadline_id) roadline_images,(SELECT COUNT(o.order_id) FROM T_ORDER o WHERE o.order_tour_user_id=r.roadline_user_id AND o.order_status='order_success') orderCount FROM T_ROADLINE r LEFT JOIN T_USER u ON(u.user_id=r.roadline_user_id) WHERE r.roadline_status='success' "
					+ builder.toString()
					+ " ORDER BY orderCount,r.roadline_create_date DESC";
			break;
		default:
			sql = "SELECT r.roadline_id,r.roadline_title,r.roadline_price,r.roadline_background,u.user_avatar,(SELECT GROUP_CONCAT(rd.describe_image) FROM T_ROADLINE_DESCRIBE rd WHERE rd.describe_roadline_id=r.roadline_id) roadline_images FROM T_ROADLINE r JOIN T_USER u ON(u.user_id=r.roadline_user_id) WHERE r.roadline_status='success' "
					+ builder.toString()
					+ " ORDER BY r.roadline_create_date DESC";
			break;
		}

		try {
			return dao.query(sql, args, RoadlineSearchEntity.class);
		} catch (InstantiationException e) {
			log.error("找到所有路线信息 出现异常", e);
			throw new RuntimeException("找到所有路线信息 出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("找到所有路线信息 出现异常", e);
			throw new RuntimeException("找到所有路线信息 出现异常", e);
		} catch (SQLException e) {
			log.error("找到所有路线信息 出现异常", e);
			throw new RuntimeException("找到所有路线信息 出现异常", e);
		}
	}

	/**
	 * @Title: getRoadlineDetailById
	 * @Description: 获取路线的详细信息 包括路线描述 路线标签
	 * @param @param roadlineId
	 * @param @return
	 * @return RoadLinePublishForm 返回类型
	 * @throws
	 */
	@Override
	public RoadLinePublishForm getRoadlineDetailById(int roadlineId) {
		String sql = "SELECT roadline_id,roadline_title,roadline_price,roadline_content,roadline_goal_area,describe_id,describe_image,describe_time,describe_area,mark_id,mark_content,mark_x,mark_y,roadline_days,roadline_background,roadline_status FROM T_ROADLINE r LEFT JOIN T_ROADLINE_DESCRIBE d ON(r.ROADLINE_ID=d.DESCRIBE_ROADLINE_ID) LEFT JOIN T_ROADLINE_DESCRIBE_MARK m ON(m.MARK_DESCRIBE_ID=d.describe_id) WHERE r.roadline_id=? ORDER BY cast(substring_index(describe_time,'_',1) as unsigned integer),substring_index(describe_time,'_',-1) ASC";
		List<Object> args = new ArrayList<Object>();
		args.add(roadlineId);
		List<Object[]> query = null;
		try {
			query = dao.query(sql, args);
		} catch (SQLException e) {
			log.error("找到所有路线信息 出现异常", e);
			throw new RuntimeException("找到所有路线信息 出现异常", e);
		}
		Map<String, Object> map = generateMap(query);
		List<RoadLinePublishForm> iteratorMap = iteratorMap(map);
		return iteratorMap != null && iteratorMap.size() > 0 ? iteratorMap
				.get(0) : null;
	}

	/**
	 * @Title: deleteCollectRoadlineById
	 * @Description: 根据收藏id ---删除收藏的路线
	 * @param @param collectId
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void deleteCollectRoadlineById(int collectId) {
		String sql = "DELETE FROM T_COLLECT_ROADLINE WHERE collect_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(collectId);
		try {
			dao.update(sql, args);
		} catch (SQLException e) {
			log.error("删除收藏路线 出现异常", e);
			throw new RuntimeException("删除收藏路线 出现异常", e);
		}
	}

	/**
	 * @Title: saveCollectRoadline
	 * @Description: 保存收藏的路线
	 * @param @param collectRoadline
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveCollectRoadline(TCollectRoadline collectRoadline) {
		collectRoadline.setCollectCreateDate(new Date());
		SQLEntity insertSQL = SQLUtil.insertSQL(collectRoadline);
		try {
			return dao.executeInsert(insertSQL.getParameter(),
					insertSQL.getList());
		} catch (Exception e) {
			log.error("删除收藏路线 出现异常", e);
			throw new RuntimeException("删除收藏路线 出现异常", e);
		}
	}

	/**
	 * @Title: getCollectRoadlineByUserId
	 * @Description: 获取收藏的的路线信息
	 * @param @param userId
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getCollectRoadlineByUserId(String userId) {
		String sql = "SELECT cr.collect_id,cr.collect_title,cr.collect_create_date,cr.collect_roadline_id roadline_id,(SELECT GROUP_CONCAT(rd.describe_image) FROM T_ROADLINE_DESCRIBE rd WHERE rd.describe_roadline_id=cr.collect_roadline_id) roadline_images FROM T_COLLECT_ROADLINE cr WHERE cr.collect_user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		try {
			return dao.query(sql, args, CollectRoadlineEntity.class);
		} catch (InstantiationException e) {
			log.error("获取收藏列表出现异常", e);
			throw new RuntimeException("获取收藏列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取收藏列表出现异常", e);
			throw new RuntimeException("获取收藏列表出现异常", e);
		} catch (SQLException e) {
			log.error("获取收藏列表出现异常", e);
			throw new RuntimeException("获取收藏列表出现异常", e);
		}
	}

	/**
	 * @Title: getRoadlineReviewedById
	 * @Description:判断路线是否被预定
	 * @param @param roadlineId
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	@Override
	public boolean getRoadlineReviewedById(int roadlineId) {
		String sql = "SELECT o.order_id FROM T_ORDER o  WHERE o.order_user_id=? AND order_status IN('order_create')";
		List<Object> args = new ArrayList<Object>();
		args.add(roadlineId);
		try {
			List<Object[]> query = dao.query(sql, args);
			return (query != null && query.size() > 0) ? true : false;
		} catch (SQLException e) {
			log.error("判断路线是否被预定出现异常", e);
			throw new RuntimeException("判断路线是否被预定出现异常", e);
		}
	}

	/**
	 * @Title: deleteRoadlineByUserId
	 * @Description:将路线标识未已删除
	 * @param @param userId
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	@Override
	public void dropRoadlineByUserId(int roadlineId) {
		roadlineDao.dropRoadlineByUserId(roadlineId);
	}

	/**
	 * @Title: deleteRoadlineByUserId
	 * @Description:删除用户的路线
	 * @param @param userId
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	@Override
	public void deleteRoadlineByUserId(String userId) {
		String sql = "DELETE FROM T_ROADLINE WHERE roadline_user_id=? AND roadline_id NOT IN(SELECT order_roadline_id FROM T_ORDER WHERE order_user_id=?)";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		args.add(userId);
		try {
			roadlineDao.update(sql, args);
		} catch (SQLException e) {
			log.error("删除用户的路线出现异常", e);
			throw new RuntimeException("删除用户的路线出现异常", e);
		}
	}

	/**
	 * @Title: getRoadlineReviewedById
	 * @Description:判断路线是否被预定
	 * @param @param userId
	 * @param @return
	 * @return boolean 返回类型
	 * @throws
	 */
	@Override
	public ModifyRoadlineEntity getRoadlineReviewedByUserId(String userId) {
		String sql = "SELECT o.order_id,r.roadline_id FROM  T_ROADLINE r LEFT JOIN T_ORDER o ON(o.order_roadline_id=r.roadline_id AND o.order_status='order_create')  WHERE r.roadline_user_id=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		try {
			List<Object> query = dao.query(sql, args,
					ModifyRoadlineEntity.class);
			return (query != null && query.size() > 0) ? (ModifyRoadlineEntity) query
					.get(0) : null;
		} catch (Exception e) {
			log.error("判断路线是否被预定出现异常", e);
			throw new RuntimeException("判断路线是否被预定出现异常", e);
		}
	}
}
