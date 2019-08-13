package com.nexters.rezoom.dto;

import com.nexters.rezoom.coverletter.domain.*;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CoverletterDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {

        @NotNull
        @NotEmpty
        private String companyName;

        @Min(2017)
        private int applicationYear = LocalDateTime.now().getYear();

        private int applicationHalf;
        private int applicationType;
        private int isPass;
        private int isApplication;
        private String deadline;
        private String jobType;
        private List<QuestionDto.SaveReq> questions = new ArrayList<>();

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .companyName(companyName)
                    .applicationHalf(ApplicationHalf.getValue(applicationHalf))
                    .applicationType(ApplicationType.getValue(applicationType))
                    .applicationYear(Year.of(applicationYear))
                    .isPass(IsPass.getValue(isPass))
                    .isApplication(IsApplication.getValue(isApplication))
                    .deadline(new Deadline(deadline))
                    .jobType(jobType)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {

        @Positive
        private long id;

        @NotNull
        @NotEmpty
        private String companyName;

        @Min(2017)
        private int applicationYear;

        private int applicationHalf;
        private int applicationType;
        private int isPass;
        private int isApplication;
        private String deadline;
        private String jobType;
        private List<QuestionDto.UpdateReq> questions;

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .id(id)
                    .companyName(companyName)
                    .applicationHalf(ApplicationHalf.getValue(applicationHalf))
                    .applicationType(ApplicationType.getValue(applicationType))
                    .applicationYear(Year.of(applicationYear))
                    .isApplication(IsApplication.getValue(isApplication))
                    .isPass(IsPass.getValue(isPass))
                    .deadline(new Deadline(deadline))
                    .jobType(jobType)
                    .build();
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    @Getter
    public static class ListRes {
        private List<ViewRes> coverletters;

        public ListRes(List<Coverletter> coverletters) {
            this.coverletters = coverletters.stream()
                    .map(CoverletterDto.ViewRes::new)
                    .collect(Collectors.toList());
        }
    }

    @ToString
    @Getter
    public static class ViewRes {

        private long id;
        private String companyName;
        private int applicationYear;
        private int applicationType;
        private int applicationHalf;
        private String deadline;
        private int isApplication;
        private int isPass;
        private String jobType;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private List<QuestionDto.ViewRes> questions;

        // TODO : NULL 처리 로직 제거하기
        public ViewRes(Coverletter coverletter) {
            this.id = coverletter.getId();
            this.companyName = coverletter.getCompanyName();
            this.applicationYear = coverletter.getApplicationYear().getValue();
            this.applicationType = coverletter.getApplicationType() == null ? 0 : coverletter.getApplicationType().getTypeNo();
            this.applicationHalf = coverletter.getApplicationHalf() == null ? 0 : coverletter.getApplicationHalf().getTypeNo();
            this.deadline = coverletter.getDeadline() == null ? null : coverletter.getDeadline().toString();
            this.isApplication = coverletter.getIsApplication() == null ? 0 : coverletter.getIsApplication().getTypeNo();
            this.isPass = coverletter.getIsPass() == null ? 0 : coverletter.getIsPass().getTypeNo();
            this.jobType = coverletter.getJobType();
            this.createDate = coverletter.getCreateDate();
            this.updateDate = coverletter.getUpdateDate();
            this.questions = coverletter.getQuestions().stream().map(QuestionDto.ViewRes::new).collect(Collectors.toList());
        }
    }
}
