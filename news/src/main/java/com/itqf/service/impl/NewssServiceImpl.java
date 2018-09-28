package com.itqf.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itqf.domain.Newss;
import com.itqf.mapper.NewssMapper;
import com.itqf.service.NewssService;
import com.itqf.utis.R;
@Service

public class NewssServiceImpl implements NewssService {
	
	@Autowired
	private NewssMapper newssMapper;

	@Override
	public Newss findById(int id) {
		
		return newssMapper.findById(id);
	}
	

}
