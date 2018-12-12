package com.nexters.rezoom.coverletter.domain;

import com.nexters.rezoom.member.domain.Member;

import java.util.List;

public interface CoverletterRepository {
    void save(Coverletter coverletter);

    Coverletter findById(Member member, long id);

    List<Coverletter> findAll(Member member, int begin, int end);

    void delete(Coverletter coverletter);
}
