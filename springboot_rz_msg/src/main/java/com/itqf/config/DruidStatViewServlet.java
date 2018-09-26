package com.itqf.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.alibaba.druid.support.http.StatViewServlet;

/**
*file:DruidStatViewServlet.java
*desc:
*web.xml
*<init-param>
*	<param-name>
*	<param-value>
*<init-param>
*/
@WebServlet(name="druidStatViewServlet",urlPatterns="/druid/*",
initParams= {
		@WebInitParam(name="allow",value="127.0.0.1"),//白名单 不配置代表所有ip都能访问
		@WebInitParam(name="deny", value="192.168.1.111"),//黑名单
		@WebInitParam(name="loginUsername",value="admin"),
		@WebInitParam(name="loginPassword",value="admin"),
		@WebInitParam(name="resetEable",value="false")//不允许页面的重置功能		
})
public class DruidStatViewServlet extends StatViewServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
