package com.nexters.rezoom.question.presentation;

import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.application.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto.ViewRes getView(@AuthenticationPrincipal Member member, @PathVariable long id) {
        return service.getView(id, member);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDto.ViewRes> getQuestionsByHashtags(@AuthenticationPrincipal Member member, @RequestParam String hashtag) {
        return service.getQuestionsByHashtags(member, hashtag);
    }

}
