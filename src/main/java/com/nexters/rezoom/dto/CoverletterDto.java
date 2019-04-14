package com.nexters.rezoom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.ApplicationType;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.Deadline;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

public class CoverletterDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {
        private String companyName;
        private int applicationHalf;
        private int applicationType;
        private int applicationYear;
        private String deadline;
        private List<QuestionDto.SaveQuestionReq> questions;

        public Coverletter toEntity() {
            return Coverletter.builder()
                    .companyName(companyName)
                    .applicationHalf(ApplicationHalf.getValue(applicationHalf))
                    .applicationType(ApplicationType.getValue(applicationType))
                    .applicationYear(Year.of(applicationYear))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        private long id;
        private String companyName;
        private int applicationHalf;
        private int applicationType;
        private int applicationYear;
        private String deadline;

        @JsonProperty(value = "isPass")
        private boolean isPass;

        @JsonProperty(value = "isApplication")
        private boolean isApplication;

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
                    .build();
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateToggleReq {
        private String type;
        private boolean enable;
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
            this.createDate = coverletter.getCreateDate();
            this.updateDate = coverletter.getUpdateDate();
            this.questions = coverletter.getQuestions().stream().map(QuestionDto.ViewRes::new).collect(Collectors.toList());
        }
    }
}
