package com.itqf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.ScheduleJobLog;
import com.itqf.mapper.ScheduleJobLogMapper;
import com.itqf.service.ScheduJobLogService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service
public class ScheduJobLogServiceImpl implements ScheduJobLogService{
	
	@Resource
	private ScheduleJobLogMapper scheduleJobLogMapper;
	
	@Override
	public void insert(ScheduleJobLog log) {
		scheduleJobLogMapper.insert(log);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduJobLogService#findAll()
	 */
	@Override
	public DataGridResult findAll(Query query) {
		// TODO Auto-generated method stub
		int offset=(int) query.get("offset");
		int limit =(int) query.get("limit");
		PageHelper.offsetPage(offset, limit);
		List<ScheduleJobLog> list = scheduleJobLogMapper.findAllLogByPage();
		
		PageInfo<ScheduleJobLog> info=new PageInfo<>(list);
		
		List<ScheduleJobLog> rows = info.getList();
		int total=(int) info.getTotal();
		return new DataGridResult(rows, total);
	}

}
