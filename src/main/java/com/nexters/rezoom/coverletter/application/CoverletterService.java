package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CoverletterService {

    @Autowired
    private CoverletterRepository coverletterRepository;

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
                    question.setHashTags(
                            questionReq.getHashtags().stream().map(saveReq -> {
                                // 이미 존재하는 해쉬태그인지 확인
                                HashTag findHashtag = hashTagRepository.findByKey(member, saveReq.getValue());
                                if (findHashtag != null) {
                                    return findHashtag;
                                }

                                // 존재하지 않으면 새로 만듬
                                return new HashTag(member, saveReq.getValue());
                            }).collect(Collectors.toSet())
                    );
                    return question;
                })
                .collect(Collectors.toList()));

        coverletterRepository.save(coverletter);
    }

    public CoverletterDto.ViewRes getView(Member member, long id) {
        Coverletter findCoverletter = coverletterRepository.findById(member, id);
        if (findCoverletter == null) {
            throw new RuntimeException("존재하지 않는 자기소개서입니다.");
        }

        return new CoverletterDto.ViewRes(findCoverletter);
    }

    public CoverletterDto.ListRes getList(Member member, int beginRow, int endRow) {
        List<Coverletter> findCoverletters = coverletterRepository.findAll(member, beginRow, endRow);
        return new CoverletterDto.ListRes(findCoverletters);
    }

    public void delete(Member member, long id) {
        Coverletter findCoverletter = coverletterRepository.findById(member, id);
        if (findCoverletter == null) {
            throw new RuntimeException("");
        }

        coverletterRepository.delete(findCoverletter);
    }

}
