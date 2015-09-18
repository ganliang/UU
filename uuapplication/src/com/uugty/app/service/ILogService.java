package com.uugty.app.service;

import com.uugty.app.domain.TUserLoginLog;

/**
 * @ClassName: ILogService
 * @Description: 日志接口
 * @author ganliang
 * @date 2015年6月15日 下午2:11:52
 */
public interface ILogService {

	/**
	 * @Title: save
	 * @Description: 保存登录的日志记录
	 * @param @param loginLog
	 * @return loginLog 返回类型
	 * @throws
	 */
	public TUserLoginLog save(TUserLoginLog loginLog);

}
