package com.itqf.service;

import com.itqf.domain.SysLog;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;

/**
*author：李丽婷
*company：千锋互联
*date:2018年7月17日 下午2:30:43
*file:SysLogService.java
*desc:
*/
public interface SysLogService {
	
	public void saveLog(SysLog log);
	
	public DataGridResult findLog(Query query);

}
