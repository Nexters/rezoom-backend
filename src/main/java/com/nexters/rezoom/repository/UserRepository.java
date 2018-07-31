package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by JaeeonJin on 2018-07-17.
 */
public interface UserRepository {
    void insertOne(User user);
    User selectOne(@Param("userName") String userName, @Param ("socialType") String socialType);
}
