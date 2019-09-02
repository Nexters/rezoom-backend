package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;

import java.util.List;

public interface CoverletterRepository {
    Coverletter save(Coverletter coverletter);
    Coverletter findById(Member member, long id);
    List<Coverletter> findAll(Member member, int beginRow, int numberPerPage);
    void delete(Coverletter coverletter);

    List<Coverletter> findByDeadline(Member member);
}
