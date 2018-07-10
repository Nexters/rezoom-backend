package com.nexters.rezoom.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nexters.rezoom.repository.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Resource
	MemberDAO dao;
	
}
