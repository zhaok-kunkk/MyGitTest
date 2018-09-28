package com.itqf.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.itqf.domain.ScheduleJob;
import com.itqf.quartz.QuartzJob;

/**
*author：李丽婷
*company：千锋互联
*date:2018年7月18日 下午2:16:53
*file:ScheduleUtils.java
*desc:工具类
*SchedulerUtils
*/
public class SchedulerUtils {
	
	// 3.1 创建Job，Trigger,Schedule，存入ScheduleJob
	public static  void createScheduler(Scheduler scheduler,ScheduleJob scheduleJob) {
		//1.创建JobDetail
		JobDetail jobDetail =JobBuilder.newJob(QuartzJob.class).
				withIdentity(Constant.JOB_KEY_PREFIX+scheduleJob.getJobId()).build();
		String cronExpression = scheduleJob.getCronExpression();
		
		//怎么向jobDetail中绑定数据？？  JobExecutionContext对象能获取到数据
		String json = JsonUtils.objectToJson(scheduleJob);
		jobDetail.getJobDataMap().put(Constant.JOBDATAMAP_KEY, json);
		
		//2.创建一个触发器
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().
				withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).
				withIdentity(Constant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId()).build();
		
		
		//3.注册job和trigger
		try {
			scheduler.scheduleJob(jobDetail, cronTrigger);
			//4.立即启动任务
			scheduler.start();
		} catch (SchedulerException e) {
			throw new RRException("创建任务失败，请联系管理员");
		}
		
	
		
	}
//	#### 3.2 暂停任务

	public  static void  pauseJob(Scheduler scheduler,long jobId) {
		JobKey jobKey = JobKey.jobKey(Constant.JOB_KEY_PREFIX+jobId);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			throw new RRException("暂停任务失败，请联系管理员");
		}
		
	}
//	#### 3.3 执行任务
	public  static void  runJob(Scheduler scheduler,long jobId) {
		JobKey jobKey = JobKey.jobKey(Constant.JOB_KEY_PREFIX+jobId);
		try {
			scheduler.triggerJob(jobKey);
		} catch (SchedulerException e) {
			throw new RRException("运行任务失败，请联系管理员");
		}
	}
//	#### 3.4 删除定时任务
	public  static void  delJob(Scheduler scheduler,long jobId) {
		JobKey jobKey = JobKey.jobKey(Constant.JOB_KEY_PREFIX+jobId);
		try {
			scheduler.deleteJob(jobKey);
			System.out.println("666");
		} catch (SchedulerException e) {
			throw new RRException("删除任务失败，请联系管理员");
		}
		
	}
	
	
//	#### 3.5 恢复执行
	public  static void  resumeJob(Scheduler scheduler,long jobId) {
		JobKey jobKey = JobKey.jobKey(Constant.JOB_KEY_PREFIX+jobId);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			throw new RRException("恢复任务失败，请联系管理员");
		}
		
	}	

//	#### 3.6 定时更新任务
	public  static void  updateJob(Scheduler scheduler,ScheduleJob scheduleJob) {
		try {
			
			TriggerKey triggerKey = TriggerKey.triggerKey(Constant.TRIGGER_KEY_PREFIX+scheduleJob.getJobId());
			//得到原来的触发器对象
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey); 
			//替换cron表达式
			cronTrigger = cronTrigger.getTriggerBuilder().
			withSchedule(CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())).build();
			
			//重置触发器
			scheduler.rescheduleJob(triggerKey, cronTrigger);
			
		} catch (SchedulerException e) {
			throw new RRException("更新任务失败，请联系管理员");
		}
		
	}	

	

}
