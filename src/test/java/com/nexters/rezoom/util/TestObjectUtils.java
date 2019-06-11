package com.nexters.rezoom.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.Deadline;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.*;

/**
 * Created by momentjin@gmail.com on 2019-03-28
 * Github : http://github.com/momentjin
 * Description : Coverletter 객체와 Questino 객체 간 양방향 편의 메소드 테스트
 **/
public class TestObjectUtils {

    public static Coverletter createCoverletterHasQuestionAndHashtag(Member member) {
        Coverletter coverletter = createCoverletter(member);

        Question question1 = createQuestion();
        Hashtag hashtag1 = createHashtag(member);
        question1.setHashtags(new HashSet<>(Collections.singletonList(hashtag1)));

        Question question2 = createQuestion();
        Hashtag hashtag2 = createHashtag(member);
        Hashtag hashtag3 = createHashtag(member);
        question2.setHashtags(new HashSet<>(Arrays.asList(hashtag1, hashtag2, hashtag3)));

        coverletter.setQuestions(Arrays.asList(question1, question2));
        return coverletter;
    }

    public static CoverletterDto.SaveReq createCoverletterSaveReqDto() {
        return loadDtoFromJsonFile("CoverletterNew.json", CoverletterDto.SaveReq.class);
    }

    public static CoverletterDto.UpdateReq createCoverletterUpdateReqDto() {
        return loadDtoFromJsonFile("CoverletterUpdate.json", CoverletterDto.UpdateReq.class);
    }

    private static Coverletter createCoverletter(Member member) {
        return Coverletter.builder()
                .companyName("testCompany")
                .questions(new ArrayList<>())
                .member(member)
                .applicationHalf(ApplicationHalf.ETC)
                .applicationType(ApplicationType.ETC)
                .applicationYear(Year.of(2018))
                .jobType("backend developer")
                .isApplication(false)
                .isPass(false)
                .deadline(new Deadline(LocalDateTime.now()))
                .build();
    }

    private static Question createQuestion() {
        return new Question("testTitle", "testContents");
    }

    private static Hashtag createHashtag(Member member) {
        return new Hashtag(member, UUID.randomUUID().toString() + "test");
    }

    private static <T> T loadDtoFromJsonFile(String fileName, Class<T> classType) {
        try {
            File file = new File("src/test/java/com/nexters/rezoom/util/resource/" + fileName);
            return new ObjectMapper().readValue(file, classType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
