package com.nexters.rezoom.coverletter.application;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
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

    public void save(Member member, CoverletterDto.SaveReq req) {
        Coverletter coverletter = new Coverletter(member, req.getCompanyName());
        coverletter.setQuestions(req.getQuestions().stream()
                .map(saveQuestionReq -> new Question(saveQuestionReq.getTitle(), saveQuestionReq.getTitle()))
                .collect(Collectors.toList()));

        // TODO : hashtag save 로직 필요

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
