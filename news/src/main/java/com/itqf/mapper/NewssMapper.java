package com.itqf.mapper;

import com.itqf.domain.Newss;

public interface NewssMapper {
    int insert(Newss record);

    int insertSelective(Newss record);
    
    Newss findById(int id);
}