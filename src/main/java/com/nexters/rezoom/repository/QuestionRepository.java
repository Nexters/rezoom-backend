package com.nexters.rezoom.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nexters.rezoom.domain.HashTagList;
import com.nexters.rezoom.dto.QuestionDTO;
import com.nexters.rezoom.dto.QuestionListResponseDTO;

public interface QuestionRepository {

    // 이력서에 문항 여러개 삽입
    void insertQuestions(@Param("resumeId") int resumeId, @Param("list") List<QuestionDTO> questionList, @Param("username") String username);

    // 이력서 내 모든 문항 조회
    List<QuestionListResponseDTO> selectAllQuestionByResumeId(@Param("resumeId") int resumeId, @Param("username") String username);

    // 이력서 내 단일 문항 상세보기
    QuestionDTO getQuestion(@Param("username") String username, @Param("resumeId") int resumeId, @Param("questionId") int questionId);

}
