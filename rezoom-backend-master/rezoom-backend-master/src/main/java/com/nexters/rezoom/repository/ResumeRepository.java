package com.nexters.rezoom.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.nexters.rezoom.domain.Question;
import com.nexters.rezoom.domain.Resume;

@Mapper
@Repository
public interface ResumeRepository {
//userId 넣기

    void deleteResume(@Param("resumeId") int resumeId, @Param("userId") int userId);

    //문항 전체 삭제
    void deleteAllQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId);

    //문항 개별 삭제
	void deleteQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId, @Param("questionId") int questionId);
	
	//문항 추가
	void createQuestion(@Param("question") Question question);
	
	//문항 상세보기
	Question getQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId, @Param("questionId") int questionId);
	
	//문항 추가
	void updateQuestion(@Param("question") Question question);
}