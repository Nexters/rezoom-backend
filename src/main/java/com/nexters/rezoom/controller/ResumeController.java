package com.nexters.rezoom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	static String userId = "20"; //임시 userId - 세션에서 받아올 예정
	
    @Autowired
    private ResumeService resumeService;
    public void setResumeService(ResumeService resumeService){
        this.resumeService = resumeService;
    }

    @GetMapping("/resumes/get")
    @ResponseBody
    public String init(){
        return "getTest";
    }

    //이력서 삭제
    @DeleteMapping(value = "/resumes/{resume_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteResume(@PathVariable("resume_id") int resumeId){ 
        System.out.print("deleteResume실행");
    	int user_Id = Integer.parseInt(userId);
    	resumeService.deleteAllQuestion(resumeId, user_Id);
    	resumeService.deleteResume(resumeId, user_Id);
    }

    //문항 개별 삭제 
    @DeleteMapping(value = "/resumes/{resume_id}/questions/{question_id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("resume_id") int resumeId, @PathVariable("question_id") int questionId){ 
        System.out.print("deleteQuestion실행");
    	int user_Id = Integer.parseInt(userId);
    	resumeService.deleteQuestion(resumeId, user_Id, questionId);
    }
    
    //문항 등록
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.OK)
    public void createQuestion(@RequestBody Question question) {
        System.out.print("createQuestion실행");
    	resumeService.createQuestion(question);
    }
    
    //문항 상세보기 
    @GetMapping("/resumes/{resume_id}/questions/{question_id}") 
    @ResponseBody
    public Question getQuestion(@PathVariable("resume_id") int resumeId, @PathVariable("question_id") int questionId){
        System.out.print("getQuestion실행");
        int user_Id = Integer.parseInt(userId);
        Question question = resumeService.getQuestion(resumeId, user_Id, questionId);
        return question;
    }
    
    //문항 수정
    @PostMapping(value = "/questions") 
    @ResponseStatus(HttpStatus.OK)
    public void updateQuestion(@RequestBody Question question) {
        System.out.print("updateQuestion실행");
    	resumeService.updateQuestion(question);
    }
    
    
}