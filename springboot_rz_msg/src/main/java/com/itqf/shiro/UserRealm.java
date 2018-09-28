package com.itqf.shiro;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.itqf.domain.SysUser;
import com.itqf.service.SysMenuService;
import com.itqf.service.SysUserService;
import com.itqf.utils.ShiroUtils;

import sun.tools.tree.ThisExpression;

/**
 * author: 007
 * date: 2018年7月16日下午4:29:22
 * file: UserRealm.java
 * desc: 
 */
@Component
public class UserRealm extends AuthorizingRealm{

	//注入service
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysMenuService sysMenuService;
	/* (non-Javadoc)
	 *授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
		System.out.println(ShiroUtils.getUserEntity().getUserId()+"--------");

		Set<String> stringPermissions = sysMenuService.queryPerms(ShiroUtils.getUserId());
		//授权
		authenticationInfo.setStringPermissions(stringPermissions);
		//角色
		
		
		return authenticationInfo;
	}

	/* (non-Javadoc)
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
			UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
		
		String username=(String) usernamePasswordToken.getPrincipal();
		String password=new String((char[])usernamePasswordToken.getCredentials() );
		System.out.println(username);
		SysUser u =null;
		u = sysUserService.login(username);
		System.out.println(u);
		if (u==null) {
			throw new UnknownAccountException("账号不存在");
		}
		if(!u.getPassword().equals(password)) {
			throw new IncorrectCredentialsException("密码不正确");
		}
		//status:1.正常2.冻结
		if (u.getStatus()!=1) {
			throw new LockedAccountException("账号不可用");
		}
		
		//用户名密码输入正确，用户正常
		SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(u,password,this.getName());
		return authenticationInfo;
	}

	
	
}
