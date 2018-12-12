package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private CoverletterService service;

    private Member member;

    public ServiceTest() {
        member = new Member("admin@admin.admin", "", "");
    }

    @Test
    public void 자기소개서_단건조회() {
        // given
        long coverletterId = 15L;

        // when
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);

        // then
        assertEquals(res.getId(), coverletterId);
        assertEquals(res.getCompanyName(), "adminCompany");
        assertEquals(res.getQuestions().size(), 2);
        res.getQuestions().forEach(viewRes -> viewRes.getHashtags().forEach(hashTagRes -> assertTrue(hashTagRes.getValue().contains("testTag"))));
    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_단건조회할때_없으면_RuntimeException() {
        // given
        long coverletterId = 1L;

        // when
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);

        // then
        // expected RuntimeException
    }

    @Test
    public void 자기소개서_리스트_조회() {
        // given
        int beginRow = 1;
        int endRow = 5;

        // when
        // 현재 member가 가진 자기소개서는 10개
        CoverletterDto.ListRes res = service.getList(member, beginRow, endRow);

        // then
        assertEquals(res.getCoverletters().size(), endRow - beginRow + 1);
    }

    @Test
    public void 자기소개서_리스트_조회_빈값이면_NULL이아니라_EMPTY() {
        // given
        int beginRow = 110;
        int endRow = 10000;

        // when
        CoverletterDto.ListRes res = service.getList(member, beginRow, endRow);

        // then
        assertNotNull(res);
        assertTrue(res.getCoverletters().isEmpty());
    }

    @Test
    public void 자기소개서_리스트_조회_페이징범위_반대일때_NULL이아니라_EMPTY() {
        // given
        int beginRow = 100;
        int endRow = 10;

        // when
        CoverletterDto.ListRes res = service.getList(member, beginRow, endRow);

        // then
        assertNotNull(res);
        assertTrue(res.getCoverletters().isEmpty());
    }

    // TODO
    public void 자기소개서_저장() {
    }

    // TODO
    public void 자기소개서_내_문항_수정() {

    }

    // TODO
    public void 자기소개서_내_문항_삭제() {

    }

    // TODO
    public void 자기소개서_내_문항_삭제_및_수정() {

    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_삭제() {
        // given
        long coverletterId = 15L;

        // when
        service.delete(member, coverletterId);

        // then
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);
        // expected runtimeException
    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_없는거_삭제할때_RuntimeException() {
        // given
        long coverletterId = 150L;

        // when
        service.delete(member, coverletterId);

        // then
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);
        // expected runtimeException
    }

}
