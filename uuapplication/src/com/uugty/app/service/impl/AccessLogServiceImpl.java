package com.uugty.app.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.domain.TAccessLog;
import com.uugty.app.domain.TSessionUnbound;
import com.uugty.app.service.IAccessLogService;

public class AccessLogServiceImpl implements IAccessLogService {

	private static BaseDao dao = new BaseDao();
	private static Logger log = Logger.getLogger(AccessLogServiceImpl.class);

	@Override
	public void saveAccessLog(TAccessLog accessLog) {
		String sql = "INSERT INTO T_ACCESS_LOG(action,parameter,session_id,user_id,access_time) VALUES(?,?,?,?,NOW())";
		List<Object> args = new ArrayList<Object>();
		args.add(accessLog.getAction());
		args.add(accessLog.getParameter());
		args.add(accessLog.getSessionId());
		args.add(accessLog.getUserId());
		try {
			dao.Insert(sql, args);
		} catch (SQLException e) {
			log.error("write access_log is error!", e);
		}
	}

	@Override
	public void saveSessionBound(TSessionUnbound bound) {
		String sql = "INSERT INTO T_SESSION_UNBOUND(user_id,session_id,unbound_time) VALUES(?,?,NOW())";
		List<Object> args = new ArrayList<Object>();
		args.add(bound.getUserId());
		args.add(bound.getSessionId());
		try {
			dao.Insert(sql, args);
		} catch (SQLException e) {
			log.error("saveSessionBound is error!", e);
		}
	}

	@Override
	public void updateSessionBound(TSessionUnbound bound) {
		String sql = "UPDATE T_SESSION_UNBOUND SET unbound_time=NOW() WHERE user_id=?";
		List<Object> args = new ArrayList<Object>();
		args.add(bound.getUserId());
		try {
			dao.Insert(sql, args);
		} catch (SQLException e) {
			log.error("updateSessionBound is error!", e);
		}
	}
}
