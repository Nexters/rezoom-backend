package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.RecentClickResume;
import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.repository.RecentClickResumeRepository;
import com.nexters.rezoom.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    RecentClickResumeRepository clickRepository;

    public void createResume(Resume resume){
        resumeRepository.createResume(resume);
    }

    // TODO : 페이징 처리 필요
    public List<Resume> getAllResume(String username) {
        return resumeRepository.selectAll(username);
    }

    // TODO : 최근 조회한 데이터는 이력서가 될 수 있고, 증빙자료가 될 수도 있어서,, 공통적으로 처리할 수 있는 AOP 등 사용해서 해결하기
    // TODO : 트랜잭션 필요
    public Resume getResume(int resumeId, String username){
        RecentClickResume recentClickResume = new RecentClickResume();
        recentClickResume.setResumeId(resumeId);
        recentClickResume.setUsername(username);
        clickRepository.insertByResume(recentClickResume);

        return resumeRepository.selectOne(resumeId, username);
    }

    public void updateResume(Resume resume, String username){
        resumeRepository.updateResume(resume, username);
    }

    public void deleteResume(int resumeId, String username){
        resumeRepository.deleteResume(resumeId, username);
    }

}
