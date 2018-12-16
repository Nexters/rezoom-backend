package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
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

    @Autowired
    private CoverletterRepository coverletterRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    public void save(Member member, CoverletterDto.SaveReq req) {
        // set coverletter
        Coverletter coverletter = new Coverletter(member, req.getCompanyName());

        // set question
        coverletter.setQuestions(req.getQuestions().stream()
                .map(questionReq -> {
                    Question question = new Question(questionReq.getTitle(), questionReq.getContents());

                    // set hashtags
                    question.setHashTags(getUpdatedHashtags(questionReq.getHashtags(), member));
                    return question;
                })
                .collect(Collectors.toList()));

        coverletterRepository.save(coverletter);
    }

    public void update(Member member, CoverletterDto.UpdateReq req) {
        // update coverletter
        Coverletter coverletter = getCoverletter(member, req.getId());
        coverletter.setCompanyName(req.getCompanyName());

        // update questions
        coverletter.setQuestions(req.getQuestions().stream()
                .map(questionReq -> {
                    Question question = getUpdatedQuestion(questionReq, member);

                    // set hashtags
                    question.setHashTags(getUpdatedHashtags(questionReq.getHashtags(), member));
                    return question;
                })
                .collect(Collectors.toList())
        );
    }

    public CoverletterDto.ViewRes getView(Member member, long id) {
        Coverletter findCoverletter = getCoverletter(member, id);
        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public CoverletterDto.ListRes getList(Member member, int beginRow, int endRow) {
        List<Coverletter> findCoverletters = coverletterRepository.findAll(member, beginRow, endRow);
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
        else {
            Question question = questionRepository.findByKey(questionReq.getId(), member);
            question.updateQuestion(questionReq.getTitle(), questionReq.getContents());
            return question;
        }
    }

    /**
     * 중복없이 hashtag를 question에 설정하기 위한 공통 메소드
     * hashtag는 삭제/추가가 빈번히 이뤄지므로 애초에 dto에서 id값을 제외한 value만 받는다. (이 때 식별자는 value, member)
     * 중복이면 기존의 해시태그값을 사용하고, 중복이 아니면 새로 해시태그를 만든다.
     */
    private Set<HashTag> getUpdatedHashtags(Set<HashTagDto.SaveReq> hashtags, Member member) {
        return hashtags.stream()
                .map(saveReq -> {
                    // 이미 존재하는 해쉬태그인지 확인
                    HashTag findHashtag = hashTagRepository.findByKey(member, saveReq.getValue());
                    if (findHashtag != null) return findHashtag;

                    // 존재하지 않으면 새로 만듬
                    HashTag hashTag = new HashTag(member, saveReq.getValue());
                    hashTagRepository.save(hashTag);
                    return hashTag;
                }).collect(Collectors.toSet());
    }
}
