package com.itqf.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.ScheduleJob;
import com.itqf.mapper.ScheduleJobMapper;
import com.itqf.service.ScheduleService;
import com.itqf.utils.Constant;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;
import com.itqf.utils.SchedulerUtils;

/**
 * author: 007
 * date: 2018年7月19日下午3:32:38
 * file: ScheduleServiceImpl.java
 * desc: 
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Resource
	private ScheduleJobMapper scheduleJobMapper;
	
	@Resource
	private Scheduler scheduler;
	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#findSchedule()
	 */
	@Override
	public DataGridResult findSchedule(Query query) {
		int offset=Integer.parseInt(query.get("offset")+"");
		int limit =Integer.parseInt(query.get("limit")+"");
		PageHelper.offsetPage(offset, limit);
		List<ScheduleJob> list = scheduleJobMapper.findScheduleJob(query.get("search")+"");
		PageInfo<ScheduleJob> pageInfo=new PageInfo<>(list);
		int total=Integer.parseInt(pageInfo.getTotal()+"");
		return new DataGridResult(pageInfo.getList(), total);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#save()
	 */
	@Override
	public R save(ScheduleJob scheduleJob) {
		
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
		scheduleJobMapper.insert(scheduleJob);
		//真正创建任务调度器
		SchedulerUtils.createScheduler(scheduler, scheduleJob);
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#delete(int)
	 */
	@Override
	public R delete(List<Integer> jobId) {
		// TODO Auto-generated method stub
		scheduleJobMapper.deleteBatch(jobId);
		for (Integer integer : jobId) {
			SchedulerUtils.delJob(scheduler, Long.parseLong(integer+""));
		}
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#findScheduleById()
	 */
	@Override
	public R findScheduleById(long jobId) {
		ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(jobId);
		
		return R.ok().put("scheduleJob", scheduleJob);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#update(com.itqf.domain.ScheduleJob)
	 */
	@Override
	public R update(ScheduleJob scheduleJob) {
		// TODO Auto-generated method stub
		scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
		//修改任务调度
		SchedulerUtils.updateJob(scheduler, scheduleJob);
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#pauseJob(java.util.List)
	 */
	@Override
	public R pauseJob(List<Long> id) {
		scheduleJobMapper.updateStatus(Constant.ScheduleStatus.PAUSE.getValue(), id);
		for (Long long1 : id) {
			SchedulerUtils.pauseJob(scheduler, long1);
		}
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#resumeJob(java.util.List)
	 */
	@Override
	public R resumeJob(List<Long> id) {
		// TODO Auto-generated method stub
		scheduleJobMapper.updateStatus(Constant.ScheduleStatus.NORMAL.getValue(), id);
		
		for (Long long1 : id) {
			SchedulerUtils.resumeJob(scheduler, long1);
		}
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.ScheduleService#runJob(java.util.List)
	 */
	@Override
	public R runJob(List<Long> id) {
		// TODO Auto-generated method stub
		scheduleJobMapper.updateStatus(Constant.ScheduleStatus.NORMAL.getValue(), id);
		for (Long long1 : id) {
			SchedulerUtils.resumeJob(scheduler, long1);
		}
		return R.ok();
	}

}
