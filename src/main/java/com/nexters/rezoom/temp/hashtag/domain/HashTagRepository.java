package com.nexters.rezoom.temp.hashtag.domain;

import com.nexters.rezoom.temp.member.domain.Member;

import java.util.List;

public interface HashTagRepository {
    HashTag findByKey(Member member, String value);

    List<HashTag> findAll(Member member);

    void delete(HashTag hashTag);
}
