package com.itqf.config;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
*desc:德鲁伊的过滤规则  主要配置忽略资源
*<url-pattern>/*
*/
@WebFilter(filterName="druidStatFilter",urlPatterns="/*",
initParams= {@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})
public class DruidStatFilter  extends WebStatFilter{

}
