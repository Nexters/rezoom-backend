package com.nexters.rezoom.hashtag.domain;

import com.nexters.rezoom.member.domain.Member;

import java.util.List;

public interface HashtagRepository {
    Hashtag findByKey(Member member, String value);

    List<Hashtag> findAll(Member member);

    void save(Hashtag hashTag);
}
