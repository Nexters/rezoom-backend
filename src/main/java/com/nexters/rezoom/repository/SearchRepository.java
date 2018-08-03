package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.Resume;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchRepository {

    List<Resume> getResumeByHashTag(@Param("hashList") List<String> hashList, @Param("usermame") String username);
}
