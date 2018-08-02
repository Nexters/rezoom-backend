package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionRepository {
    //문항 전체 삭제
    void deleteAllQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId);

    //문항 개별 삭제
    void deleteQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId, @Param("questionId") int questionId);

    //문항 추가
    void createQuestion(@Param("question") Question question);

    //문항 상세보기
    Question getQuestion(@Param("userId") int userId, @Param("resumeId") int resumeId, @Param("questionId") int questionId);

    //문항 수정
    void updateQuestion(@Param("question") Question question);

    void insertHashTag(@Param("userId") int userId, @Param("list") List<String> hashTagList);

    List<Integer> getHashId(@Param("userId") int userId);


    void insertQuestionHash(@Param("questionId") int questionId, @Param("resumeId") int resumeId, @Param("userId") int userId, @Param("list") List<Integer> list);
}
