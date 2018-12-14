package com.nexters.rezoom.hashtag.application;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
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

    // 사용자가 등록한 해쉬태그 리스트를 조회한다.
    public List<HashTagDto.ViewRes> getMyHashtags(Member member) {
        return repository.findAll(member).stream().map(HashTagDto.ViewRes::new).collect(Collectors.toList());
    }

    // 특정 해쉬태그가 등록된 문항 리스트를 조회한다.
    public List<QuestionDto.ViewRes> getQuestionsRelatedHashtag(Member member, HashTag hashTag) {
        HashTag findHashtag = repository.findByKey(member, hashTag.getValue());

        return findHashtag.getQuestions().stream().map(q -> {
            Set<HashTagDto.ViewRes> sets = q.getHashTags().stream().map(HashTagDto.ViewRes::new).collect(Collectors.toSet());
            return new QuestionDto.ViewRes(q.getId(), q.getTitle(), q.getContents(), sets);
        }).collect(Collectors.toList());
    }

    public void modifyHashTag(Member member, HashTagDto.UpdateReq req) {
        HashTag hashTag = repository.findByKey(member, req.getBeforeValue());
        hashTag.updateValue(req.getUpdatedValue());
    }

    public void removeHashTag(Member member, String value) {
        HashTag findHashTag = repository.findByKey(member, value);
        repository.delete(findHashTag);
    }

}
