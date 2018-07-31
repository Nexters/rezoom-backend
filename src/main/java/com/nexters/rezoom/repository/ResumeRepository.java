package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ResumeRepository {
    void createResume(Resume resume);
    Resume selectOne(@Param("resumeId") int resumeId, @Param("userId") int userId);
    List<Resume> selectAll(@Param("userId") int userId);
    void updateResume(@Param("resume")Resume resume, @Param("resumeId") int resumeId, @Param("userId") int userId);
    void deleteResume(@Param("resumeId") int resumeId, @Param("userId") int userId);
}
