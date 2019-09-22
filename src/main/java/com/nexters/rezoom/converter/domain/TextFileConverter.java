package com.nexters.rezoom.converter.domain;

import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.question.domain.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 * <p>
 * 텍스트 파일의 내용이 다음과 같아야 정상적으로 migration이 수행된다.
 * <p>
 * 포멧)
 * 파일명 : 회사명 지원년도 지원분기 지원종류 지원여부 합격여부.txt (필수, 모를 경우 '기타'로 대체해야 합니다)
 * <p>
 * Q1. 문항 제목1 (필수)
 * T1. 태그1, 태그2 ... (생략가능)
 * A1. 문항 내용2 (필수)
 * END
 * <p>
 * Q2. 문항 제목2
 * T2. 태그1, 태그2 ...
 * A2. 문항 내용2
 * END
 * <p>
 * 예시)
 * 삼성전자 2019 상반기 신입
 * <p>
 * Q1. 지원동기 및 포부를 말씀해주세요.
 * T1. 지원동기, 포부
 * A2. 삼성전자가 좋아서 지원했습니다. 열심히 하겠습니다. 하하하하하하하
 * 삼성전자가 좋은 두 번째 이유는 ~~~
 * 세번째 이유는 ~~
 * <p>
 * 그러므로 앞으로 열시미 해보겠사옵니드아.
 * <p>
 * <p>
 * Q2. 성장과정을 말씀해주세요.
 * T2. 성장과정
 * A2. 부모님의 사랑을 받으며 잘 컸습니다.
 * \
 */
public final class TextFileConverter extends CoverletterConverter {

    private final static String FILE_EXTENSION = "txt";

    public TextFileConverter(File textFile) {
        super(textFile, FILE_EXTENSION);
    }

    @Override
    public List<Question> parseQuestions() {

        // 1. load a file
        Scanner scanner = loadFile();

        // 2. parse a file's contents
        List<Question> questions = new ArrayList<>();

        String title = "", contents = "";
        Set<Hashtag> hashtags = new HashSet<>();

        int successFlag = 0;

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            // 문항 제목
            if (line.startsWith("Q")) {
                successFlag += 1;
                title = line.substring(4);
            }

            // 문항 내용
            else if (line.startsWith("A")) {
                successFlag -= 1;
                StringBuilder sb = new StringBuilder(line.substring(4)).append("\n");

                while (scanner.hasNext()) {
                    String contentsLine = scanner.nextLine();
                    if (contentsLine.equals("END")) break;

                    sb.append(contentsLine).append("\n");
                }

                if (!sb.toString().startsWith("Q"))
                    contents = sb.toString();
            }

            else if (line.startsWith("T")) {
                line = line.substring(4);
                hashtags = Arrays.stream(line.split(", "))
                        .map(Hashtag::new)
                        .collect(Collectors.toSet());
            }

            if (!title.equals("") && !contents.equals("")) {
                Question q = new Question(title, contents);
                q.setHashtags(hashtags);
                questions.add(q);

                // reset
                title = "";
                contents = "";
                hashtags = new HashSet<>();
            }
        }

        if (successFlag != 0)
            throw new IllegalArgumentException("파일 내용이 올바르지 않습니다.");

        // 3. return
        return questions;
    }

    private Scanner loadFile() {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
