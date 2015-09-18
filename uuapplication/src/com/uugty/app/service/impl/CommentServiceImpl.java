package com.uugty.app.service.impl;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.domain.TComment;
import com.uugty.app.domain.TCommentReply;
import com.uugty.app.entity.CommentEntity;
import com.uugty.app.entity.CommentReplyEntity;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.service.ICommentService;
import com.uugty.app.utils.Page;
import com.uugty.app.utils.SQLUtil;

public class CommentServiceImpl implements ICommentService {
	private static final Logger log = Logger
			.getLogger(CommentServiceImpl.class);
	private BaseDao dao = new BaseDao();

	/**
	 * @Title: getCommentsByCommentedUserId
	 * @Description: 根据被评论的用户id，来获取评论
	 * @param @param userId
	 * @param @param page
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getCommentsByCommentedUserId(String userId, Page page) {
		String sql = "SELECT u.user_id,u.user_name,user_avatar,c.comment_content,date_format(c.comment_date,'%Y-%m-%d') comment_date FROM t_comment c LEFT JOIN t_user u ON(u.user_id=c.comment_user_id) WHERE c.commented_user_id=? ORDER BY c.comment_date DESC LIMIT ?,?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		args.add(page.getBeginIndex());
		args.add(page.getEndIndex());
		try {
			List<Object> query = dao.query(sql, args, CommentEntity.class);
			List<Object> list = new ArrayList<Object>();
			for (Object object : query) {
				CommentEntity comment = (CommentEntity) object;
				String commentContent = comment.getCommentContent();
				if (commentContent != null && !"".equals(commentContent)) {
					comment.setCommentContent(URLDecoder.decode(commentContent,
							"UTF-8"));
				}
				list.add(comment);
			}
			return list;
		} catch (InstantiationException e) {
			log.error("根据用户id找到登录信息出现异常", e);
			throw new RuntimeException("根据用户id找到登录信息出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("根据用户id找到登录信息出现异常", e);
			throw new RuntimeException("根据用户id找到登录信息出现异常", e);
		} catch (Exception e) {
			log.error("根据用户id找到登录信息出现异常", e);
			throw new RuntimeException("根据用户id找到登录信息出现异常", e);
		}
	}

	@Override
	public int getCommentsCountByCommentedUserId(String userId) {
		String sql = "SELECT COUNT(c.comment_id) FROM T_COMMENT c  WHERE c.commented_user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		try {
			return dao.count(sql, args);
		} catch (SQLException e) {
			log.error("根据用户id找到评论数量出现异常", e);
			throw new RuntimeException("根据用户id找到评论数量出现异常", e);
		}
	}

	/**
	 * @Title: saveComment
	 * @Description: 保存评论
	 * @param @param comment
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int saveComment(TComment comment) {
		comment.setCommentDate(new Date().toLocaleString());
		SQLEntity insertSQL = SQLUtil.insertSQL(comment);
		try {
			return dao.executeInsert(insertSQL.getParameter(),
					insertSQL.getList());
		} catch (Exception e) {
			log.error("保存评论出现异常", e);
			throw new RuntimeException("保存评论出现异常", e);
		}
	}

	/**
	 * @Title: saveCommentReply
	 * @Description: 保存评论的回复
	 * @param @param reply
	 * @param @return
	 * @return int 返回类型
	 * @throws
	 */
	@Override
	public int saveCommentReply(TCommentReply reply) {
		reply.setReplyDate(new Date());
		SQLEntity insertSQL = SQLUtil.insertSQL(reply);
		try {
			return dao.executeInsert(insertSQL.getParameter(),
					insertSQL.getList());
		} catch (Exception e) {
			log.error("保存回复评论出现异常", e);
			throw new RuntimeException("保存回复评论出现异常", e);
		}
	}

