package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.config.dto.Paging;
import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.ErrorCode;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Coverletter coverletter = req.toEntity();
        coverletter.setMember(member);

        // set questions
        List<Question> questions = new ArrayList<>();
        for (QuestionDto.SaveReq questionReq : req.getQuestions()) {
            questions.add(questionReq.toEntity());
        }
        coverletter.setQuestions(questions);

        // set hashtags
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), member));
        }

        coverletterRepository.save(coverletter);
        return coverletter.getId();
    }

    // TODO : save() 메소드와 구조가 매우 유사함 (개선하기)
    public void update(Member member, long coverletterId, CoverletterDto.UpdateReq req) {
        Coverletter coverletter = req.toEntity();
        coverletter.setMember(member);

        // set questions
        List<Question> questions = new ArrayList<>();
        for (QuestionDto.UpdateReq questionReq : req.getQuestions()) {
            questions.add(questionReq.toEntity());
        }
        coverletter.setQuestions(questions);

        // set hashtags
        for (Question question : coverletter.getQuestions()) {
            question.setHashtags(getUpdatedHashtags(question.getHashtags(), member));
        }

        coverletterRepository.save(coverletter);
    }

    public CoverletterDto.ViewRes getView(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);

        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public CoverletterDto.ListRes getList(Member member, int pageNo) {
        Paging paging = new Paging(pageNo);
        List<Coverletter> findCoverletters = coverletterRepository.findAll(member, paging.getBeginRow(), paging.getNumberPerPage());

        return new CoverletterDto.ListRes(findCoverletters);
    }

    public void delete(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);
        coverletterRepository.delete(findCoverletter);
    }

    private Coverletter getCoverletter(Member member, long id) {
        Coverletter findCoverletter = coverletterRepository.findById(member, id);
        if (findCoverletter == null) {
            throw new EntityNotFoundException(ErrorCode.COVERLETTER_NOT_FOUND);
        }

        return findCoverletter;
    }

    /**
     * TODO : Key값이 애매하게 잡혀있어서, Merge를 사용할 수 없음 (개선하기)
     * 중복없이 hashtag를 question에 설정하기 위한 메소드
     *
     * > 중복O : 기존 해시태그 사용
     * > 중복X : 새로운 해시태그 생성
     */
    private Set<Hashtag> getUpdatedHashtags(Set<Hashtag> hashtags, Member member) {
        Set<Hashtag> resultHashtags = new HashSet<>();

        for (Hashtag hashtag : hashtags) {
            Hashtag findHashtag = hashTagRepository.findByKey(member, hashtag.getValue());
            if (findHashtag != null) {
                resultHashtags.add(findHashtag);
                continue;
            }

            // 존재하지 않는 태그는 새로 생성한다.
            Hashtag newHashtag = new Hashtag(member, hashtag.getValue());
            hashTagRepository.save(newHashtag);
            resultHashtags.add(newHashtag);
        }

        return resultHashtags;
    }

}
