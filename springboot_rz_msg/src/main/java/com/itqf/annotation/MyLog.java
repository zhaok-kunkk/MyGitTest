package com.itqf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
*author：李丽婷
*company：千锋互联
*date:2018年7月17日 下午2:14:49
*file:MyLog.java
*desc:
*/
//使用元注解标记自定义注解
@Target(ElementType.METHOD) //该注解使用的位置
@Retention(RetentionPolicy.RUNTIME)//放射能读取到该注解
@Documented
public @interface MyLog {

	//定义一个字符串类型的属性
	String  value()  default "";
}
