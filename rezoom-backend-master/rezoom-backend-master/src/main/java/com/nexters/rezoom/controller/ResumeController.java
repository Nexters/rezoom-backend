package com.nexters.rezoom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.service.ResumeService;

@RestController
@RequestMapping("/")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;
    public void setResumeService(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping("/resume/get")
    @ResponseBody
    public String init(){
        return "getTest";
    }

    //이력서 삭제
    @DeleteMapping(value = "/resume/{user_id}/{resume_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResume(@PathVariable("resume_id") int resumeId, @PathVariable("user_id") String userId){ 
    	if(resumeId==0) {
    		System.out.println("resume이 널입니다.");
    	}if(userId==null) {
    		System.out.println("user_id가 널입니다.");
    	}
    	else {
    		System.out.println("userId는 "+userId);
    	}
    	int user_Id = Integer.parseInt(userId);
    	resumeService.deleteAllQuestion(resumeId, user_Id);
    }

    //문항 개별 삭제 
    @DeleteMapping(value = "/resume/{user_id}/{resume_id}/{question_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("resume_id") int resumeId, @PathVariable("user_id") String userId, @PathVariable("question_id") int questionId){ 
    	if(resumeId==0) {
    		System.out.println("resume이 널입니다.");
    	}if(userId==null) {
    		System.out.println("user_id가 널입니다.");
    	}
    	else {
    		System.out.println("userId는 "+userId);
    	}
    	int user_Id = Integer.parseInt(userId);
    	System.out.println("deleteQuestion시작!");
    	resumeService.deleteQuestion(resumeId, user_Id, questionId);
    }
    
    //문항 등록
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public void createQuestion(@RequestBody Question question) {
    	if(question==null) {
    		System.out.println("null입니다.");
    	}
    	else {
    		System.out.println("null아님");
    	}
    	resumeService.createQuestion(question);
    }
    
    //문항 상세보기
    @GetMapping("/question/{user_id}/{resume_id}/{question_id}") //question?? 생략해도 될까요??
    @ResponseBody
    public Question getQuestion(@PathVariable("user_id") String userId, @PathVariable("resume_id") int resumeId, @PathVariable("question_id") int questionId){
        System.out.print("getQuestion실행");
        int user_Id = Integer.parseInt(userId);

        Question question = resumeService.getQuestion(resumeId, user_Id, questionId);
        if(question == null)
            System.out.print("question이 널입니다");
        else {
        	System.out.println("question null아님");
        }
        return question;
    }
    
    //문항 수정
    @PostMapping(value = "/question") //putMapping시 null로 들어감
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@RequestBody Question question) {
        System.out.print("updateQuestion실행");

    	if(question==null) {
    		System.out.println("null입니다.");
    	}
    	else {
    		System.out.println("null아님");
    	}

    	resumeService.updateQuestion(question);
    }
    
    
}