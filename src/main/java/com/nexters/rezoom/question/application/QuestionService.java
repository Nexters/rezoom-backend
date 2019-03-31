package com.nexters.rezoom.question.application;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
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

    /**
     * 문항 단건 조회
     */
    public QuestionDto.ViewRes getView(long questionId, Member member) {
        Question question = repository.findByKey(questionId, member);
        if (question == null) {
            throw new RuntimeException("존재하지 않는 문항입니다.");
        }

        return new QuestionDto.ViewRes(question);
    }

    /**
     * 특정 해쉬태그가 존재하는 문항을 조회한다.
     */
    public List<QuestionDto.ViewRes> getQuestionsByHashtags(Member member, String value) {
        Hashtag findHashtag = hashTagService.getHashTag(member, value);
        return findHashtag.getQuestions().stream()
                .map(q -> {
                    Set<HashTagDto.ViewRes> sets = q.getHashtags().stream()
                            .map(HashTagDto.ViewRes::new)
                            .collect(Collectors.toSet());
                    return new QuestionDto.ViewRes(q.getId(), q.getTitle(), q.getContents(), sets);
                }).collect(Collectors.toList());
    }

}
