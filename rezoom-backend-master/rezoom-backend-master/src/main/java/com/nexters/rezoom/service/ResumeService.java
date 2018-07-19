package com.nexters.rezoom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.repository.ResumeRepository;

@Service
public class ResumeService {
	
    @Autowired
    ResumeRepository resumeRepository;


    public void deleteResume(int resumeId, int userId){
        resumeRepository.deleteResume(resumeId, userId);
    }

    //문항 전체 삭제 
    public void deleteAllQuestion(int resumeId, int userId) {
    	System.out.println("ResumeService시작!");
    	 resumeRepository.deleteAllQuestion(resumeId, userId);
    } 
    
    //문항 개별 삭제 
    public void deleteQuestion(int resumeId, int userId, int questionId) {
    	System.out.println("deleteQuestion시작!");
    	 resumeRepository.deleteQuestion(resumeId, userId, questionId);
    } 
    
    //문항 삽입
    public void createQuestion(Question question) {
    	System.out.println("createQuestion시작!");
    	 resumeRepository.createQuestion(question);
    } 
    
    //문항 상세보기
    public Question getQuestion(int resumeId, int userId, int questionId) {
    	System.out.println("getQuestionService시작!");
    	return resumeRepository.getQuestion(resumeId, userId, questionId);
    } 

    //문항 수정
    public void updateQuestion(Question question) {
    	System.out.println("updateQuestionService시작!");
    	 resumeRepository.updateQuestion(question);
    } 
}