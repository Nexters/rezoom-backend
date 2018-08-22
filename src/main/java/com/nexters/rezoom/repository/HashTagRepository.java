package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.domain.HashTagList;
import com.nexters.rezoom.dto.QuestionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public interface HashTagRepository {
    void insertHashtags(@Param("list") List<HashTag> hashtags, @Param("username") String username);
    void insertQuestionHashtagMapping(List<QuestionDTO> questions);
    List<HashTag> selectAll(@Param("username") String username);
    void deleteQuestionHashtagMapping(@Param("question") QuestionDTO questionDTO);
    List<HashTag> selectHashTagByKeyword(@Param("list") List<HashTag> hashtags, @Param("username") String username);
}
