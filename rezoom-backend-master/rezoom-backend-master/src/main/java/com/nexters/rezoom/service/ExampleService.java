package com.nexters.rezoom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexters.rezoom.domain.Example;
import com.nexters.rezoom.repository.ExampleRepository;

@Service
public class ExampleService {

	@Autowired
	ExampleRepository exampleRepository;
	
	public List<Example> getExampleList() {
		return exampleRepository.selectAll();
	}
	
}
