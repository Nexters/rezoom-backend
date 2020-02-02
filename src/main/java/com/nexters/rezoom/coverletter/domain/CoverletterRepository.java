package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2019-10-07
 * Github : http://github.com/momentjin
 */

@Repository
public interface CoverletterRepository extends JpaRepository<Coverletter, Long> {

    Optional<Coverletter> findByIdAndMember(Long id, Member member);
    Page<Coverletter> findAllByMember(Pageable pageable, Member member);
    List<Coverletter> findAllByDeadlineGreaterThanEqual(Deadline deadline);
    List<Coverletter> findAllByMemberAndCompanyNameStartsWith(Member member, String companyName);
}
