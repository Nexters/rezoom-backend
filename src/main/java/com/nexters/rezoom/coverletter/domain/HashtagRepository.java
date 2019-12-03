package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Hashtag findByMemberAndValue(Member member, String value);

    List<Hashtag> findAllByMember(Member member);
}