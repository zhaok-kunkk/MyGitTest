package com.itqf.service;

import java.util.List;

import com.itqf.domain.SysRole;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


public interface SysRoleService {

	public DataGridResult findAllRole(Query query);
	
	public R addRole(SysRole sysRole);
	
	public R deleteRole(List<Integer> ids);
	
	public R findRoleById(int roleId);
	
	public R updateRole(SysRole sysRole);
}
