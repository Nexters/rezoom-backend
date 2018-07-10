package com.nexters.rezoom.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.nexters.rezoom.service.SearchService;

@Controller
public class SearchController {
	
	@Resource
	SearchService service;
	
}
