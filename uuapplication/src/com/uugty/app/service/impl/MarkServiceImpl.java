package com.uugty.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.domain.TMark;
import com.uugty.app.entity.MarkEntity;
import com.uugty.app.service.IMarkService;
import com.uugty.app.utils.Page;

public class MarkServiceImpl implements IMarkService {

	private static Logger log = Logger.getLogger(MarkServiceImpl.class);

	private static BaseDao dao = new BaseDao();

	
	/**
	 * @Title: getMarkList
	 * @Description: 获取首页 标签显示的列表
	 * @param @param mark
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getMarkList(TMark mark) {
		String sql = "SELECT mark_id,mark_title,mark_content,mark_images,mark_search_type,date_format(mark_date,'%Y-%m-%d %H:%i:%s') mark_date FROM T_MARK ORDER BY mark_weight DESC LIMIT ?,?";
		List<Object> args = new ArrayList<Object>();
		Page page = new Page(mark.getPageSize(), mark.getCurrentPage(), 0);
		args.add(page.getBeginIndex());
		args.add(page.getPageSize());
		try {
			return dao.query(sql, args, MarkEntity.class);
		} catch (InstantiationException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		} catch (SQLException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		}
	}

	/**
	 * @Title: getMarkList
	 * @Description: 获取首页 标签显示的列表和路线列表
	 * @param @param mark
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getMarkLineList(TMark mark) {
		String sql = "SELECT DISTINCT mark.mark_search_type,mark.mark_id,mark.mark_title,mark.mark_content,mark.mark_images,mark.mark_weight FROM ((select 'mark' as mark_search_type,mark_id,mark_title,mark_content+100000 mark_content,mark_images,mark_weight FROM T_MARK) union  (SELECT 'line' as mark_search_type,r.roadline_id as mark_id,r.roadline_title as mark_title,cast(r.roadline_price as char) as mark_content,r.roadline_background as mark_images,(SELECT SUM(c.total_index)/COUNT(c.comment_id) FROM T_COMMENT c WHERE c.commented_user_id=r.roadline_user_id) mark_weight FROM T_ROADLINE r WHERE r.roadline_status='success' AND r.roadline_is_hot='no' )) mark WHERE mark.mark_content!=100000 ORDER BY CAST(mark.mark_content AS UNSIGNED) DESC LIMIT ?,?";
		List<Object> args = new ArrayList<Object>();
		Page page = new Page(mark.getPageSize(), mark.getCurrentPage(), 0);
		args.add(page.getBeginIndex());
		args.add(page.getPageSize());
		try {
			return dao.query(sql, args, MarkEntity.class);
		} catch (InstantiationException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		} catch (SQLException e) {
			log.error("获取首页 标签显示的列表出现异常", e);
			throw new RuntimeException("获取首页 标签显示的列表出现异常", e);
		}
	}

	/**
	 * @Title: saveMarkList
	 * @Description: 批量插入标签
	 * @param @param marks
	 * @return void 返回类型
	 * @throws
	 */
	@Override
	public void saveMarkList(List<TMark> marks) {
		String sql = "INSERT INTO T_MARK(mark_title,mark_images,mark_search_type,mark_weight,mark_date) VALUES(?,?,?,?,NOW())";
		List<List<Object>> lists = new ArrayList<List<Object>>();
		for (TMark mark : marks) {
			List<Object> list = new ArrayList<Object>();
			list.add(mark.getMarkTitle());
			list.add(mark.getMarkImages());
			list.add(mark.getMarkSearchType());
			list.add(mark.getMarkWeight());
			lists.add(list);
		}
		try {
			dao.executeInsertBatch(sql, lists);
		} catch (SQLException e) {
			log.error("批量保存标签出现异常", e);
			throw new RuntimeException("批量保存标签出现异常", e);
		}
	}
}
