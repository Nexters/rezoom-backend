package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchRepository {
    List<Question> selectQuestionsByHashTags(@Param("hashList") List<String> hashList, @Param("username") String username);
    List<Question> selectQuestionsByKeyword(@Param("username") String username, @Param("keyword") String keyword);
    List<Resume> selectResumesByCompanyName(@Param("username") String username, @Param("companyName") String companyName);
}
