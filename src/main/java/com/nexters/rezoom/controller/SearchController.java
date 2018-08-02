package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/{type}")
    public List<Resume> getResumeByHashTag(Principal principal, @PathVariable("type") String type, @RequestParam(value="keyword") String keyword){
        
        List<String> keywordList = Arrays.asList(keyword.split(","));
        List<Resume> resumeList = searchService.getResumeByHashTag(keywordList, principal.getName());

        return resumeList;
    }

}
