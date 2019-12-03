package com.nexters.rezoom.converter;

import com.nexters.rezoom.converter.domain.CoverletterConverter;
import com.nexters.rezoom.converter.domain.TextFileConverter;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 */
public class TextFileConverterTest {

    private final static String RESOURCE_PATH = "src/test/java/com/nexters/rezoom/resource/";

    @Test
    public void 텍스트파일을_Covlertter객체로_변환할_수_있다() {
        // given
        File textFile = new File(RESOURCE_PATH + "E사 2019 하반기 신입.txt");
        CoverletterConverter converter = new TextFileConverter(textFile);

        // when
        Coverletter coverletter = converter.convert();

        // then
        assertEquals(coverletter.getCompanyName(), "E사");
        assertEquals(coverletter.getApplicationYear(), Year.of(2019));
        assertEquals(coverletter.getApplicationHalf(), ApplicationHalf.SECOND_HALF);
        assertEquals(coverletter.getApplicationType(), ApplicationType.JUNIOR);
        assertEquals(coverletter.getQuestions().size(), 3);
    }

    @Test
    public void 확장자가_다르면_RuntimeException() {
        // given
        File nonTextFile = new File(RESOURCE_PATH + "temp.md");

        // when & then
        assertThrows(RuntimeException.class, () -> {
            CoverletterConverter converter = new TextFileConverter(nonTextFile);
        });
    }

    @Test
    public void 파일이_존재하지_않으면_RunTimeException() {
        // given
        File textFile = new File(RESOURCE_PATH + "temp999.txt");

        // when & then
        assertThrows(RuntimeException.class, () -> {
            CoverletterConverter converter = new TextFileConverter(textFile);
        });
    }

    /**
     * 파싱 형식과 일치하지 않는 케이스의 파일 넣어줘야합니다. (보류)
     */
//     @Test
//    public void 파일_파싱이_실패하면_RunTimeException() {
//        // given
//        File textFile = new File(RESOURCE_PATH + "B사 2019 하반기 신입.txt");
//        CoverletterConverter converter = new TextFileConverter(textFile);
//
//        // when & then
//        assertThrows(RuntimeException.class, converter::convert);
//    }

}
