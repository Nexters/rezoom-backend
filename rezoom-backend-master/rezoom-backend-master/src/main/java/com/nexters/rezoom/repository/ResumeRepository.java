package com.nexters.rezoom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.nexters.rezoom.domain.Question;


@Mapper
@Repository
public interface ResumeRepository {

    void deleteResume(@Param("resumeId") int resumeId, @Param("userId") int userId);

    //문항 전체 삭제
    void deleteAllQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId);

    //문항 개별 삭제
	void deleteQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId, @Param("questionId") int questionId);
	
	//문항 추가
	void createQuestion(@Param("question") Question question);
	
	//문항 상세보기
	Question getQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId, @Param("questionId") int questionId);
	
	//문항 수정
	void updateQuestion(@Param("question") Question question);
}