package com.nexters.rezoom.question.application;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.ErrorCode;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import com.nexters.rezoom.question.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final HashtagService hashTagService;

    public QuestionService(QuestionRepository repository, HashtagService hashTagService) {
        this.repository = repository;
        this.hashTagService = hashTagService;
    }

    /**
     * 문항 단건 조회
     */
    public QuestionDto.ViewRes getView(long questionId, Member member) {
        Question question = repository.findByKey(questionId, member);
        if (question == null) {
            throw new EntityNotFoundException(ErrorCode.QUESTION_NOT_FOUND);
        }

        return new QuestionDto.ViewRes(question);
    }

    /**
     * 특정 해쉬태그가 존재하는 문항을 조회한다.
     */
    @Transactional
    public List<QuestionDto.ViewRes> getQuestionsByHashtags(Member member, List<String> hashtags) {
        // TODO : 해시태그로 문항 검색하는 SQL - 성능 테스트 필요
        Set<Question> questionSet = new HashSet<>();
        for (String hasthag : hashtags) {
            try {
                Hashtag findHashtag = hashTagService.getHashTag(member, hasthag);
                questionSet.addAll(findHashtag.getQuestions());
            } catch (EntityNotFoundException e) {
                // TODO : 개선할 필요가 있어 보인다.
                // do not anything
            }
        }

        return questionSet.stream()
                .map(QuestionDto.ViewRes::new)
                .collect(Collectors.toList());
    }

    public Page<QuestionDto.ViewRes> getList(Member member, Pageable pageable) {
        return repository.findAllByMember(pageable, member)
                .map(QuestionDto.ViewRes::new);
    }

}
