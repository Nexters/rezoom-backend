package com.nexters.rezoom.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexters.rezoom.repository.SearchDAO;

@Service
public class SearchServiceImpl implements SearchService {
	
	@Resource
	SearchDAO dao;
	
}
