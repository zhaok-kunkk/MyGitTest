package com.itqf.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.itqf.domain.ScheduleJob;

public interface ScheduleJobMapper {
    int deleteByPrimaryKey(Long jobId);

    int insert(ScheduleJob record);

    int insertSelective(ScheduleJob record);

    ScheduleJob selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(ScheduleJob record);

    int updateByPrimaryKey(ScheduleJob record);
    
    List<ScheduleJob> findScheduleJob(@Param("search") String search);
    
    int deleteBatch(List<Integer> jobId);
    
    public int updateStatus(@Param("status")Byte status,@Param("list")List<Long> ids);
}