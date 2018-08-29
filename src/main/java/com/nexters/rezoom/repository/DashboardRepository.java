package com.nexters.rezoom.repository;

import com.nexters.rezoom.dto.RecentClickResumeDTO;
import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.ResumeStatisticsSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-18.
 */
public interface DashboardRepository {
    // 이력서 조회했을 때 조회 내역을 기록
    void insertResumeClick(RecentClickResumeDTO recentClickResume);

    // 최근 조회한 이력서 리스트 조회
    List<Resume> selectRecentResumeClick(@Param("username") String username);

    // 이력서 통계 정보 조회
    ResumeStatisticsSummary selectResumeStatistics(@Param("username") String username);

    // 이력서 마감 날짜 조회
    List<Resume> selectResumeWithDeadline(@Param("username") String username);
}
