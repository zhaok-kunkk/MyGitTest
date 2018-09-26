package com.itqf.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.annotation.MyLog;
import com.itqf.service.ScheduJobLogService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;


@Controller
public class SchedulerLogController {

	@Resource
	private ScheduJobLogService scheduJobLogService;
	
	@MyLog("查询日志")
	@RequestMapping("schedule/log/list")
	@ResponseBody
	public DataGridResult findAllLog(Integer offset,Integer limit,@RequestParam(defaultValue="") String search) {
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("search", search);
		
		return scheduJobLogService.findAll(new Query(map));
	}
}
