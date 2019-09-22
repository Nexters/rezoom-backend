package com.nexters.rezoom.question.presentation;

import com.nexters.rezoom.coverletter.dto.PageRequest;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.application.QuestionService;
import com.nexters.rezoom.question.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    @GetMapping("")
    public Page<QuestionDto.ViewRes> getList(@AuthenticationPrincipal Member member, PageRequest pageRequest) {
        pageRequest.setSort(Sort.by("id").descending());
        return service.getList(member, pageRequest.of());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto.ViewRes getView(@AuthenticationPrincipal Member member, @PathVariable long id) {
        return service.getView(id, member);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDto.ViewRes> getQuestionsByHashtags(@AuthenticationPrincipal Member member, @RequestParam List<String> hashtags) {

        // TODO : 해시태그 복수로 검색할 수 있도록 변경하기
        return service.getQuestionsByHashtags(member, hashtags);
    }

}
