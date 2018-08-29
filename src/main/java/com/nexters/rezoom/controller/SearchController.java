package com.nexters.rezoom.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.view.QuestionView;
import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @GetMapping("/resumes")
    @ResponseStatus(HttpStatus.OK)
    public List<Resume> getResumesByCompanyName(Principal principal, @RequestParam String companyName){
        List<Resume> resumeList = searchService.getResumesByCompanyName(principal.getName(), companyName);
        return resumeList;
    }

    // TODO : 분기처리로직 - 인터페이스로 의존성 분리.
    @ApiOperation(value = "키워드 또는 해쉬태그... 로 문항을 검색한다.")
    @GetMapping("/questions")
    @JsonView(QuestionView.Search.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Question> getQuestionByKeyword(
            Principal principal,
            @ApiParam(value="keyword 또는 hashTag") @RequestParam("type") String searchType,
            @ApiParam(value="검색 keyword 또는 해쉬태그(예시: \"태그1,태그2,태그3\")") @RequestParam String keyword){
        List<Question> result;

        switch (searchType) {
            case "keyword":
                result = searchService.getQuestionsByKeyword(principal.getName(), keyword);
                return result;
            case "hashTag":
                List<String> hashTagList = Arrays.asList(keyword.split(","));
                result = searchService.getQuestionsByHashTags(hashTagList, principal.getName());
                return result;
            default:
                return null;
        }
    }

}
