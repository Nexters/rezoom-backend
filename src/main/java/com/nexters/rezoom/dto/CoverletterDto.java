package com.nexters.rezoom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.Deadline;
import lombok.*;
import org.hibernate.validator.constraints.Range;

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

        @Range(min = 0, max = 3)
        private int applicationHalf = 3;

        @Range(min = 0, max = 3)
        private int applicationType = 3;

        @Min(2017)
        private int applicationYear = LocalDateTime.now().getYear();

        private String deadline = null;

        @JsonProperty(value = "pass")
        private boolean isPass;

        @JsonProperty(value = "application")
        private boolean isApplication;

        private String jobType;

        @Builder.Default
        private List<QuestionDto.SaveQuestionReq> questions = new ArrayList<>();

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .companyName(companyName)
                    .applicationHalf(ApplicationHalf.getValue(applicationHalf))
                    .applicationType(ApplicationType.getValue(applicationType))
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

        @NotNull
        @NotEmpty
        private String companyName;

        @Range(min = 0, max = 3)
        private int applicationHalf;

        @Range(min = 0, max = 3)
        private int applicationType;

        @Min(2017)
        private int applicationYear;

        private String deadline;

        @JsonProperty(value = "pass")
        private boolean isPass;

        @JsonProperty(value = "application")
        private boolean isApplication;

        private String jobType;

        private List<QuestionDto.UpdateQuestionReq> questions;

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .id(id)
                    .companyName(companyName)
                    .applicationHalf(ApplicationHalf.getValue(applicationHalf))
                    .applicationType(ApplicationType.getValue(applicationType))
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
        private int applicationYear;
        private int applicationType;
        private int applicationHalf;
        private String deadline;
        private boolean isApplication;
        private boolean isPass;
        private String jobType;
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private List<QuestionDto.ViewRes> questions;

        public ViewRes(Coverletter coverletter) {
            this.id = coverletter.getId();
            this.companyName = coverletter.getCompanyName();
            this.applicationYear = coverletter.getApplicationYear().getValue();
            this.applicationType = coverletter.getApplicationType() == null ? 0 : coverletter.getApplicationType().getTypeNo();
            this.applicationHalf = coverletter.getApplicationHalf() == null ? 0 : coverletter.getApplicationHalf().getTypeNo();
            this.deadline = coverletter.getDeadline() == null ? null : coverletter.getDeadline().toString();
            this.isApplication = coverletter.isApplication();
            this.isPass = coverletter.isPass();
            this.jobType = coverletter.getJobType();
            this.createDate = coverletter.getCreateDate();
            this.updateDate = coverletter.getUpdateDate();
            this.questions = coverletter.getQuestions().stream().map(QuestionDto.ViewRes::new).collect(Collectors.toList());
        }
    }
}
