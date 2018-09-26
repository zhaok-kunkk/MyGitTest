package com.itqf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itqf.domain.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    List<SysMenu> findAllMenu(@Param("search")String search);
    
    int deleteBatch(List<Integer> list);
    
    public List<SysMenu> selectMenuNotButton();
    
    public SysMenu findMenuAndParentByMenuId(long menuId);
    
    //查询用户所具有的权限
    public List<String> queryPerms(long userId);
    
    //查询所有目录
    public List<SysMenu> findMenu();
    
    //查询目录下的菜单
    public List<SysMenu> findMenuByParentId(long parentId);
}