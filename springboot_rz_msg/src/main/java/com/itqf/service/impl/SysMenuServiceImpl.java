package com.itqf.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.SysMenu;
import com.itqf.mapper.SysMenuMapper;
import com.itqf.service.SysMenuService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;
import com.itqf.utils.ShiroUtils;


@Service
public class SysMenuServiceImpl implements SysMenuService{

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#findAllMenu()
	 */
	@Override
	public DataGridResult findAllMenu(Query query) {
		
		PageHelper.offsetPage(Integer.parseInt(query.get("offset")+""), (Integer)query.get("limit"));
		/*SysMenu menu=new SysMenu();
		menu.setMenuId(0l);
		menu.setName("一级菜单");
		menu.setParentId(-1l);*/
		List<SysMenu> list = sysMenuMapper.findAllMenu(query.get("search")+"");
		
		//list.add(menu);
		PageInfo<SysMenu> pageInfo=new PageInfo<>(list);
		List<SysMenu> rows = pageInfo.getList();
		Integer total=Integer.parseInt(pageInfo.getTotal()+"");
		DataGridResult result=new DataGridResult(rows, total);
		//System.out.println(query.get("search")+"---"+result);
		return result;
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#delete(java.util.List)
	 */
	@Transactional
	@Override
	public R delete(List<Integer> list) {
		// TODO Auto-generated method stub
		int i = sysMenuMapper.deleteBatch(list);
		if(i>0) {
			return R.ok();
		}
		
		return R.error();
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#selectMenuNotButton()
	 */
	@Override
	public R selectMenuNotButton() {
		
		List<SysMenu> list = sysMenuMapper.selectMenuNotButton();
		/*for (SysMenu sysMenu : list) {
			System.out.println("1、"+sysMenu.getName()+sysMenu.getMenuId()+sysMenu.getParentId());
		}*/
		SysMenu menu=new SysMenu();
		menu.setMenuId(0l);
		menu.setName("一级菜单");
		menu.setParentId(-1l);
		list.add(menu);
		/*for (SysMenu sysMenu : list) {
			System.out.println("2、"+sysMenu.getName()+sysMenu.getMenuId()+sysMenu.getParentId());
		}*/
		
		return R.ok().put("menuList", list);
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#addMenu(com.itqf.domain.SysMenu)
	 */
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED)
	@Override
	public R addMenu(SysMenu menu) {
		sysMenuMapper.insert(menu);
		return R.ok();
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#findMenuAndParentByMenuId(long)
	 */
	@Override
	public R findMenuAndParentByMenuId(long menuId) {
		
		SysMenu menu = sysMenuMapper.findMenuAndParentByMenuId(menuId);
		
		return R.ok().put("menu", menu);
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#update(com.itqf.domain.SysMenu)
	 */
	@Override
	public R update(SysMenu menu) {
		sysMenuMapper.updateByPrimaryKeySelective(menu);
		return R.ok();
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#queryAll(long)
	 */
	@Override
	public Set<String> queryPerms(long userId) {
		List<String> list=sysMenuMapper.queryPerms(userId);
		Set<String> permsSet=new HashSet<>();
		for (String string : list) {
			if(StringUtils.isBlank(string)) {
				continue;
			}
			
			String a[]=string.trim().split(",");
			permsSet.addAll(Arrays.asList(a));
		}
		return permsSet;
	}
	/* (non-Javadoc)
	 * @see com.itqf.service.SysMenuService#findMenuList()
	 */
	@Override
	public R findMenuList() {
		R r=new R();
		List<SysMenu> menuList = sysMenuMapper.findMenu(); 
		for (SysMenu sysMenu : menuList) {
			Long menuId = sysMenu.getMenuId();
			//求该目录下的子菜单、
			System.out.println(menuId+"----------");
			List<SysMenu> list = sysMenuMapper.findMenuByParentId(menuId);
			for (SysMenu sysMenu2 : list) {
				System.out.println(sysMenu2.getName()+"-------");
			}
			sysMenu.setList(list);
			
			
			
		}
		r.put("menuList", menuList);
		r.put("permissions", this.queryPerms(ShiroUtils.getUserId()));
		
		return r;
	}

	
	
	
	
	
}
