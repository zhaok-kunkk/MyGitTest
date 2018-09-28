package com.itqf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.itqf.domain.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    SysUser findUserByUserName(String username);
    
    List<SysUser> findAllUser(@Param("search")String search);
    
    int deleteBatch(List<Integer> list);
    
    
}