package com.nexters.rezoom.converter.domain;

import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.question.domain.Question;

import java.io.File;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 *
 * 새로운 타입의 파일을 파싱하기 원하는 경우 본 추상클래스를 상속하세요.
 */
public abstract class CoverletterConverter {

    protected File file;

    private String fileExtension;

    public CoverletterConverter(File file, String fileExtension) {
        this.fileExtension = fileExtension;
        this.file = file;
        checkFile(file);
    }

    public Coverletter convert() {
        Coverletter coverletter = this.createCoverletterByFileName();
        List<Question> questions = this.parseQuestions();
        coverletter.setQuestions(questions);

        return coverletter;
    }

    protected abstract List<Question> parseQuestions();

    private void checkFile(File file) {
        isExist(file);
        checkFileExtension(file);
    }

    private void isExist(File file) {
        if (!file.exists())
            throw new RuntimeException("파일이 존재하지 않습니다");
    }

    /**
     * 현재 파일 확장자가 올바른 확장자인지 확인한다. (요구 확장자 정보는 구현 클래스에 상수로 존재)
     *
     * @param file
     */
    private void checkFileExtension(File file) {
        Optional<String> fileExtension = Optional.of(file.getName())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(file.getName().lastIndexOf(".") + 1));

        if (!fileExtension.isPresent() || !fileExtension.get().equals(this.fileExtension))
            throw new RuntimeException("파일 확장자가 올바르지 않습니다.");
    }

    /**
     * FileName을 통해 Coverletter 지원 정보를 얻고, 해당 정보를 설정한 Coverletter 객체를 생성한다.
     *
     * @return Coverletter
     */
    private Coverletter createCoverletterByFileName() {
        String[] coverletterInfos = this.getCoverletterInfo(file.getName().replace("."+fileExtension, ""));
        String companyName = coverletterInfos[0];
        int applicationYear = Integer.parseInt(coverletterInfos[1]);
        ApplicationHalf applicationHalf = ApplicationHalf.getValueByName(coverletterInfos[2]);
        ApplicationType applicationType = ApplicationType.getValueByName(coverletterInfos[3]);

        return Coverletter.builder()
                .companyName(companyName)
                .applicationYear(Year.of(applicationYear))
                .applicationHalf(applicationHalf)
                .applicationType(applicationType)
                .build();
    }

    /**
     * fileName에서 기본적인 자기소개서 지원 정보를 추출한다.
     * 지원정보는 '공백'으로 구분되어야 한다.
     * 지원정보는 총 4개다.
     *
     * @param fileName "네이버 2019 상반기 신입"
     * @return 지원 정보가 담긴 배열 (회사명, 지원연도, 지원분기, 지원종류)
     */
    private String[] getCoverletterInfo(String fileName) {
        String[] coverletterInfos = fileName.split(" ");
        if (coverletterInfos.length != 4) {
            throw new RuntimeException("파일명이 올바르지 않습니다.");
        }

        return coverletterInfos;
    }
}
