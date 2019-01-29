package com.itqf.quartz.task;

import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.itqf.utils.InitDatabaseUtils;
import com.itqf.utils.Lg;


@Component("BackUpDB")
public class BackUpDB {

	public static   void  backUpDB() {//Schedule_log 表中的methodName
		//恢复  source 数据库  sql脚本
		//备份 mysqldump -uroot -padmin -h localhost  -P 3306 shiroboot > /Users/liliting/Desktop/bigdata/oa.sql
	   
		Runtime runtime = Runtime.getRuntime();
		
		Properties properties  = new Properties();
		try {
			properties.load(BackUpDB.class.getClassLoader().getResourceAsStream("db.properties"));
			String command = InitDatabaseUtils.getExportCommand(properties);
			
			String path  ="C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\";//mysql安装目录
			
			runtime.exec(path+command);
			Lg.log("数据库备份成功，请到"+properties.getProperty("jdbc.exportPath")+"目录下查看！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	public static void main(String[] args) {
		//backUpDB();
	}
	
}
