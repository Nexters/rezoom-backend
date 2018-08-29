package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    @Autowired
    ResumeRepository resumeRepository;

    public void createResume(Resume resume){
        resumeRepository.createResume(resume);
    }

    // TODO : 페이징 처리 필요
    public List<Resume> getAllResume(String username) {
        return resumeRepository.selectAll(username);
    }

    public Resume getResume(int resumeId, String username){
        return resumeRepository.selectOne(resumeId, username);
    }

    public void updateResume(Resume resume, String username){
        resumeRepository.updateResume(resume, username);
    }

    public void deleteResume(int resumeId, String username){
        resumeRepository.deleteResume(resumeId, username);
    }

}
