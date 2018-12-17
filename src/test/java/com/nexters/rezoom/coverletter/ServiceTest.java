package com.nexters.rezoom.coverletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    // TODO : 상대경로로 파일 참조가 안되는 문제 해결하기. 현재 폴더의 파일 참조가 왜 안되지?..

    @Autowired
    private CoverletterService service;

    @Autowired
    private CoverletterRepository repository;

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

    @Test
    public void 자기소개서_저장() throws IOException {
        // given
        File file = new File("D:\\workspace\\nexters\\rezoom-backend\\src\\test\\java\\com\\nexters\\rezoom\\coverletter\\CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);

        // when
        service.save(member, req);

        // then
        // TODO : 수정필요 (현재: 방금 추가한 자기소개서를 가져오기 위해 마지막번째 자기소개서를 가져오고 있음)
        List<Coverletter> findCoverletters = repository.findAll(member, 0, 1000);
        Coverletter findCoverletter = findCoverletters.get(findCoverletters.size() - 1);

        assertEquals(req.getCompanyName(), findCoverletter.getCompanyName());
        findCoverletter.getQuestions().forEach(question -> {
            assertNotEquals(question.getId(), 0);
            assertTrue(question.getTitle().contains("test"));
            assertTrue(question.getContents().contains("test"));

            question.getHashTags().forEach(hashTag -> assertNotEquals(hashTag.getId(), 0));
        });
    }

    @Test
    public void 자기소개서_문항_해시태그_수정() throws IOException {
        // given
        File file = new File("D:\\workspace\\nexters\\rezoom-backend\\src\\test\\java\\com\\nexters\\rezoom\\coverletter\\CoverletterUpdate.json");
        CoverletterDto.UpdateReq req = new ObjectMapper().readValue(file, CoverletterDto.UpdateReq.class);

        // when
        service.update(member, req);

        // then
        Coverletter findCoverletter = repository.findById(member, req.getId());

        assertEquals(findCoverletter.getCompanyName(), req.getCompanyName());
        findCoverletter.getQuestions().forEach(question -> {
            assertNotEquals(question.getId(), 0);
            assertTrue(question.getTitle().contains("(updated)"));
            assertTrue(question.getContents().contains("(updated)"));

            question.getHashTags().forEach(hashTag -> assertNotEquals(hashTag.getId(), 0));
        });
    }

    // 문항 삭제는 문항 Serrvice에서 지원하는 걸로 결정. dto에 넣어버리면 너무 복잡하다
    // public void 자기소개서_내_문항_삭제() {}

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
