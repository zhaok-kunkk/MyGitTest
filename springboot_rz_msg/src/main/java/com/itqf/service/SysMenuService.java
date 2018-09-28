package com.itqf.service;

import java.util.List;
import java.util.Set;

import com.itqf.domain.SysMenu;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


public interface SysMenuService {

	public DataGridResult findAllMenu(Query query);
	
	public R delete(List<Integer> list);
	
	public R selectMenuNotButton();
	
	public R addMenu(SysMenu menu);
	
	public R findMenuAndParentByMenuId(long menuId);
	
	public R update(SysMenu menu);
	
	public Set<String> queryPerms(long userId);
	
	public R findMenuList();
}
