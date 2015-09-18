package com.uugty.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.domain.TMark;
import com.uugty.app.domain.TRoadline;

/**
 * @ClassName: RoadlineDao
 * @Description: 路线dao
 * @author ganliang
 * @date 2015年7月7日 上午11:19:44
 */
public class RoadlineDao extends BaseDao {

	private static final Logger log = Logger.getLogger(RoadlineDao.class);

	/**
	 * @Title: roadlineDelete
	 * @Description: 删除路线 通过statement批处理
	 * @param @param roadline
	 * @return void 返回类型
	 * @throws
	 */
	public void roadlineDeleteByStatement(TRoadline roadline) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = openConnection();
			st = conn.createStatement();
			int roadlineId = roadline.getRoadlineId();

			String sql = "DELETE FROM T_ROADLINE WHERE ROADLINE_ID="
					+ roadlineId;
			st.addBatch(sql);
			log.info(sql);

			sql = "DELETE FROM T_ROADLINE_DESCRIBE WHERE DESCRIBE_ROADLINE_ID="
					+ roadlineId;
			st.addBatch(sql);
			log.info(sql);

			sql = "DELETE FROM T_MARK WHERE MARK_ROADLINE_ID=" + roadlineId;
			st.addBatch(sql);
			log.info(sql);

			st.executeBatch();

		} catch (SQLException e) {
			log.error("通过statement来删除路线!", e);
			throw new RuntimeException("通过statement来删除路线!", e);
		} finally {
			close(null, st, null, conn);
		}

	}

	public void roadlineDeleteByPreparedStatement(TRoadline roadline) {
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = openConnection();
			int roadlineId = roadline.getRoadlineId();

			String sql = "DELETE FROM T_ROADLINE WHERE ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			log.info(sql + "参数:" + roadlineId);
			pst.execute();
			pst.close();

			sql = "DELETE FROM T_ROADLINE_DESCRIBE WHERE DESCRIBE_ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			log.info(sql + "参数:" + roadlineId);
			pst.execute();
			pst.close();

			sql = "DELETE FROM T_MARK WHERE MARK_ROADLINE_ID=?";
			pst = conn.prepareStatement(sql);
			log.info(sql + "参数:" + roadlineId);
			pst.setInt(1, roadlineId);
			pst.execute();
			pst.close();

		} catch (SQLException e) {
			log.error("通过PreparedStatement来删除路线!", e);
			throw new RuntimeException("通过PreparedStatement来删除路线!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

	public void dropRoadlineByUserId(int roadlineId) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		PreparedStatement pst4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		String roadline_is_hot = null;
		String roadline_title = null;
		String roadline_content = null;
		String roadline_goal_area = null;
		String roadline_status = null;

		try {
			conn = openConnection();

			/** 获取当前用户的路线信息 */
			String sql = "SELECT roadline_is_hot,roadline_title,roadline_content,roadline_goal_area,roadline_status FROM T_ROADLINE WHERE roadline_id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, roadlineId);
			log.info(sql + "参数:" + roadlineId);
			rs = pst.executeQuery();
			if (rs.next()) {
				roadline_is_hot = rs.getString("roadline_is_hot");
				roadline_title = rs.getString("roadline_title");
				roadline_content = rs.getString("roadline_content");
				roadline_goal_area = rs.getString("roadline_goal_area");
				roadline_status = rs.getString("roadline_status");
				/** 判断该路线是否是热点城市路线 */
				if (TRoadline.ROADLINE_IS_HOT_YES.equals(roadline_is_hot)
						&& TRoadline.STATUS_SUCCESS.equals(roadline_status)) {
					sql = "SELECT mark_id,mark_title FROM T_MARK";
					pst2 = conn.prepareStatement(sql);
					log.info(sql);
					rs2 = pst2.executeQuery();
					List<TMark> marks = new ArrayList<TMark>();
					while (rs2.next()) {
						TMark mark = new TMark();
						mark.setMarkId(rs2.getInt("mark_id"));
						mark.setMarkTitle(rs2.getString("mark_title"));
						marks.add(mark);
					}
					StringBuilder questions = new StringBuilder();
					List<Object> args = new ArrayList<Object>();
					boolean is_mark = false;
					for (TMark tMark : marks) {
						is_mark = false;
						int markId = tMark.getMarkId();
						String markTitle = tMark.getMarkTitle();
						if (!"".equals(roadline_title)
								&& roadline_title.contains(markTitle)) {
							is_mark = true;
						}
						if (!"".equals(roadline_content)
								&& roadline_content.contains(markTitle)) {
							is_mark = true;
						}
						if (!"".equals(roadline_goal_area)
								&& roadline_goal_area.contains(markTitle)) {
							is_mark = true;
						}
						if (is_mark) {
							questions.append("?,");
							args.add(markId);
						}
					}
					if (questions.length() > 1) {
						questions.deleteCharAt(questions.lastIndexOf(","));
						sql = "update t_mark set mark_content=mark_content-1 where mark_id in("
								+ questions.toString() + ")";
						pst3 = conn.prepareStatement(sql);
						for (int i = 0; i < args.size(); i++) {
							pst3.setObject(i + 1, args.get(i));
						}
						log.info(sql + "参数:" + args.toArray());
						pst3.executeUpdate();
						pst3.close();
					}
					rs2.close();
					pst2.close();
				}
			}
			rs.close();
			pst.close();

			/** 标记路线已删除 */
			sql = "UPDATE T_ROADLINE SET roadline_status=?,roadline_is_hot=? WHERE roadline_id=?";
			pst4 = conn.prepareStatement(sql);
			pst4.setString(1, TRoadline.STATUS_DROP);
			pst4.setString(2, TRoadline.ROADLINE_IS_HOT_NO);
			pst4.setInt(3, roadlineId);
			log.info(sql + "参数:" + TRoadline.STATUS_DROP + ","
					+ TRoadline.ROADLINE_IS_HOT_NO + "," + roadlineId);
			pst4.executeUpdate();
			pst4.close();
		} catch (SQLException e) {
			log.error("将路线标记为删除出现异常!", e);
			throw new RuntimeException("将路线标记为删除出现异常!", e);
		} finally {
			close(null, null, null, conn);
		}
	}

}
