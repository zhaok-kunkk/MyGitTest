package com.itqf.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itqf.domain.Newss;
import com.itqf.service.NewssService;
import com.itqf.utis.R;

@Controller
public class ReceiveController {
	@Autowired
	private NewssService newssService;
	
	@RequestMapping("/screen/weibo")
	@ResponseBody
	public String findById(@RequestParam("id") int id){
		return "_________________" + id;
		//return newssService.findById(id);
		
	}
	
}
