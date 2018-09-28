package com.itqf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itqf.domain.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> findAllRole(@Param("search")String search);
    
    int deleteBatch(List<Integer> ids);
}