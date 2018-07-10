package com.nexters.rezoom.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.nexters.rezoom.service.ResumeService;

@Controller
public class ResumeController {
	
	@Resource
	ResumeService service;
}
