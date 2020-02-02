package com.nexters.rezoom.core.domain.coverletter.application;

import com.nexters.rezoom.core.domain.coverletter.api.dto.QuestionDto;
import com.nexters.rezoom.core.domain.coverletter.domain.Hashtag;
import com.nexters.rezoom.core.domain.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.core.domain.coverletter.domain.Question;
import com.nexters.rezoom.core.domain.coverletter.domain.QuestionRepository;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
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
    private final HashtagRepository hashtagRepository;

    public QuestionService(QuestionRepository repository, HashtagRepository hashtagRepository) {
        this.repository = repository;
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * 문항 단건 조회
     */
    public QuestionDto.ViewRes getView(Long questionId, Member member) {
        Question question = repository.findByKey(questionId, member);
        if (question == null) {
            throw new BusinessException(ErrorType.QUESTION_NOT_FOUND);
        }

        return new QuestionDto.ViewRes(question);
    }

    /**
     * 특정 해쉬태그가 존재하는 문항을 조회한다.
     */
    @Transactional
    public List<QuestionDto.ViewRes> getQuestionsByHashtags(Member member, List<String> hashtags) {
        // TODO : 해시태그로 문항 검색하는 SQL - 성능 테스트 필요, 쿼리 DSL 통해 SQL로 한번에 조회하기
        Set<Question> questionSet = new HashSet<>();
        for (String hasthag : hashtags) {
            try {
                Hashtag findHashtag = hashtagRepository.findByMemberAndValue(member, hasthag)
                        .orElseThrow(() -> new BusinessException(ErrorType.HASHTAG_NOT_FOUND));

                questionSet.addAll(findHashtag.getQuestions());
            } catch (BusinessException e) {
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
