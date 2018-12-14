package com.nexters.rezoom.hashtag.domain;

import com.nexters.rezoom.member.domain.Member;

import java.util.List;

public interface HashTagRepository {
    HashTag findByKey(Member member, String value);

    List<HashTag> findAll(Member member);

    void delete(HashTag hashTag);

    void save(HashTag hashTag);
}
