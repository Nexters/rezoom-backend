package com.nexters.rezoom.question.domain;

import com.nexters.rezoom.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

    Question findByKey(long questionId, Member member);

    Page<Question> findAllByMember(Pageable pageable, Member member);

}
