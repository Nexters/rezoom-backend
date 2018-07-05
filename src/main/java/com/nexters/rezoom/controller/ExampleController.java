package com.nexters.rezoom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.rezoom.domain.Example;
import com.nexters.rezoom.service.ExampleService;

@RestController
@RequestMapping("examples")
public class ExampleController {

	@Autowired
	ExampleService exampleService;
	
	@GetMapping("/")
	public List<Example> getAllExamples() {
		List<Example> examples = exampleService.getExampleList();
		return examples;
	}
	
}
