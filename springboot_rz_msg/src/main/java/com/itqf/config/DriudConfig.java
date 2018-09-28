package com.itqf.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.itqf.utils.Lg;


@Configuration
public class DriudConfig {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")//读取以spring.datasource开头的文件
	public DataSource druidDataSource() {
		Lg.log("Druid执行成功");
		DruidDataSource dataSource=new DruidDataSource();
		List<Filter> filters=new ArrayList<>();
		filters.add(createFilter());
		//运行执行多条sql 需要设置一个过滤器
		dataSource.setProxyFilters(filters);
		return dataSource;
		
	}
	
	@Bean
	public WallFilter createFilter() {
		
		WallFilter filter=new WallFilter();
		WallConfig config=new WallConfig();
		config.setMultiStatementAllow(true);
		filter.setConfig(config);
		return filter;
		
	}
	
	
	
	
	
	
	
	
}
