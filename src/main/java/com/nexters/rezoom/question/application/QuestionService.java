package com.nexters.rezoom.question.application;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.ErrorCode;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final HashtagService hashTagService;

    public QuestionService(QuestionRepository repository, HashtagService hashTagService) {
        this.repository = repository;
        this.hashTagService = hashTagService;
    }

    public QuestionDto.ViewRes getView(long questionId, Member member) {
        Question question = repository.findByKey(questionId, member);
        if (question == null) {
            throw new EntityNotFoundException(ErrorCode.QUESTION_NOT_FOUND);
        }

        return new QuestionDto.ViewRes(question);
    }

    public List<QuestionDto.ViewRes> getQuestionsByHashtags(Member member, String value) {
        Hashtag findHashtag = hashTagService.getHashTag(member, value);

        return findHashtag.getQuestions().stream()
                .map(QuestionDto.ViewRes::new)
                .collect(Collectors.toList());
    }

}
