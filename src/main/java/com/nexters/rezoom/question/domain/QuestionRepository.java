package com.nexters.rezoom.question.domain;

import com.nexters.rezoom.member.domain.Member;

public interface QuestionRepository {

    // 문항 단건 조회
    Question findByKey(long questionId, Member member);

}
