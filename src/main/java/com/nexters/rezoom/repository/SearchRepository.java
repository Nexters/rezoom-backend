package com.nexters.rezoom.repository;

import com.nexters.rezoom.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchRepository {
    List<QuestionDTO> getResumeByHashTag(@Param("hashList") List<String> hashList, @Param("username") String username);
}
