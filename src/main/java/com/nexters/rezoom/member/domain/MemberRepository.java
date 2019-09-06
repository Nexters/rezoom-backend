package com.nexters.rezoom.member.domain;

import java.util.List;

public interface MemberRepository {
    Member findById(String id);
    void save(Member member);

    List<Member> findActiveMembers();
}
