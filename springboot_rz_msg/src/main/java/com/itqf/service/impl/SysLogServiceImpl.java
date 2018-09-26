package com.itqf.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.domain.SysLog;
import com.itqf.mapper.SysLogMapper;
import com.itqf.service.SysLogService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;

/**
*author：李丽婷
*company：千锋互联
*date:2018年7月17日 下午2:31:19
*file:SysLogServiceImpl.java
*desc:
*/
@Service
public class SysLogServiceImpl implements SysLogService {

	@Resource
	private SysLogMapper sysLogMapper;
	
	
	@Override
	public void saveLog(SysLog log) {
		// TODO Auto-generated method stub
		sysLogMapper.insert(log);
	}


	@Override
	public DataGridResult findLog(Query query) {
		Integer limit = (Integer) query.get("limit");
		Integer offset = (Integer) query.get("offset");
		//分页工具类
		PageHelper.offsetPage(offset, limit);
		
		 List<SysLog> list =  sysLogMapper.findAllLog();
		
		 PageInfo<SysLog> pageInfo = new PageInfo<>(list);
		 
		 long total  = pageInfo.getTotal();
		 List<SysLog> rows =  pageInfo.getList();
		 
		 
		
		return new DataGridResult(rows,Integer.parseInt(total+""));
	}

}
