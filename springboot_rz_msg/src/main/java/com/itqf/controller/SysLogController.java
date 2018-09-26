package com.itqf.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.service.SysLogService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;


@Controller
public class SysLogController {
	
	@Resource
	private SysLogService sysLogService;
	
	
	@RequestMapping("/sys/log/list")
	@ResponseBody
	public DataGridResult logList(@RequestParam Map<String, Object>  params) {
		
		return sysLogService.findLog(new Query(params));
	}
	
	
	
	
	

}
