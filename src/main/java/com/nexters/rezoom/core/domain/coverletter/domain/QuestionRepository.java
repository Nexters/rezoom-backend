package com.nexters.rezoom.core.domain.coverletter.domain;

import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

    Question findByKey(Long questionId, Member member);
    Page<Question> findAllByMember(Pageable pageable, Member member);
}
