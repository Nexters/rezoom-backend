package com.nexters.rezoom.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.nexters.rezoom.service.MemberService;

@Controller
public class MemberController {
	
	@Resource
	MemberService service;
	
}
