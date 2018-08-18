package com.nexters.rezoom.service;

import com.nexters.rezoom.dto.RecentClickResumeDTO;
import com.nexters.rezoom.repository.RecentClickResumeRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by JaeeonJin on 2018-08-18.
 */
@Service
public class RecentClickResumeService {

    @Autowired
    RecentClickResumeRepository clickRepository;

    /**
     * 쿼리를 못짜서 로직으로 해결 <br>
     * 조회 내역은 무조건 4개만 가져옴 <br>
     * 조회 내역 중 중복된 내역은 삭제함
     */
    public List<RecentClickResumeDTO> selectRecentResumeClick(@Param("username") String username) {
        List<RecentClickResumeDTO> recentClickResumeDTOList = clickRepository.selectAllByResume(username);

        final int maxSize = 4;
        int count = 0;
        Map<Integer, RecentClickResumeDTO> temp = new HashMap<>();
        for (RecentClickResumeDTO recentClickResumeDTO : recentClickResumeDTOList) {
            int resumeId = recentClickResumeDTO.getResumeId();
            if (!temp.containsKey(resumeId) && count < maxSize) {
                temp.put(resumeId, recentClickResumeDTO);
                count++;
            }
        }
        recentClickResumeDTOList = new ArrayList<>(temp.values());

        return recentClickResumeDTOList;
    }
}
