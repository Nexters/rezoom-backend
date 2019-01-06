package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.config.common.Paging;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class CoverletterService {

    private final QuestionRepository questionRepository;
    private final CoverletterRepository coverletterRepository;
    private final HashTagRepository hashTagRepository;

    @Autowired
    public CoverletterService(CoverletterRepository coverletterRepository, HashTagRepository hashTagRepository, QuestionRepository questionRepository) {
        this.coverletterRepository = coverletterRepository;
        this.hashTagRepository = hashTagRepository;
        this.questionRepository = questionRepository;
    }

    public long save(Member member, CoverletterDto.SaveReq req) {
        // set coverletter
        Coverletter coverletter = new Coverletter(member, req.getCompanyName());

        // set question & hashtag
        coverletter.setQuestions(req.getQuestions()
                .stream()
                .map(questionReq ->
                        new Question(questionReq.getTitle(), questionReq.getContents())
                                .setHashtags(getUpdatedHashtags(questionReq.getHashtags(), member)))
                .collect(Collectors.toList()));

        coverletterRepository.save(coverletter);
        return coverletter.getId();
    }

    public void update(Member member, CoverletterDto.UpdateReq req) {
        Coverletter coverletter = this.getCoverletter(member, req.getId());
        coverletter.setQuestions(req.getQuestions().stream()
                .map(questionReq ->
                        getUpdatedQuestion(questionReq, member)
                                .setHashtags(getUpdatedHashtags(questionReq.getHashtags(), member)))
                .collect(Collectors.toList()));
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

    private Coverletter getCoverletter(Member member, long id) {
        Coverletter findCoverletter = coverletterRepository.findById(member, id);
        if (findCoverletter == null) {
            throw new RuntimeException("존재하지 않는 자기소개서입니다.");
        }

        return findCoverletter;
    }

    private Question getUpdatedQuestion(QuestionDto.UpdateQuestionReq questionReq, Member member) {
        if (questionReq.isNew())
            return new Question(questionReq.getTitle(), questionReq.getContents());

        Question question = questionRepository.findByKey(questionReq.getId(), member);
        if (question == null)
            return new Question(questionReq.getTitle(), questionReq.getContents());

        question.updateData(questionReq.getTitle(), questionReq.getContents());
        return question;


    }

    /**
     * 중복없이 hashtag를 question에 설정하기 위한 공통 메소드
     * 중복이면 기존의 해시태그값을 사용하고, 중복이 아니면 새로 해시태그를 만든다.
     */
    private Set<Hashtag> getUpdatedHashtags(Set<HashTagDto.SaveReq> hashtagReq, Member member) {
        return hashtagReq.stream()
                .map(saveReq -> {
                    // TODO : Key값이 애매하게 잡혀있어서, Merge를 사용할 수 없음 (개선하기)
                    // 이미 존재하는 해쉬태그인지 확인
                    Hashtag findHashtag = hashTagRepository.findByKey(member, saveReq.getValue());
                    if (findHashtag != null) return findHashtag;

                    // 존재하지 않으면 새로 만듬 (save할 필요가 없으나, 이렇게 안하면 해쉬태그가 중복되어 생성됌)
                    Hashtag hashtag = new Hashtag(member, saveReq.getValue());
                    hashTagRepository.save(hashtag);
                    return hashtag;
                }).collect(Collectors.toSet());
    }
}
