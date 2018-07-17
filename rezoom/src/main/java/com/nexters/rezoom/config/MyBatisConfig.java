package com.nexters.rezoom.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = { "com.nexters.rezoom.repository" })
public class MyBatisConfig {}
