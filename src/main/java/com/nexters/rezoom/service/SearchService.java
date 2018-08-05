package com.nexters.rezoom.service;

import com.nexters.rezoom.dto.QuestionDTO;
import com.nexters.rezoom.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public List<QuestionDTO> getResumeByHashTag(List<String> hashList, String username) {
        return searchRepository.getResumeByHashTag(hashList, username);
    }
}
