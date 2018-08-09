package com.nexters.rezoom.repository;

import com.nexters.rezoom.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public interface HashTagRepository {
    void insertHashtags(@Param("list") List<String> hashtags, @Param("username") String username);
    void insertQuestionHashtagMapping(List<QuestionDTO> questions);
  
}
