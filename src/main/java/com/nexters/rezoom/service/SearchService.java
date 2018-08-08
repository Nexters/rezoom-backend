package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.dto.QuestionListBySearchDTO;
import com.nexters.rezoom.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public List<Resume> getResumesByCompanyName(String username, String companyName){
        return searchRepository.selectResumesByCompanyName(username, companyName);
    }

    public List<QuestionListBySearchDTO> getQuestionsByHashTags(List<String> hashList, String username) {
        return searchRepository.selectQuestionsByHashTags(hashList, username);
    }

    public List<QuestionListBySearchDTO> getQuestionsByKeyword(String username, String keyword){
        return searchRepository.selectQuestionsByKeyword(username, keyword);
    }

}
