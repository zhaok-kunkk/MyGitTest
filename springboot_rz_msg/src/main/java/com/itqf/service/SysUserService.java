package com.itqf.service;

import java.util.List;

import com.itqf.domain.SysUser;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


public interface SysUserService {

	public SysUser login(String username);
	
	public DataGridResult findAllUser(Query query);
	
	public R addUser(SysUser user);
	
	public R deleteBatch(List<Integer> list);
	
	public R selectUserById(int userId);
	
	public R updateUser(SysUser user);
	
	public R updatePwd(SysUser sysUser);
}
