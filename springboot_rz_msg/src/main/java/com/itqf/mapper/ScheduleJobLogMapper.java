package com.itqf.mapper;

import java.util.List;

import com.itqf.domain.ScheduleJobLog;

public interface ScheduleJobLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(ScheduleJobLog record);

    int insertSelective(ScheduleJobLog record);

    ScheduleJobLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(ScheduleJobLog record);

    int updateByPrimaryKey(ScheduleJobLog record);
    
    List<ScheduleJobLog> findAllLogByPage();
}