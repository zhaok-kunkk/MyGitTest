package com.itqf.service;

import com.itqf.domain.SysLog;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


public interface SysLogService {
	
	public void saveLog(SysLog log);
	
	public DataGridResult findLog(Query query);

}
