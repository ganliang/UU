package com.uugty.app.service;

import com.uugty.app.domain.TAccessLog;
import com.uugty.app.domain.TSessionUnbound;

public interface IAccessLogService {

	public void saveAccessLog(TAccessLog accessLog);

	public void updateSessionBound(TSessionUnbound bound);

	public void saveSessionBound(TSessionUnbound bound);

}
