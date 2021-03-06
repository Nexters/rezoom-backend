package com.nexters.rezoom.core.domain.coverletter.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Optional<Hashtag> findByMemberAndValue(Member member, String value);
    List<Hashtag> findAllByMember(Member member);
}