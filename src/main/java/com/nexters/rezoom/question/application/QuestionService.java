package com.nexters.rezoom.question.application;

import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    public QuestionDto.ViewRes getView(long questionId, Member member) {
        Question question = repository.findByKey(questionId, member);
        if (question == null) {
            throw new RuntimeException("존재하지 않는 문항입니다.");
        }

        return new QuestionDto.ViewRes(question);
    }

}
