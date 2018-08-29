package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.ResumeStatisticsSummary;
import com.nexters.rezoom.dto.ResumeStatisticsDTO;
import com.nexters.rezoom.repository.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JaeeonJin on 2018-08-19.
 */
@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    /**
     * 최근 조회한 이력서 리스트 가져오기 <br>
     * 쿼리를 못짜서 로직으로 해결 <br>
     * 조회 내역은 무조건 4개만 가져옴 <br>
     * 조회 내역 중 중복된 내역은 삭제함
     */
    public List<Resume> getRecentResumeClick(String username) {
        List<Resume> recentClickResumeDTOList = dashboardRepository.selectRecentResumeClick(username);
        final int maxSize = 4;
        int count = 0;

        Map<Integer, Resume> temp = new LinkedHashMap<>();
        for (Resume resume : recentClickResumeDTOList) {
            int resumeId = resume.getResumeId();
            if (!temp.containsKey(resumeId) && count < maxSize) {
                temp.put(resumeId, resume);
                count++;
            }
        }

        recentClickResumeDTOList = new ArrayList<>(temp.values());
        return recentClickResumeDTOList;
    }

    /**
     * 이력서 통계 정보 가져오기
     */
    public ResumeStatisticsDTO getResumeStatistics(String username) {
        ResumeStatisticsSummary summary = dashboardRepository.selectResumeStatistics(username);
        ResumeStatisticsDTO dto = new ResumeStatisticsDTO(summary);
        return dto;
    }

    /**
     * 미지원 이력서 중 마감기한이 얼마남지 않은 가져오기
     * @param username
     * @return
     */
    public List<Resume> getDeadlineInfo(String username) {
        return dashboardRepository.selectResumeWithDeadline(username);
    }
}
