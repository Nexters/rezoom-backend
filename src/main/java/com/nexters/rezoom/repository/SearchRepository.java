package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchRepository {
    List<Question> getResumeByHashTag(@Param("hashList") List<String> hashList);
}
