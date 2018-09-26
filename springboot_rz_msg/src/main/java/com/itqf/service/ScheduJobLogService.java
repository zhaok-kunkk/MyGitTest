package com.itqf.service;

import java.util.List;

import com.itqf.domain.ScheduleJobLog;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;


public interface ScheduJobLogService {
	
	public DataGridResult findAll(Query query);
	
	public  void insert(ScheduleJobLog log);

}
