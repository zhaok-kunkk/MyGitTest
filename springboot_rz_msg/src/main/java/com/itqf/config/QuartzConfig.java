package com.itqf.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class QuartzConfig {
	
	@Bean
	public SchedulerFactoryBean createScheduleFactory(@Qualifier("druidDataSource") DataSource dataSource) {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		schedulerFactoryBean.setDataSource(dataSource);
		Properties prop = new Properties();
		 //配置实例
        //prop.put("org.quartz.scheduler.instanceName", "MyScheduler");//实例名称
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        prop.put("org.quartz.threadPool.threadCount", "5");
        //JobStore配置
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");

		//把任务相关数据持久化存储到数据库中
		schedulerFactoryBean.setQuartzProperties(prop);
		//设置启动延迟时间
		schedulerFactoryBean.setStartupDelay(5);
		schedulerFactoryBean.setSchedulerName("MyScheduler");
		schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖数据库中同名job
		schedulerFactoryBean.setAutoStartup(true);//设置自动启动
		
		return schedulerFactoryBean;
	}

}
