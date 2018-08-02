package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.domain.QuestionList;
import com.nexters.rezoom.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    HttpSession session;

    //문항 등록 >> 여러 개
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public void createQuestion(@RequestBody QuestionList list) {
        int resumeId = list.getResumeId();

        for(Question q : list.getList()) {
            q.setResumeId(resumeId);
            q.setUserId(getUserId());
            questionService.createQuestion(q);
        }

    }

    //문항 수정 >> 완료  >> 여러개로해야한다
    @PutMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@RequestBody QuestionList list){
        int resumeId = list.getResumeId();
        for(Question q : list.getList()) {
            q.setResumeId(resumeId);
            q.setUserId(getUserId());
            questionService.updateQuestion(q);
        }
    }

    //문항 상세보기
    @GetMapping("/{question_id}")
    @ResponseBody  //json 오류..(@RequestBody)
    public Question getQuestion(@RequestParam("resumeId") int resumeId, @PathVariable("question_id") int questionId){
        System.out.println("userId"+getUserId());
        //int user_Id = Integer.parseInt(getUserId());
        int userId = getUserId();
        Question q = questionService.getQuestion(userId, resumeId, questionId);
        return q;
    }

    //문항 개별 삭제 
    @DeleteMapping(value = "/{question_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@RequestParam("resumeId") int resumeId, @PathVariable("question_id") int questionId){
        questionService.deleteQuestion(resumeId, getUserId(), questionId);
    }
    private int getUserId() {
        // int userId = ((User)session.getAttribute(SESSION_USER)).getUserId();
        return 40;
    }
}
