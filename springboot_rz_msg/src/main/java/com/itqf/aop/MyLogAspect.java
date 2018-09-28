package com.itqf.aop;


import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import com.itqf.annotation.MyLog;
import com.itqf.domain.SysLog;
import com.itqf.service.SysLogService;
import com.itqf.utils.HttpContextUtils;
import com.itqf.utils.IPUtils;
import com.itqf.utils.JsonUtils;
import com.itqf.utils.Lg;
import com.itqf.utils.ShiroUtils;

/**
 * author: 007
 * date: 2018年7月18日下午8:18:31
 * file: MyLogAspect.java
 * desc: 
 */
@Aspect
@Component
public class MyLogAspect {

	@Resource
	private SysLogService sysLogService;
	
	@Pointcut(value="@annotation(com.itqf.annotation.MyLog)")
	private void myPointcut() {	}
	
	@AfterReturning(pointcut="myPointcut()")
	public void afterReturning(JoinPoint joinpoint) {
		
		Lg.log("------------>调用方法了"+joinpoint.getTarget().getClass().getName());
		SysLog log = new SysLog();
		log.setCreateDate(new Date());
		log.setIp(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
		System.out.println(IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest()));
		String methodName = joinpoint.getTarget().getClass().getName()+"."+ joinpoint.getSignature().getName();
		log.setMethod(methodName);
		//怎么得到目标方法的注解
		MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
		Method method =  methodSignature.getMethod();
		MyLog mylog = 	method.getAnnotation(MyLog.class);
		if (mylog!=null) {
			String operation  = mylog.value();//	@MyLog("查询菜单列表") 得到中间的文本
			log.setOperation(operation);
		}
		
		 //Parameter p []  =  method.getParameters();  参数列表（形参）
//		log.setParams(Arrays.toString(p));
		Object o [] =  joinpoint.getArgs();
		log.setParams(JsonUtils.objectToJson(o));
		log.setUsername(ShiroUtils.getUserEntity().getUsername());
		
		
		sysLogService.saveLog(log);
		
		Lg.log(JsonUtils.objectToJson(o)+"--"+methodName);
	}
	
}
