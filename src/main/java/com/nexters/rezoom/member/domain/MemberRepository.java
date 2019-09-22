package com.nexters.rezoom.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findById(String id);

    // TODO : 현재 활성화된 유저만 조회할 수 있도록 수정해야 한다. (필드가 필요함, 로그인 접속 기록 등으로 추가해서 확인할 것)
    List<Member> findAll();
}
