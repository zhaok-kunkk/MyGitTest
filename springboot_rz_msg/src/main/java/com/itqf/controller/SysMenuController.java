package com.itqf.controller;



import java.awt.Menu;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.annotation.MyLog;
import com.itqf.domain.SysMenu;
import com.itqf.service.SysMenuService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;
import com.itqf.utils.ShiroUtils;

import groovy.lang.IntRange;




@Controller 
public class SysMenuController {

	@Resource
	private SysMenuService sysMenuService;
	
	@MyLog("查询菜单列表")
	@RequestMapping("/sys/menu/list")
	@RequiresPermissions("sys:menu:list")
	@ResponseBody
	public DataGridResult menuList(Integer offset,Integer limit,String search) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("search", search);
		if(search==null) {
			map.put("search", "");
			
		}
		
		
		
		return sysMenuService.findAllMenu(new Query(map));
		
	}
	
	@MyLog("删除菜单")
	@RequestMapping("/sys/menu/del")
	@RequiresPermissions("sys:menu:delete")
	@ResponseBody
	public R delMenu(@RequestBody List<Integer> ids) {
		for (Integer integer : ids) {
			if(integer<29) {
				
			return 	R.error("系统菜单，不能删除");
			}
		}
		
		return sysMenuService.delete(ids);
		
	}
	
	
	@RequestMapping("/sys/menu/select")
	@RequiresPermissions("sys:menu:select")
	@ResponseBody
	public R selectMenu() {
		
		return sysMenuService.selectMenuNotButton();
		
	}
	
	@MyLog("添加菜单")
	@RequestMapping("/sys/menu/save")
	@RequiresPermissions("sys:menu:save")
	@ResponseBody
	public R addMenu(@RequestBody SysMenu menu) {
		
		
		return sysMenuService.addMenu(menu);
	}
	
	
	@RequestMapping("/sys/menu/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	@ResponseBody
	public R MenuInfo(@PathVariable long menuId) {
	
		return sysMenuService.findMenuAndParentByMenuId(menuId);
	}
	
	@MyLog("修改菜单")
	@RequestMapping("/sys/menu/update")
	@RequiresPermissions("sys:menu:update")
	@ResponseBody
	public R updateMenu(@RequestBody SysMenu menu) {
		
		
		return sysMenuService.update(menu);
	}
	
	
	@RequestMapping("/sys/menu/user")
	//@RequiresPermissions("sys:menu:list")
	@ResponseBody
	public R queryMenu() {
		
		return sysMenuService.findMenuList();
	}
	
	
	
}
