package com.nexters.rezoom.converter;

import com.nexters.rezoom.converter.domain.CoverletterConverter;
import com.nexters.rezoom.converter.domain.TextFileConverter;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Year;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 */
public class TextFileConverterTest {

    @Test
    @DisplayName("txt파일을 coverletter 객체로 전환")
    public void test1() {
        // given
        File textFile = new File("src/test/java/com/nexters/rezoom/resource/" + "E사 2019 하반기 신입.txt");
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
    @DisplayName("확장자가 txt가 아닐 때, RuntimeException 발생")
    public void test2() {
        // given
        File nonTextFile = new File("src/test/java/com/nexters/rezoom/resource/" + "temp.md");

        // when & then
        assertThrows(RuntimeException.class, () -> {
            CoverletterConverter converter = new TextFileConverter(nonTextFile);
        });
    }

    @Test
    @DisplayName("파일이 존재하지 않을 때, RuntimeException 발생")
    public void test3() {
        // given
        File textFile = new File("src/test/java/com/nexters/rezoom/resource/" + "temp999.txt");

        // when & then
        assertThrows(RuntimeException.class, () -> {
            CoverletterConverter converter = new TextFileConverter(textFile);
        });
    }

    // @Test
    // @DisplayName("파일 파싱이 실패할 경우, RuntimeException 발생")
    // 잘못된 파일 넣어주면 됌
    public void test4() {
        // given
        File textFile = new File("src/test/java/com/nexters/rezoom/resource/" + "E사 2019 하반기 신입.txt");
        CoverletterConverter converter = new TextFileConverter(textFile);

        // when & then
        assertThrows(RuntimeException.class, converter::convert);
    }

    @Test
    public void test5() {
        HashSet<String> set = new HashSet<>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");

        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
