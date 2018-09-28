package com.itqf.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.SysRole;
import com.itqf.mapper.SysRoleMapper;
import com.itqf.service.SysRoleService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


@Service
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleMapper sysRoleMapper;

	/* (non-Javadoc)
	 * @see com.itqf.service.SysRoleService#findAllRole()
	 */
	@Override
	public DataGridResult findAllRole(Query query) {
		PageHelper.offsetPage(Integer.parseInt(query.get("offset")+""),Integer.parseInt(query.get("limit")+""));
		
		List<SysRole> list = sysRoleMapper.findAllRole(query.get("search")+"");
		PageInfo<SysRole> pageInfo=new PageInfo<>(list);
		List<SysRole> role = pageInfo.getList();
		long total = pageInfo.getTotal();
		DataGridResult result=new DataGridResult(role, Integer.parseInt(total+""));
		return result;
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysRoleService#addRole(com.itqf.domain.SysRole)
	 */
	@Override
	public R addRole(SysRole sysRole) {
		int i = sysRoleMapper.insert(sysRole);
		if(i>0) {
			return R.ok();
		}
		return R.error();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysRoleService#deleteRole(java.util.List)
	 */
	@Override
	public R deleteRole(List<Integer> ids) {
		int i = sysRoleMapper.deleteBatch(ids);
		if (i>0) {
			return R.ok();
		}
		return R.error();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysRoleService#findRoleById(int)
	 */
	@Override
	public R findRoleById(int roleId) {
		SysRole sysRole = sysRoleMapper.selectByPrimaryKey((long)roleId);
		return R.ok().put("menu", sysRole);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysRoleService#updateRole(com.itqf.domain.SysRole)
	 */
	@Override
	public R updateRole(SysRole sysRole) {
		int i = sysRoleMapper.updateByPrimaryKey(sysRole);
		if(i>0) {
			return R.ok();
		}
		return R.error();
	}
	
}
