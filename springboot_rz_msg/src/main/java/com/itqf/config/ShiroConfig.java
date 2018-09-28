package com.itqf.config;



import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.itqf.shiro.UserRealm;



@Configuration//代表是spring的一个配置文件
public class ShiroConfig {

	
	/*
	 * 1.创建sessionManager对象
	 */
	@Bean(name="sessionManager")
	public SessionManager createSessionManager() {
		
		DefaultWebSessionManager sessionManager=new DefaultWebSessionManager();
		sessionManager.setGlobalSessionTimeout(1000*60*30);//设置session超时时间
		sessionManager.setSessionIdUrlRewritingEnabled(false);//地址栏是否拼接sessionId
		sessionManager.setSessionValidationSchedulerEnabled(true);//清楚多余的会话
		
		return sessionManager;
	}
	
	
	/*
	 * 2.构建securityManager
	 */
	@Bean("securityManager")
	public SecurityManager creatSecurityManager(SessionManager sessionManager,UserRealm userRealm) {
		
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		securityManager.setSessionManager(sessionManager);
		securityManager.setRealm(userRealm);
		
		//缓存的配置
		EhCacheManager ehCacheManager=new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		securityManager.setCacheManager(ehCacheManager);
		
		//记住我
		CookieRememberMeManager rememberMeManager=new CookieRememberMeManager();
		Cookie cookie=rememberMeManager.getCookie();
		//设置最大时长
		cookie.setMaxAge(6000);
		rememberMeManager.setCookie(cookie);
		securityManager.setRememberMeManager(rememberMeManager);
		
		
		return securityManager;
	}
		
		/*
		 * 3.配置过滤器
		 */
		@Bean("shiroFiler")
		public ShiroFilterFactoryBean creatShiroFilter(SecurityManager securityManager) {
			
			ShiroFilterFactoryBean shiroFilter=new ShiroFilterFactoryBean();
			shiroFilter.setSecurityManager(securityManager);
			//配置登录页面
			shiroFilter.setLoginUrl("/login.html");
			shiroFilter.setSuccessUrl("/index.html");//认证页面
			shiroFilter.setUnauthorizedUrl("unauthorized.html");//未认证页面
			
			//配置匿名访问
			Map<String, String> filterChainDefinitionMap=new  LinkedHashMap<>();
			filterChainDefinitionMap.put("/public/**", "anon");
			filterChainDefinitionMap.put("/login.html", "anon");
			filterChainDefinitionMap.put("/sys/login", "anon");
			filterChainDefinitionMap.put("/sys/logout", "anon");
			//验证码
			filterChainDefinitionMap.put("/*.jpg", "anon");
			
			
			filterChainDefinitionMap.put("/**", "user");
			//配置匿名访问
			shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap); 
			
			return shiroFilter;
			
		}
	
		/*
		 * 4.使spring容器能读到shiro的注解
		 */
	@Bean()
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor=new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}
	
	/*
	 * 5.启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor 之后才可以使用.
	 */
	@Bean("advisorAutoProxyCreator")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	
	/*
	 * 6.配置
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor creAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor=new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		
		return authorizationAttributeSourceAdvisor;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
