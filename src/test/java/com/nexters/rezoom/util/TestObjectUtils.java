package com.nexters.rezoom.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;

import java.io.File;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by momentjin@gmail.com on 2019-03-28
 * Github : http://github.com/momentjin
 **/
public class TestObjectUtils {

    public static Coverletter createCoverletterHasQuestionAndHashtag(Member member) {
        Coverletter coverletter = createCoverletter(member);

        Question question1 = createQuestion();
        Question question2 = createQuestion();

        Hashtag hashtag1 = createHashtag(member, null);
        Hashtag hashtag2 = createHashtag(member,"testTag");
        Hashtag hashtag3 = createHashtag(member, null);

        question1.setHashtags(new HashSet<>(Arrays.asList(hashtag1, hashtag2)));
        question2.setHashtags(new HashSet<>(Arrays.asList(hashtag1, hashtag2, hashtag3)));

        coverletter.setQuestions(Arrays.asList(question1, question2));
        return coverletter;
    }

    public static CoverletterDto.SaveReq createCoverletterSaveReqDto() {
        return loadDtoFromJsonFile("CoverletterNew.json", CoverletterDto.SaveReq.class);
    }

    public static MemberDto.SignUpReq createMemberSignReqDto() {
        return loadDtoFromJsonFile("MemberSignup.json", MemberDto.SignUpReq.class);
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
                .isApplication(IsApplication.ETC)
                .isPass(IsPass.ETC)
                .deadline(new Deadline("2019-05-12 18:00"))
                .build();
    }

    private static Question createQuestion() {
        return new Question("testTitle", "testContents");
    }

    private static Hashtag createHashtag(Member member, String value) {
        return new Hashtag(member, value == null ? UUID.randomUUID().toString() + "test" : value);
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
