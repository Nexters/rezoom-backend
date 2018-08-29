package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionRepository {

    // 이력서에 문항 여러개 삽입
    void insertQuestions(@Param("resumeId") int resumeId, @Param("list") List<Question> questionList, @Param("username") String username);

    // 이력서 내 모든 문항 조회
    List<Question> selectAllQuestionByResumeId(@Param("resumeId") int resumeId, @Param("username") String username);

    // 이력서 내 단일 문항 상세보기
    Question getQuestion(@Param("username") String username, @Param("resumeId") int resumeId, @Param("questionId") int questionId);

    // 문항 1개 수정
    void updateQuestion(@Param("resumeId") int resumeId, @Param("question") Question question, @Param("username") String username);

    // 문항 삭제
    void deleteQuestion(@Param("resumeId") int resumeId, @Param("list") List<Integer> questionIds, @Param("username") String username);
}
