package com.nexters.rezoom.converter;

import com.nexters.rezoom.converter.domain.ConverterService;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.member.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by momentjin@gmail.com on 2019-08-30
 * Github : http://github.com/momentjin
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ConverterServiceTest {

    private static Member member;

    private final CoverletterService coverletterService;
    private final ConverterService converterService;

    @Autowired
    public ConverterServiceTest(CoverletterService coverletterService, ConverterService converterService) {
        this.coverletterService = coverletterService;
        this.converterService = converterService;
    }

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }


    // @Test
    // @DisplayName("파일 to 데이터베이스 기능 테스트")
    public void test1() {
        // given
        // multipartfile <- convert(file) (TODO : how?)

        // when
        // converterService.convertFromFileToCoverletter(member, multipartFiles);

        // then
        // CoverletterDto.ListRes coverletters = coverletterService.getList(member, 1);
        // Optional<Coverletter> coverletterOptional = coverletters.getCoverletters().stream()
        //        .filter(coverletter -> coverletter.getCompanyName().equals(companyName))
        //        .findAny();

        // assertTrue(coverletterOptional.isPresent());
    }

}
