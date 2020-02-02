package com.nexters.rezoom.core.domain.converter;

import com.nexters.rezoom.core.domain.converter.domain.ConverterService;
import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by momentjin@gmail.com on 2019-08-30
 * Github : http://github.com/momentjin
 */

@SpringBootTest(classes = {ConverterService.class})
@ExtendWith(SpringExtension.class)
public class ConverterServiceTest {

    private static Member member;

    @Autowired
    private ConverterService converterService;

    @MockBean
    private CoverletterRepository coverletterRepository;

    @BeforeAll
    public static void createMember() {
        member = TestObjectUtils.createTestMember();
    }

    @Test
    public void 텍스트파일_변환_테스트_성공() {
        MultipartFile[] multipartFiles = {new MultipartFileStub()};

        // when
        converterService.convertFileToCoverletter(member, multipartFiles);

        // then
        verify(coverletterRepository, atLeastOnce()).save(any(Coverletter.class));
    }

}
