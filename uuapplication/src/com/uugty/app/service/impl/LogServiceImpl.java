package com.uugty.app.service.impl;

import org.apache.log4j.Logger;

import com.uugty.app.dao.BaseDao;
import com.uugty.app.domain.TUserLoginLog;
import com.uugty.app.entity.SQLEntity;
import com.uugty.app.service.ILogService;
import com.uugty.app.utils.SQLUtil;

/**
 * @ClassName: LogServiceImpl
 * @Description: 日志接口实现类
 * @author ganliang
 * @date 2015年6月15日 下午2:11:33
 */
public class LogServiceImpl implements ILogService {
	private static final Logger log = Logger.getLogger(LogServiceImpl.class);
	private BaseDao baseDao = new BaseDao();

	@Override
	public TUserLoginLog save(TUserLoginLog loginLog) {
		SQLEntity sqlEntity = SQLUtil.insertSQL(loginLog);
		int loginLogId = 0;
		try {
			loginLogId = baseDao.executeInsert(sqlEntity.getParameter(),
					sqlEntity.getList());
		} catch (Exception e) {
			log.error("保存登录日志出现异常", e);
			throw new RuntimeException("保存登录日志出现异常", e);
		}
		loginLog.setLoginLogId(loginLogId);
		return loginLog;

	}
}
