package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.dto.QuestionListSearchDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchRepository {
    List<QuestionListSearchDTO> selectQuestionsByHashTags(@Param("hashList") List<String> hashList, @Param("username") String username);
    List<QuestionListSearchDTO> selectQuestionsByKeyword(@Param("username") String username, @Param("keyword") String keyword);
    List<Resume> selectResumesByCompanyName(@Param("username") String username, @Param("companyName") String companyName);
}
