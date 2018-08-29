package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.domain.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public interface HashTagRepository {
    void insertHashtags(@Param("list") List<HashTag> hashtags, @Param("username") String username);
    void insertQuestionHashtagMapping(List<Question> questions);
    List<HashTag> selectAll(@Param("username") String username);
    void deleteQuestionHashtagMapping(@Param("question") Question question);
    List<HashTag> selectHashTagByKeyword(@Param("list") List<HashTag> hashtags, @Param("username") String username);
}
