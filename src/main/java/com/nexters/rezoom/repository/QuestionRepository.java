package com.nexters.rezoom.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuestionRepository {

    //문항전체삭제
    void deleteQuestion(@Param("resumeId") int resumeId, @Param("userId") int userId);
}
