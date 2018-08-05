package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

        public List<Question> getResumeByHashTag(List<String> hashList) {
        return searchRepository.getResumeByHashTag(hashList);
    }
}
