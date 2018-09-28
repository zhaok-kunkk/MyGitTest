package com.itqf.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.annotation.MyLog;
import com.itqf.domain.ScheduleJob;
import com.itqf.service.ScheduleService;
import com.itqf.utils.DataGridResult;
import com.itqf.utils.Query;
import com.itqf.utils.R;

@Controller
public class SysSchedulerController {

	@Resource
	private ScheduleService scheduleService;
	
	@MyLog("查询任务")
	@RequestMapping("/schedule/job/list")
	@ResponseBody
	public DataGridResult scheduleList(String offset,String limit,@RequestParam(defaultValue="")String search) {
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("offset", offset);
		map.put("limit", limit);
		/*if (search==null) {
			search="";
		}*/
		map.put("search", search);
		
		return scheduleService.findSchedule(new Query(map));
	}
	
	@MyLog("增加任务")
	@RequestMapping("/schedule/job/save")
	@ResponseBody
	public R save(@RequestBody ScheduleJob scheduleJob) {
		
		return scheduleService.save(scheduleJob);
	}
	
	@MyLog("删除任务")
	@RequestMapping("/schedule/job/del")
	@ResponseBody
	public R del(@RequestBody List<Integer> jobId ) {
		
		return scheduleService.delete(jobId);
				
	}
	
	@RequestMapping("/schedule/job/info/{jobId}")
	@ResponseBody
	public R updateinfo(@PathVariable long jobId) {
		
		return scheduleService.findScheduleById(jobId);
	}
	
	@MyLog("修改任务")
	@RequestMapping("/schedule/job/update")
	@ResponseBody
	public R update(@RequestBody ScheduleJob scheduleJob) {
		
		return scheduleService.update(scheduleJob);
		
	}
	/**
	 * 暂停任务
	 */
	@RequestMapping("/schedule/job/pause")
	@ResponseBody
	public R pauseJob(@RequestBody List<Long> ids) {
		return scheduleService.pauseJob(ids);
	}
	
	/**
	 * 恢复任务
	 */
	@RequestMapping("/schedule/job/resume")
	@ResponseBody
	public R  resumeJob(@RequestBody List<Long> ids) {
		return scheduleService.resumeJob(ids);
	}
	
	/**
	 * 执行任务
	 */
	@RequestMapping("/schedule/job/run")
	@ResponseBody
	public R  runJob(@RequestBody List<Long> ids) {
		return scheduleService.runJob(ids);
	}
}
