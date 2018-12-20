package com.nexters.rezoom.member.domain;

public interface MemberRepository {
    Member findById(String id);

    void save(Member member);
}
