package com.itqf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.annotation.MyLog;
import com.itqf.domain.SysRole;
import com.itqf.domain.SysUser;
import com.itqf.service.SysRoleService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;
import com.itqf.utils.ShiroUtils;


@Controller
public class SysRoleController {

	@Resource
	private SysRoleService sysRoleService;
	
	@MyLog("查询角色列表")
	@RequestMapping("/sys/role/list")
	@ResponseBody
	public DataGridResult findAllRole(Integer offset,Integer limit,String search){
		Map<String,Object> map=new HashMap<String , Object>();
		map.put("offset", offset);
		map.put("limit", limit);
		if(search==null) {
			search="";
		}
		map.put("search", search);
		
		
		return sysRoleService.findAllRole(new Query(map));
	}
	
	@MyLog("添加角色信息")
	@RequestMapping("/sys/role/save")
	@ResponseBody
	public R AddRole(@RequestBody SysRole sysRole) {
		sysRole.setCreateTime(new Date(System.currentTimeMillis()));
		SysUser sysUser = ShiroUtils.getUserEntity();
		sysRole.setCreateUserId(sysUser.getUserId());
		return sysRoleService.addRole(sysRole);
	}
	
	@MyLog("删除角色信息")
	@RequestMapping("/sys/role/del")
	@ResponseBody
	public R deleteRole(@RequestBody List<Integer> ids) {
		
		return sysRoleService.deleteRole(ids);
		
	}
	
	@RequestMapping("/sys/role/info/{menuId}")
	@ResponseBody
	public R intoUpdate(@PathVariable int menuId) {
		
		return sysRoleService.findRoleById(menuId);
	}
	
	@MyLog("修改角色信息")
	@RequestMapping("/sys/role/update")
	@ResponseBody
	public R updateRole(@RequestBody SysRole sysRole) {
		
		return sysRoleService.updateRole(sysRole);
	}
	
}
