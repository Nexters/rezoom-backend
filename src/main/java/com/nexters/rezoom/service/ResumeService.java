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

    ResumeRepository resumeRepository;
    QuestionRepository questionRepository;

    @Autowired
    public ResumeService(ResumeRepository resumeRepository, QuestionRepository questionRepository) {
        this.resumeRepository = resumeRepository;
        this.questionRepository = questionRepository;
    }

    public void createResume(Resume resume){
        resumeRepository.createResume(resume);
    }

    // TODO : 페이징 처리르 어떻게 할 것인지....
    public List<Resume> getAllResume(int userId) {
        return resumeRepository.selectAll(userId);
    }

    public Resume getResume(int resumeId, int userId){
        return resumeRepository.selectOne(resumeId, userId);
    }

    public void updateResume(Resume resume, int resumeId, int userId){
        resumeRepository.updateResume(resume, resumeId, userId);
    }

    // TODO : 트랜잭션 처리 or CASCADE 처리
    public void deleteResume(int resumeId, int userId){
        resumeRepository.deleteResume(resumeId, userId);
        questionRepository.deleteAllQuestion(resumeId, userId);
    }

}
