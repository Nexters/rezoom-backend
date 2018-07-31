package com.nexters.rezoom.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.domain.QuestionList;
import com.nexters.rezoom.service.QuestionService;

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
    	System.out.println("createQuestion실행");
    	int resumeId = list.getResumeId();
		
    		//int userId = list.getUserId();
    		for(Question q : list.getList()) {
    			q.setResumeId(resumeId);
    			q.setUserId(getUserId());
    			questionService.createQuestion(q);
    		}
       }
    
    //문항 수정 >> 완료  >> 여러개로해야한다
    @PostMapping(value = "/") 
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@RequestBody QuestionList list){
        System.out.print("updateQuestion실행");
    	int resumeId = list.getResumeId();
        for(Question q : list.getList()) {
			q.setResumeId(resumeId);
			q.setUserId(getUserId());
			questionService.updateQuestion(q);
		}
    }
    
    //문항 상세보기 
    @PostMapping("/{question_id}") 
    @ResponseBody  //json 오류..(@RequestBody)
    public Question getQuestion(@RequestParam("resumeId") int resumeId, @PathVariable("question_id") int questionId){
        System.out.print("getQuestion실행");
        System.out.println("userId"+getUserId());
        	//int user_Id = Integer.parseInt(getUserId());
        	int userId = getUserId();
        Question q = questionService.getQuestion(userId, resumeId, questionId);
        return q;
    }
    
    //문항 개별 삭제 
    @PostMapping(value = "DELETE/{question_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@RequestParam("resumeId") int resumeId, @PathVariable("question_id") int questionId){ 
        System.out.print("deleteQuestion실행");
    //	int user_Id = Integer.parseInt(getUserId());
    	questionService.deleteQuestion(resumeId, getUserId(), questionId);
    }
    private int getUserId() {
      // int userId = ((User)session.getAttribute(SESSION_USER)).getUserId();
        return 40;
    }
}