	/**
	 * @Title: getCommentListByUserId
	 * @Description: 根据用户id获取评论列表
	 * @param @param userId
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getCommentListByUserId(String userId) {
		String sql = "SELECT c.comment_id,c.total_index,c.comment_content,c.comment_images,date_format(c.comment_date,'%Y-%m-%d') comment_date ,r.reply_content,r.reply_images,u.user_id,u.user_name,user_avatar FROM T_COMMENT c LEFT JOIN T_COMMENT_REPLY r ON(c.comment_id=r.comment_id) LEFT JOIN T_USER u ON(u.user_id=c.comment_user_id)  WHERE c.commented_user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		try {
			List<Object> query = dao.query(sql, args, CommentEntity.class);
			List<Object> list = new ArrayList<Object>();
			for (Object object : query) {
				CommentEntity comment = (CommentEntity) object;
				String commentContent = comment.getCommentContent();
				if (commentContent != null && !"".equals(commentContent)) {
					comment.setCommentContent(URLDecoder.decode(commentContent,
							"UTF-8"));
				}
				String replyContent = comment.getReplyContent();
				if (replyContent != null && !"".equals(replyContent)) {
					comment.setReplyContent(URLDecoder.decode(replyContent,
							"UTF-8"));
				}
				list.add(comment);
			}
			return list;
		} catch (Exception e) {
			log.error("获取评论列表出现异常", e);
			throw new RuntimeException("获取评论列表出现异常", e);
		}
	}

	/**
	 * 
	 * @Title: getCommentIndexByUserId
	 * @Description: 获评论的取性价比指数
	 * @param @param userId
	 * @param @return
	 * @return Object 返回类型
	 * @throws
	 */
	@Override
	public Object getCommentIndexByUserId(String userId) {
		String sql = "SELECT (COUNT(c.comment_id)) commentCount,SUM(c.ratio_index)/COUNT(c.comment_id) avageRatioIndex,SUM(c.fresh_index)/COUNT(c.comment_id) avageFreshIndex,SUM(c.service_index)/COUNT(c.comment_id) avageServiceIndex FROM T_COMMENT c WHERE c.commented_user_id=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(userId);
		NumberFormat nf = new DecimalFormat("0.0");
		CommentReplyEntity entity = null;
		try {
			List<Object> query = dao.query(sql, args, CommentReplyEntity.class);
			if (query != null && query.size() > 0) {
				CommentReplyEntity comment = (CommentReplyEntity) query.get(0);
				entity = new CommentReplyEntity();
				String avageRatioIndex = comment.getAvageRatioIndex();
				if (avageRatioIndex != null && !"".equals(avageRatioIndex)) {
					entity.setAvageRatioIndex(nf.format(Float
							.parseFloat(avageRatioIndex)));
				}

				String avageFreshIndex = comment.getAvageFreshIndex();
				if (avageFreshIndex != null && !"".equals(avageFreshIndex)) {
					entity.setAvageRatioIndex(nf.format(Float
							.parseFloat(avageFreshIndex)));
				}

				String avageServiceIndex = comment.getAvageServiceIndex();
				if (avageServiceIndex != null && !"".equals(avageServiceIndex)) {
					entity.setAvageRatioIndex(nf.format(Float
							.parseFloat(avageServiceIndex)));
				}
				String avageTotalIndex = comment.getAvageTotalIndex();
				if (avageTotalIndex != null && !"".equals(avageTotalIndex)) {
					entity.setAvageRatioIndex(nf.format(Float
							.parseFloat(avageTotalIndex)));
				}
				entity.setCommentCount(comment.getCommentCount());
			}
		} catch (InstantiationException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		} catch (SQLException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		}
		return entity;
	}

	/**
	 * @Title: getCommentListByRoadlineId
	 * @Description: 根据路线id，来获取路线的评论
	 * @param @param roadlineId
	 * @param @return
	 * @return List<Object> 返回类型
	 * @throws
	 */
	@Override
	public List<Object> getCommentListByRoadlineId(int roadlineId) {
		String sql = "SELECT c.comment_id,c.total_index,c.comment_content,c.comment_images,date_format(c.comment_date,'%Y-%m-%d') comment_date,r.reply_content,r.reply_images,u.user_id,u.user_name,user_avatar FROM T_ROADLINE road LEFT JOIN T_COMMENT c ON(road.roadline_user_id=c.commented_user_id) LEFT JOIN T_COMMENT_REPLY r ON(c.comment_id=r.comment_id) LEFT JOIN T_USER u ON(u.user_id=c.comment_user_id) WHERE road.roadline_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(roadlineId);
		try {
			return dao.query(sql, args, CommentEntity.class);
		} catch (InstantiationException e) {
			log.error("获取评论列表出现异常", e);
			throw new RuntimeException("获取评论列表出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取评论列表出现异常", e);
			throw new RuntimeException("获取评论列表出现异常", e);
		} catch (SQLException e) {
			log.error("获取评论列表出现异常", e);
			throw new RuntimeException("获取评论列表出现异常", e);
		}
	}

	/**
	 * @Title: getCommentIndexByRoadlineId
	 * @Description: 根据路线id 获取性价比指数
	 * @param @param roadlineId
	 * @param @return
	 * @return CommentReplyEntity 返回类型
	 * @throws
	 */
	@Override
	public CommentReplyEntity getCommentIndexByRoadlineId(int roadlineId) {
		String sql = "SELECT (COUNT(c.comment_id)) commentCount,IFNULL(SUM(c.total_index)/COUNT(c.comment_id),'0') avageTotalIndex,IFNULL(SUM(c.ratio_index)/COUNT(c.comment_id),'0') avageRatioIndex,IFNULL(SUM(c.fresh_index)/COUNT(c.comment_id),'0') avageFreshIndex,IFNULL(SUM(c.service_index)/COUNT(c.comment_id),'0') avageServiceIndex FROM T_ROADLINE r LEFT JOIN T_COMMENT c ON(r.roadline_user_id=c.commented_user_id)  WHERE r.roadline_id=? ";
		List<Object> args = new ArrayList<Object>();
		args.add(roadlineId);
		List<Object> query = null;
		try {
			query = dao.query(sql, args, CommentReplyEntity.class);
			return query != null && query.size() > 0 ? (CommentReplyEntity) query
					.get(0) : null;
		} catch (InstantiationException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		} catch (IllegalAccessException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		} catch (SQLException e) {
			log.error("获取评论指数出现异常", e);
			throw new RuntimeException("获取评论指数出现异常", e);
		}
	}
}
