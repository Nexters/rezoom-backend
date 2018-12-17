package com.nexters.rezoom.question.presentation;

import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.application.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService service;

    private Member member = new Member("admin@admin.admin", "", "");

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto.ViewRes getView(@PathVariable long id) {
        return service.getView(id, member);
    }

}
