package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.RecentClickResume;
import com.nexters.rezoom.dto.RecentClickResumeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-18.
 */
public interface RecentClickResumeRepository {
    List<RecentClickResumeDTO> selectAllByResume(@Param("username") String username);
    void insertByResume(RecentClickResume recentClickResume);
}
