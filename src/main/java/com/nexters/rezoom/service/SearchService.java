package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.dto.QuestionListBySearchDTO;
import com.nexters.rezoom.repository.SearchRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    SearchRepository searchRepository;

    public List<Resume> getResumeByKeyword(String username, String keyword){
        return searchRepository.getResumeByKeyword(username, keyword);
    }
    public List<QuestionListBySearchDTO> getQuestionByHashTag(List<String> hashList, String username) {
        return searchRepository.getQuestionByHashTag(hashList, username);
    }
    public List<QuestionListBySearchDTO> getQuestionByKeyword(String username, String keyword){
        return searchRepository.getQuestionByKeyword(username, keyword);
    }
}
