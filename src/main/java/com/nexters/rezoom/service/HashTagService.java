package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.repository.HashTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by JaeeonJin on 2018-08-09.
 */
@Service
public class HashTagService {

    @Autowired
    HashTagRepository hashTagRepository;

    // 사용자가 입력한 모든 해시태그 조회
    public List<HashTag> getAllHashTag(String username){
        return hashTagRepository.selectAll(username);
    }
}
