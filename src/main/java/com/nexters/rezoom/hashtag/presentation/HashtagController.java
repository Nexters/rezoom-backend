package com.nexters.rezoom.hashtag.presentation;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
public class HashtagController {

    @Autowired
    private HashtagService service;

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<HashTagDto.ViewRes> getMyHashtags(@AuthenticationPrincipal Member member) {
        return service.getMyHashtags(member);
    }

    @GetMapping("/{value}/questions")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDto.ViewRes> getQuestionsRelatedHashtag(@AuthenticationPrincipal Member member, @PathVariable String value) {
        return service.getQuestionsRelatedHashtag(member, value);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyHashtag(@AuthenticationPrincipal Member member, @RequestBody HashTagDto.UpdateReq req) {
        service.modifyHashTag(member, req);
    }

    @DeleteMapping("/{value}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeHashtag(@AuthenticationPrincipal Member member, @PathVariable String value) {
        service.removeHashTag(member, value);
    }
}
