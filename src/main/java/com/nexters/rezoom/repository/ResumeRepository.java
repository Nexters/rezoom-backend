package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ResumeRepository {

    Resume getResume(@Param("resumeId") int resumeId, @Param("userId") int userId);

    void updateResume(@Param("resume")Resume resume, @Param("resumeId") int resumeId, @Param("userId") int userId);

    void deleteResume(@Param("resumeId") int resumeId, @Param("userId") int userId);

    void createResume(Resume resume);

    int getUserId(@Param("userName") String userName);

}
