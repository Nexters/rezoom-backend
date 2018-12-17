package com.nexters.rezoom.hashtag.presentation;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtags")
public class HashtagController {

    @Autowired
    private HashtagService service;

    // 임시
    private Member member = new Member("admin@admin.admin", "", "");

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public List<HashTagDto.ViewRes> getMyHashtags() {
        return service.getMyHashtags(member);
    }

    @GetMapping("/{value}/questions")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDto.ViewRes> getQuestionsRelatedHashtag(@PathVariable String value) {
        return service.getQuestionsRelatedHashtag(member, value);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyHashtag(@RequestBody HashTagDto.UpdateReq req) {
        service.modifyHashTag(member, req);
    }

    @DeleteMapping("/{value}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeHashtag(@PathVariable String value) {
        service.removeHashTag(member, value);
    }
}
