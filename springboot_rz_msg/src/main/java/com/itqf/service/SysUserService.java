package com.itqf.service;

import java.util.List;

import com.itqf.domain.SysUser;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;

/**
 * author: 007
 * date: 2018年7月12日上午11:54:46
 * file: SysUserService.java
 * desc: 
 */

public interface SysUserService {

	public SysUser login(String username);
	
	public DataGridResult findAllUser(Query query);
	
	public R addUser(SysUser user);
	
	public R deleteBatch(List<Integer> list);
	
	public R selectUserById(int userId);
	
	public R updateUser(SysUser user);
	
	public R updatePwd(SysUser sysUser);
}
