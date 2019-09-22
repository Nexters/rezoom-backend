package com.nexters.rezoom.hashtag.domain;

import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByMemberAndValue(Member member, String value);

    List<Hashtag> findAllByMember(Member member);
}