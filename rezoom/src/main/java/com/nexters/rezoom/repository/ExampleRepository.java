package com.nexters.rezoom.repository;

import java.util.List;

import com.nexters.rezoom.domain.Example;

public interface ExampleRepository {
	List<Example> selectAll();
}
