package com.nexters.rezoom.temp.hashtag.application;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto_temp;
import com.nexters.rezoom.temp.hashtag.domain.HashTag;
import com.nexters.rezoom.temp.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.temp.member.domain.Member;
import com.nexters.rezoom.temp.question.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<QuestionDto_temp.ViewRes> getQuestionsRelatedHashtag(Member member, HashTag hashTag) {
        HashTag findHashtag = repository.findByKey(member, hashTag.getValue());

        // TODO : Stream 전환
        List<QuestionDto_temp.ViewRes> response = new ArrayList<>();
        for (Question q : findHashtag.getQuestions()) {
            Set<HashTagDto.ViewRes> sets = q.getHashTags().stream().map(HashTagDto.ViewRes::new).collect(Collectors.toSet());
            response.add(new QuestionDto_temp.ViewRes(q.getId(), q.getTitle(), q.getContents(), sets));
        }
        return response;
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
