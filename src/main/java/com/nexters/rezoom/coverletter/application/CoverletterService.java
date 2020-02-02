package com.nexters.rezoom.coverletter.application;

import com.nexters.global.exception.BusinessException;
import com.nexters.global.exception.ErrorType;
import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.coverletter.dto.CoverletterDto;
import com.nexters.rezoom.coverletter.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class CoverletterService {

    private final CoverletterRepository coverletterRepository;
    private final HashtagRepository hashTagRepository;

    @Autowired
    public CoverletterService(CoverletterRepository coverletterRepository, HashtagRepository hashtagRepository) {
        this.coverletterRepository = coverletterRepository;
        this.hashTagRepository = hashtagRepository;
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

        coverletter.checkPassStatus();
        Coverletter savedCoverletter = coverletterRepository.save(coverletter);

        return savedCoverletter.getId();
    }

    // TODO : 문제 있음. hashtag key 문제로 create와 동일하게 작업.
    // TODO : create_date 등의 추가 데이터 누락. 일일히 set 해줘야하는 문제 있음.
    public void update(Member member, long coverletterId, CoverletterDto.UpdateReq req) {
        Coverletter existCoverletter = this.getCoverletter(member, coverletterId);

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

        coverletter.checkPassStatus();
        coverletterRepository.save(coverletter);
    }

    public CoverletterDto.ViewRes getView(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);

        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public Page<CoverletterDto.ViewRes> getList(Member member, Pageable pageable) {
        return coverletterRepository.findAllByMember(pageable, member)
                .map(CoverletterDto.ViewRes::new);
    }

    public CoverletterDto.ListRes searchByCompanyName(Member member, String companyName) {
        List<Coverletter> coverletters = coverletterRepository.findAllByMemberAndCompanyNameStartsWith(member, companyName);
        return new CoverletterDto.ListRes(coverletters);
    }

    public void delete(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);
        coverletterRepository.delete(findCoverletter);
    }

    private Coverletter getCoverletter(Member member, long id) {
        Optional<Coverletter> findCoverletter = coverletterRepository.findByIdAndMember(id, member);
        if (!findCoverletter.isPresent()) {
            throw new BusinessException(ErrorType.COVERLETTER_NOT_FOUND);
        }

        return findCoverletter.get();
    }

    /**
     * TODO : Key값이 애매하게 잡혀있어서, Merge를 사용할 수 없음 (개선하기)
     * 중복없이 hashtag를 question에 설정하기 위한 메소드
     * <p>
     * > 중복O : 기존 해시태그 사용
     * > 중복X : 새로운 해시태그 생성
     */
    private Set<Hashtag> getUpdatedHashtags(Set<Hashtag> hashtags, Member member) {
        Set<Hashtag> resultHashtags = new HashSet<>();

        for (Hashtag hashtag : hashtags) {

            Optional<Hashtag> optionalHashtag = hashTagRepository.findByMemberAndValue(member, hashtag.getValue());
            if (optionalHashtag.isPresent()) {
                resultHashtags.add(optionalHashtag.get());
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
