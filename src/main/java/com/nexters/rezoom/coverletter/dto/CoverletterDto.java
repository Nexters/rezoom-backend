package com.nexters.rezoom.coverletter.dto;

import com.nexters.rezoom.coverletter.domain.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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

        @NotEmpty
        private String companyName;

        @Min(2017)
        private int applicationYear = LocalDateTime.now().getYear();

        private ApplicationHalf applicationHalf = ApplicationHalf.ETC;
        private ApplicationType applicationType = ApplicationType.ETC;
        private IsPass isPass = IsPass.ETC;
        private IsApplication isApplication = IsApplication.ETC;
        private LocalDateTime deadline;
        private String jobType;
        private List<QuestionDto.SaveReq> questions = new ArrayList<>();

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .companyName(companyName)
                    .applicationHalf(applicationHalf)
                    .applicationType(applicationType)
                    .applicationYear(Year.of(applicationYear))
                    .isPass(isPass)
                    .isApplication(isApplication)
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

        @NotEmpty
        private String companyName;

        @Min(2017)
        private int applicationYear;

        private ApplicationHalf applicationHalf = ApplicationHalf.ETC;
        private ApplicationType applicationType = ApplicationType.ETC;
        private IsPass isPass = IsPass.ETC;
        private IsApplication isApplication = IsApplication.ETC;
        private LocalDateTime deadline;
        private String jobType;
        private List<QuestionDto.UpdateReq> questions;

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .id(id)
                    .companyName(companyName)
                    .applicationHalf(applicationHalf)
                    .applicationType(applicationType)
                    .applicationYear(Year.of(applicationYear))
                    .isApplication(isApplication)
                    .isPass(isPass)
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
        private Year applicationYear;
        private ApplicationType applicationType;
        private ApplicationHalf applicationHalf;
        private LocalDateTime deadline;
        private IsApplication isApplication;
        private IsPass isPass;
        private String jobType;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private List<QuestionDto.ViewRes> questions;

        public ViewRes(Coverletter coverletter) {
            this.id = coverletter.getId();
            this.companyName = coverletter.getCompanyName();
            this.applicationYear = coverletter.getApplicationYear();
            this.applicationType = coverletter.getApplicationType();
            this.applicationHalf = coverletter.getApplicationHalf();
            this.deadline = coverletter.getDeadline() == null ? null : coverletter.getDeadline().getDeadline();
            this.isApplication = coverletter.getIsApplication();
            this.isPass = coverletter.getIsPass();
            this.jobType = coverletter.getJobType();
            this.createDate = coverletter.getCreateDate();
            this.updateDate = coverletter.getUpdateDate();
            this.questions = coverletter.getQuestions().stream()
                    .map(QuestionDto.ViewRes::new)
                    .collect(Collectors.toList());
        }
    }
}