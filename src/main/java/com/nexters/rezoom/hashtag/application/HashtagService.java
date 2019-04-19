package com.nexters.rezoom.hashtag.application;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.ErrorCode;
import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class HashtagService {

    private final HashTagRepository repository;

    @Autowired
    public HashtagService(HashTagRepository repository) {
        this.repository = repository;
    }

    /**
     * 사용자가 등록한 해쉬태그 중 1개 이상 문항에 등록된 해쉬태그만 조회한다.
     * @return 해쉬태크 리스트 (문항에 적어도 1개 이상 등록된)
     */
    public List<HashTagDto.ViewRes> getMyHashtags(Member member) {
        return repository.findAll(member).stream()
                .filter(hashTag -> hashTag.getQuestions() != null) // not null
                .filter(hashTag -> !hashTag.getQuestions().isEmpty()) // not empty
                .map(HashTagDto.ViewRes::new)
                .collect(Collectors.toList());
    }

    public Hashtag getHashTag(Member member, String value) {
        Hashtag findHashtag = repository.findByKey(member, value);
        if (findHashtag == null) {
            throw new EntityNotFoundException(ErrorCode.HASHTAG_NOT_FOUND);
        }
        return findHashtag;
    }
}
