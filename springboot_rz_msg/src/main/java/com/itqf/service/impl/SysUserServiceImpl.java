package com.itqf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.SysUser;
import com.itqf.mapper.SysUserMapper;
import com.itqf.service.SysUserService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;

/**
 * author: 007
 * date: 2018年7月12日上午11:55:37
 * file: SysUserServiceImpl.java
 * desc: 
 */
@Service
public class SysUserServiceImpl implements SysUserService{

	@Resource
	private SysUserMapper sysUserMapper;

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#login(java.lang.String)
	 */
	@Override
	public SysUser login(String username) {
		// TODO Auto-generated method stub
		return sysUserMapper.findUserByUserName(username);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#findAllUser(com.itqf.utils.Query)
	 */
	@Override
	public DataGridResult findAllUser(Query query) {
		
		PageHelper.offsetPage(Integer.parseInt(query.get("offset")+""),(Integer)query.get("limit"));
		
		System.out.println(query.get("search")+"------------");
		List<SysUser> list = sysUserMapper.findAllUser(query.get("search")+"");
		
		PageInfo<SysUser> pageInfo=new PageInfo<>(list);
		List<SysUser> rows = pageInfo.getList();
		int total = Integer.parseInt(pageInfo.getTotal()+"");
		DataGridResult result=new DataGridResult(rows, total);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#addUser(com.itqf.domain.SysUser)
	 */
	@Override
	public R addUser(SysUser user) {
		sysUserMapper.insert(user);
		return R.ok();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#deleteBatch(java.util.List)
	 */
	@Override
	public R deleteBatch(List<Integer> list) {
		int i = sysUserMapper.deleteBatch(list);
		if(i>0) {
			return R.ok();
		}
		return R.error();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#selectUserById(int)
	 */
	@Override
	public R selectUserById(int userId) {
		// TODO Auto-generated method stub
		SysUser user = sysUserMapper.selectByPrimaryKey((long)userId);
		
		//System.out.println(user.getEmail()+"-"+user.getUsername()+user.getPassword());
		return R.ok().put("menu", user);
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#updateUser(com.itqf.domain.SysUser)
	 */
	@Override
	public R updateUser(SysUser user) {
		int i = sysUserMapper.updateByPrimaryKey(user);
		if(i>0) {
			return R.ok();
		}
		return R.error();
	}

	/* (non-Javadoc)
	 * @see com.itqf.service.SysUserService#updatePwd(java.lang.String)
	 */
	@Override
	public R updatePwd(SysUser sysuser) {
		// TODO Auto-generated method stub
		sysUserMapper.updateByPrimaryKeySelective(sysuser);
		return R.ok();
	}

	
	
	
	
}
