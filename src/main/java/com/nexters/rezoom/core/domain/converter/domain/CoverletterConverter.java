package com.nexters.rezoom.core.domain.converter.domain;

import com.nexters.rezoom.core.domain.coverletter.domain.*;
import com.nexters.rezoom.core.domain.member.domain.Member;

import java.io.File;
import java.time.Year;
import java.util.List;
import java.util.Optional;

/**
 * Created by momentjin@gmail.com on 2019-08-28
 * Github : http://github.com/momentjin
 *
 * 새로운 타입의 파일 파싱을 원하는 경우 본 추상클래스를 상속해서 구현해야 합니다.
 */
public abstract class CoverletterConverter {

    protected File file;

    private String fileExtension;

    private static final int COVERLETTER_INFO_SIZE = 4;
    private static final String COVERLETTER_INFO_SEPARATOR = " ";

    private static final int COMPANY_NAME_IDX = 0;
    private static final int APPLICATION_YEAR_IDX = 1;
    private static final int APPLICATION_HALF_IDX = 2;
    private static final int APPLICATION_TYPE_IDX = 3;

    CoverletterConverter(File file, String fileExtension) {
        this.fileExtension = fileExtension;
        this.file = file;
        checkFile(file);
    }

    public Coverletter convert(Member member) {
        Coverletter coverletter = this.createCoverletterByFileName(member);
        List<Question> questions = this.parseQuestions();
        coverletter.setQuestions(questions);

        return coverletter;
    }

    /**
     * 파일 타입 별 문항 파싱을 위한 추상 메소드
     *
     * @return
     */
    protected abstract List<Question> parseQuestions();

    /**
     * 파일이 존재하는지, 확장자는 올바른지 검사한다.
     *
     * @param file
     */
    private void checkFile(File file) {
        isExist(file);
        checkFileExtension(file);
    }

    /**
     * 파일이 존재하는지 검사하고, 존재하지 않으면 예외를 발생시킨다.
     *
     * @param file
     */
    private void isExist(File file) {
        if (!file.exists())
            throw new IllegalArgumentException("파일이 존재하지 않습니다");
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
            throw new IllegalArgumentException("파일 확장자가 올바르지 않습니다.");
    }

    /**
     * FileName을 통해 Coverletter 지원 정보를 얻고, 해당 정보를 설정한 Coverletter 객체를 생성한다.
     *
     * @return Coverletter
     */
    private Coverletter createCoverletterByFileName(Member member) {
        String[] coverletterInfos = this.getCoverletterInfo(file.getName().replace("."+fileExtension, ""));

        String companyName = coverletterInfos[COMPANY_NAME_IDX];
        int applicationYear = Integer.parseInt(coverletterInfos[APPLICATION_YEAR_IDX]);
        ApplicationHalf applicationHalf = ApplicationHalf.valueOf(coverletterInfos[APPLICATION_HALF_IDX]);
        ApplicationType applicationType = ApplicationType.valueOf(coverletterInfos[APPLICATION_TYPE_IDX]);

        return Coverletter.newCoverletterBuilder()
                .member(member)
                .companyName(companyName)
                .applicationType(applicationType)
                .applicationHalf(applicationHalf)
                .applicationState(ApplicationState.ETC)
                .applicationYear(Year.of(applicationYear))
                .jobType("")
                .passState(PassState.ETC)
                .build();
    }

    /**
     * fileName에서 기본적인 자기소개서 지원 정보를 추출한다.
     * 지원정보는 '공백'으로 구분되어야 한다.
     *
     * @param fileName "구글 2019 상반기 신입"
     * @return 지원 정보가 담긴 배열 (회사명, 지원연도, 지원분기, 지원종류)
     */
    private String[] getCoverletterInfo(String fileName) {
        String[] coverletterInfos = fileName.split(COVERLETTER_INFO_SEPARATOR);
        if (coverletterInfos.length != COVERLETTER_INFO_SIZE) {
            throw new IllegalArgumentException("파일명이 올바르지 않습니다.");
        }

        return coverletterInfos;
    }
}
