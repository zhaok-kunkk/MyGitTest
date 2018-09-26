package com.itqf.quartz;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.itqf.domain.ScheduleJob;
import com.itqf.domain.ScheduleJobLog;
import com.itqf.service.ScheduJobLogService;
import com.itqf.utils.Constant;
import com.itqf.utils.JsonUtils;
import com.itqf.utils.Lg;
import com.itqf.utils.SpringContextUtils;



/**   
 * author: 007
 * date: 2018年7月20日上午11:32:25
 * file: QuartzJob.java
 * desc: 
 */
public class QuartzJob extends QuartzJobBean{

	/* (non-Javadoc)
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		long start  =System.currentTimeMillis();
		
		ScheduleJobLog log = new ScheduleJobLog();

		try {
		JobDataMap dataMap =  context.getJobDetail().getJobDataMap();
		Lg.log("----------------->任务被调用啦，哈哈哈"+dataMap);

		String json = dataMap.getString(Constant.JOBDATAMAP_KEY);
		
		//{"jobId":50,"beanName":"testTask","methodName":"testTask","params":null,"cronExpression":"0/5 * * * * ?","status":0,"remark":"还是测试","createTime":1531902575739}
		
		//{"jobId":50,"beanName":"backUpDB","methodName":"backUpDb","params":null,"cronExpression":"0/5 * * * * ?","status":0,"remark":"还是测试","createTime":1531902575739}

		//{"jobId":50,"beanName":"c alStaff","calStaff":"backUpDb","params":null,"cronExpression":"0/5 * * * * ?","status":0,"remark":"还是测试","createTime":1531902575739}

		
		ScheduleJob scheduleJob  = JsonUtils.jsonToPojo(json, ScheduleJob.class); 
		
//		TestTask test = new TestTask();
//		test.testTask();  固定testTask 方法
		
		//BackUpDB.backUpDB();  固定调用备份数据库 
		//备份数据库的任务
		//定时结算报销金额的任务
		//…………
		//取出了要执行的任务的方法
		String beanName = scheduleJob.getBeanName();//类名
		String methodName = scheduleJob.getMethodName();//方法名
		String params = scheduleJob.getParams();//参数
		
		//得到该bean的对象
		 Object target =  SpringContextUtils.getBean(beanName);
		 //通过反射得到要调用的方法的Method对象
		 Method method  =null;
		 if (StringUtils.isNotBlank(params)) {
			 method =  target.getClass().getDeclaredMethod(methodName, String.class);
			 //通过反射调用方法
			 method.invoke(target, params);
		 }else {
			method = target.getClass().getDeclaredMethod(methodName);
			method.invoke(target);
		}
		 
		//保存一条任务被调用的数据
		 
		 log.setBeanName(beanName);
		 log.setMethodName(methodName);
		 log.setParams(params);
		 log.setCreateTime(new Date());
		 System.out.println(scheduleJob.getJobId()+"------------------");
		 System.out.println(scheduleJob.getStatus()+"------------------");
		 log.setJobId(scheduleJob.getJobId());
		
		 log.setStatus(scheduleJob.getStatus());
		 long end  =System.currentTimeMillis();
		 log.setTimes(Integer.parseInt(end-start+""));;

		 } catch (Exception e) {
			 log.setError(e.getMessage());
		 }finally {
			 ScheduJobLogService service = (ScheduJobLogService) SpringContextUtils.getBean(ScheduJobLogService.class);
			 service.insert(log);
		}
		
	}

}
