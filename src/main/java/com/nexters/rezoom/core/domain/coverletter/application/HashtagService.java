package com.nexters.rezoom.core.domain.coverletter.application;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import com.nexters.rezoom.core.domain.coverletter.domain.Hashtag;
import com.nexters.rezoom.core.domain.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.core.domain.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public HashtagService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * 사용자가 등록한 해쉬태그 중 1개 이상 문항에 등록된 해쉬태그만 조회한다.
     */
    public List<String> getMyHashtags(Member member) {
        return hashtagRepository.findAllByMember(member)
                .stream()
                .filter(hashTag -> hashTag.getQuestions() != null)
                .filter(hashTag -> !hashTag.getQuestions().isEmpty())
                .map(Hashtag::getValue)
                .collect(Collectors.toList());
    }

    public Hashtag getHashTag(Member member, String value) {
        return hashtagRepository.findByMemberAndValue(member, value)
                .orElseThrow(() -> new BusinessException(ErrorType.HASHTAG_NOT_FOUND));
    }
}