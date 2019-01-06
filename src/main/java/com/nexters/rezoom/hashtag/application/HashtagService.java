package com.nexters.rezoom.hashtag.application;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class HashtagService {

    @Autowired
    private HashTagRepository repository;

    // 사용자가 등록한 해쉬태그 중 문항과 연관관계가 있는 해쉬태그 리스트를 조회한다.
    public List<HashTagDto.ViewRes> getMyHashtags(Member member) {
        return repository.findAll(member).stream()
                .filter(hashTag -> hashTag.getQuestions() != null) // not null
                .filter(hashTag -> !hashTag.getQuestions().isEmpty()) // not empty
                .map(HashTagDto.ViewRes::new)
                .collect(Collectors.toList());
    }

    // 특정 해쉬태그가 등록된 문항 리스트를 조회한다.
    public List<QuestionDto.ViewRes> getQuestionsRelatedHashtag(Member member, String value) {
        Hashtag findHashtag = getHashTag(member, value);

        return findHashtag.getQuestions().stream().map(q -> {
            Set<HashTagDto.ViewRes> sets = q.getHashtags().stream().map(HashTagDto.ViewRes::new).collect(Collectors.toSet());
            return new QuestionDto.ViewRes(q.getId(), q.getTitle(), q.getContents(), sets);
        }).collect(Collectors.toList());
    }

    public void modifyHashTag(Member member, HashTagDto.UpdateReq req) {
        Hashtag findHashtag = getHashTag(member, req.getBeforeValue());
        findHashtag.updateValue(req.getUpdatedValue());
    }

    public void removeHashTag(Member member, String value) {
        Hashtag findHashtag = getHashTag(member, value);
        repository.delete(findHashtag);
    }

    public Hashtag getHashTag(Member member, String value) {
        Hashtag findHashtag = repository.findByKey(member, value);
        if (findHashtag == null) {
            throw new RuntimeException("해쉬태그가 존재하지 않습니다.");
        }
        return findHashtag;
    }
}
