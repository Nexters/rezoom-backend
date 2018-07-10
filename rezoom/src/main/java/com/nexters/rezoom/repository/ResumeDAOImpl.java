package com.nexters.rezoom.repository;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResumeDAOImpl implements ResumeDAO {
	
	@Resource
	SqlSessionTemplate template;
	
}
