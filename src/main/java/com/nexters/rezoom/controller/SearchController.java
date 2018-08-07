package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.dto.QuestionListBySearchDTO;
import com.nexters.rezoom.service.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "회사명으로 내가 쓴 이력서를 검색한다.")
    @GetMapping("/resume")
    @ResponseStatus(HttpStatus.OK)
    public List<Resume> getResumeByKeyword(Principal principal, @RequestParam(value="keyword") String keyword){
        List<Resume> resumeList = searchService.getResumeByKeyword(principal.getName(), keyword);
        return resumeList;
    }

    @ApiOperation(value = "키워드로 문항을 검색한다.")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionListBySearchDTO> getQuestionByKeyword(Principal principal, @RequestParam(value="keyword") String keyword){
        List<QuestionListBySearchDTO> questionList = searchService.getQuestionByKeyword(principal.getName(), keyword);
        return questionList;
    }

    @ApiOperation(value = "해시태그로 문항을 검색한다.")
    @GetMapping("/{type}")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionListBySearchDTO> getQuestionByHashTag(Principal principal, @PathVariable("type") String type, @RequestParam(value="keyword") String keyword){
        List<String> keywordList = Arrays.asList(keyword.split(","));
        List<QuestionListBySearchDTO> questionList = searchService.getQuestionByHashTag(keywordList, principal.getName());
        return questionList;
    }

}
