package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.User;
import com.nexters.rezoom.repository.QuestionRepository;
import com.nexters.rezoom.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Resume getResume(int resumeId, int userId){
        return resumeRepository.getResume(resumeId, userId);
    }

    public void createResume(Resume resume){
        resumeRepository.createResume(resume);
    }

    public void updateResume(Resume resume, int resumeId, int userId){
        resumeRepository.updateResume(resume, resumeId, userId);
    }

    public void deleteResume(int resumeId, int userId){
        resumeRepository.deleteResume(resumeId, userId);
    }

    public void deleteQuestion(int resumeId, int userId){
        questionRepository.deleteQuestion(resumeId, userId);
    }

    public int getUserId(String userName){
        return resumeRepository.getUserId(userName);
    }


}
