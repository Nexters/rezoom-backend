package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ResumeRepository {
    void createResume(Resume resume);
    Resume selectOne(@Param("resumeId") int resumeId, @Param("username") String username);
    List<Resume> selectAll(String username);
    void updateResume(@Param("resume") Resume resume, @Param("username") String username);
    void deleteResume(@Param("resumeId") int resumeId, @Param("username") String username);
}
