package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.config.common.Paging;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class CoverletterService {

    private final CoverletterRepository coverletterRepository;
    private final HashTagRepository hashTagRepository;

    @Autowired
    public CoverletterService(CoverletterRepository coverletterRepository, HashTagRepository hashTagRepository) {
        this.coverletterRepository = coverletterRepository;
        this.hashTagRepository = hashTagRepository;
    }

    public long save(Member member, CoverletterDto.SaveReq req) {
        // init
        Coverletter coverletter = req.toEntity();
        coverletter.setMember(member);

        // set question
        coverletter.setQuestions(req.getQuestions().stream()
                .map(QuestionDto.SaveQuestionReq::toEntity)
                .collect(Collectors.toList()));

        // set hashtag
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), member));
        }

        coverletterRepository.save(coverletter);
        return coverletter.getId();
    }

    public void update(Member member, long coverletterId, CoverletterDto.UpdateReq req) {
        Coverletter coverletter = req.toEntity();
        coverletter.setMember(member);

        // set question
        coverletter.setQuestions(req.getQuestions().stream()
                .map(QuestionDto.UpdateQuestionReq::toEntity)
                .collect(Collectors.toList()));

        // set hashtag
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), member));
        }

        coverletterRepository.save(coverletter);
    }

    public CoverletterDto.ViewRes getView(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);
        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public CoverletterDto.ListRes getList(Member member, Paging paging) {
        List<Coverletter> findCoverletters = coverletterRepository.findAll(member, paging.getBeginRow(), paging.getNumberPerPage());
        return new CoverletterDto.ListRes(findCoverletters);
    }

    public void delete(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);
        coverletterRepository.delete(findCoverletter);
    }

    // -------------------------------------------------------------------------------

    private Coverletter getCoverletter(Member member, long id) {
        Coverletter findCoverletter = coverletterRepository.findById(member, id);
        if (findCoverletter == null) {
            throw new RuntimeException("존재하지 않는 자기소개서입니다.");
        }

        return findCoverletter;
    }

    /**
     * TODO : Key값이 애매하게 잡혀있어서, Merge를 사용할 수 없음 (개선하기)
     *
     * 중복없이 hashtag를 question에 설정하기 위한 메소드
     * > 중복O : 기존 해시태그 사용
     * > 중복X : 새로운 해시태그 생성
     */
    private Set<Hashtag> getUpdatedHashtags(Set<Hashtag> hashtags, Member member) {
        return hashtags.stream()
                .map(hashtag -> {
                    // 이미 존재하는 해쉬태그인지 확인
                    Hashtag findHashtag = hashTagRepository.findByKey(member, hashtag.getValue());
                    if (findHashtag != null) return findHashtag;

                    // 존재하지 않으면 생성
                    Hashtag newHashtag = new Hashtag(member, hashtag.getValue());
                    hashTagRepository.save(newHashtag);
                    return newHashtag;
                }).collect(Collectors.toSet());
    }

}
