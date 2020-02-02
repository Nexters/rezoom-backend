package com.nexters.rezoom.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nexters.rezoom.core.domain.coverletter.api.dto.CoverletterDto;
import com.nexters.rezoom.core.domain.coverletter.domain.*;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.domain.RezoomMember;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * Created by momentjin@gmail.com on 2019-03-28
 * Github : http://github.com/momentjin
 **/
public class TestObjectUtils {

    public static RezoomMember createTestMember() {
        return RezoomMember.RezoomMemberBuilder()
                .id("test@test.test")
                .name("tester")
                .password("test")
                .build();
    }

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

    public static MemberDto.SignUpReq createMemberSignReqDto() {
        return loadDtoFromJsonFile("MemberSignup.json", MemberDto.SignUpReq.class);
    }

    private static Coverletter createCoverletter(Member member) {
        return Coverletter.builder()
                .companyName("testCompany")
                .member(member)
                .applicationHalf(ApplicationHalf.ETC)
                .applicationType(ApplicationType.ETC)
                .applicationYear(Year.of(2018))
                .jobType("backend developer")
                .applicationState(ApplicationState.WAIT)
                .passState(PassState.ETC)
                .deadline(new Deadline(LocalDateTime.now()))
                .applicationState(ApplicationState.ETC)
                .passState(PassState.ETC)
                .deadline(new Deadline(LocalDateTime.now().plusDays(5)))
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
            File file = new File("src/test/java/com/nexters/rezoom/resource/" + fileName);
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            return mapper.readValue(file, classType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
