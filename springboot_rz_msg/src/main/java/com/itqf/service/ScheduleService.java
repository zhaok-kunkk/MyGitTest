package com.itqf.service;

import java.util.List;

import com.itqf.domain.ScheduleJob;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;

public interface ScheduleService {

	public DataGridResult findSchedule(Query query);
	
	public R save(ScheduleJob scheduleJob);
	
	public R delete(List<Integer> jobId);
	
	public R findScheduleById(long jobId);
	
	public R update(ScheduleJob scheduleJob);
	
	public R pauseJob(List<Long> id);
	
	public R resumeJob(List<Long> id);
	
	public R runJob(List<Long> id);
}
