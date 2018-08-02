package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    //문항 전체 삭제
    public void deleteAllQuestion(int resumeId, int userId) {
        questionRepository.deleteAllQuestion(resumeId, userId);
    }

    //문항 개별 삭제
    public void deleteQuestion(int resumeId, int userId, int questionId) {
        questionRepository.deleteQuestion(resumeId, userId, questionId);
    }

    //문항 삽입
    public void createQuestion(Question question) {

        questionRepository.createQuestion(question);
        questionRepository.insertHashTag(question.getUserId(), question.getHashTagList());

        //핵 비효율적인 느낌각
        List<Integer> list = questionRepository.getHashId(question.getUserId());
        questionRepository.insertQuestionHash(question.getQuestionId(), question.getResumeId(), question.getUserId(), list);

    }

    //문항 상세보기
    public Question getQuestion(int userId, int resumeId, int questionId) {
        return questionRepository.getQuestion(userId, resumeId, questionId);
    }

    //문항 수정
    public void updateQuestion(Question question) {
        questionRepository.updateQuestion(question);
    }
}
