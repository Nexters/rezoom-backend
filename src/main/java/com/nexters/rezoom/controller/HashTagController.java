package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.service.HashTagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-09.
 */
@RestController
@RequestMapping("/hashTags")
public class HashTagController {

    @Autowired
    HashTagService hashTagService;

    @ApiOperation(value = "사용자가 작성한 모든 해시태그를 조회한다.")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<HashTag> getAllHashTag(Principal principal){
        return hashTagService.getAllHashTag(principal.getName());
    }

}
