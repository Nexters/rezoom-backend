package com.nexters.rezoom.coverletter.application;

import com.nexters.config.exception.EntityNotFoundException;
import com.nexters.config.exception.ErrorCode;
import com.nexters.rezoom.coverletter.domain.Hashtag;
import com.nexters.rezoom.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class HashtagService {

    private final HashtagRepository repository;

    @Autowired
    public HashtagService(HashtagRepository repository) {
        this.repository = repository;
    }

    /**
     * 사용자가 등록한 해쉬태그 중 1개 이상 문항에 등록된 해쉬태그만 조회한다.
     * @return 해쉬태그 리스트 (문항에 적어도 1개 이상 등록된)
     */
    public List<String> getMyHashtags(Member member) {
        return repository.findAllByMember(member).stream()
                .filter(hashTag -> hashTag.getQuestions() != null) // not null
                .filter(hashTag -> !hashTag.getQuestions().isEmpty()) // not empty
                .map(Hashtag::getValue)
                .collect(Collectors.toList());
    }

    public Hashtag getHashTag(Member member, String value) {
        Hashtag findHashtag = repository.findByMemberAndValue(member, value);
        if (findHashtag == null) {
            throw new EntityNotFoundException(ErrorCode.HASHTAG_NOT_FOUND);
        }
        return findHashtag;
    }
}