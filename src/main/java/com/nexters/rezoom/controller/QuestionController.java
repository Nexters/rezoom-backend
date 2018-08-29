package com.nexters.rezoom.controller;

import com.nexters.rezoom.dto.QuestionDTO;
import com.nexters.rezoom.dto.QuestionListRequestDTO;
import com.nexters.rezoom.dto.QuestionListResponseDTO;
import com.nexters.rezoom.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "특정 이력서 내에 여러개의 문항을 생성한다.")
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public void createQuestions(@RequestBody QuestionListRequestDTO questionListRequestDTO, Principal principal) {
        questionService.createQuestions(questionListRequestDTO, principal.getName());
    }

    @ApiOperation(value = "특정 이력서 내에 존재하는 모든 문항을 조회한다.")
    @GetMapping(value="")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionListResponseDTO> getAllQuestion(@RequestParam int resumeId, Principal principal) {
        return questionService.getAllQuestion(resumeId, principal.getName());
    }

    @ApiOperation(value = "특정 이력서 내의 하나의 문항을 조회한다.")
    @GetMapping("/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDTO getQuestion(@RequestParam int resumeId, @PathVariable int questionId, Principal principal){
        QuestionDTO question = questionService.getQuestion(principal.getName(), resumeId, questionId);
        return question;
    }

    @ApiOperation(value = "이력서 내 모든 문항 정보(제목,내용,해쉬태그)을 수정한다.")
    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyAllQuestion(@RequestBody QuestionListRequestDTO questionListRequestDTO, Principal principal) {
        questionService.updateAllQuestion(questionListRequestDTO, principal.getName());
    }

}
