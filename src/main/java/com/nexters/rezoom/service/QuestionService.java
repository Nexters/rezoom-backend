package com.nexters.rezoom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

  //문항 전체 삭제 
    public void deleteAllQuestion(int resumeId, int userId) {
    	System.out.println("ResumeService시작!");
    	 questionRepository.deleteAllQuestion(resumeId, userId);
    } 
    
    //문항 개별 삭제 
    public void deleteQuestion(int resumeId, int userId, int questionId) {
    	System.out.println("deleteQuestion시작!");
    	 questionRepository.deleteQuestion(resumeId, userId, questionId);
    } 
    
    //문항 삽입
    public void createQuestion(Question question) {
    	System.out.println("createQuestion시작!");
    	 questionRepository.createQuestion(question);
    } 
    
    //문항 상세보기
    public Question getQuestion(int userId, int resumeId,int questionId) {
    	System.out.println("getQuestionService시작!");
    	return questionRepository.getQuestion(userId, resumeId, questionId);
    } 

    //문항 수정
    public void updateQuestion(Question question) {
    	System.out.println("updateQuestionService시작!");
    	 questionRepository.updateQuestion(question);
    } 
}
