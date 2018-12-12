package com.nexters.rezoom.dto;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CoverletterDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {
        private String companyName;
        private List<QuestionDto.SaveQuestionReq> questions;

        public SaveReq(String companyName, List<QuestionDto.SaveQuestionReq> questions) {
            this.companyName = companyName;
            this.questions = questions;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        private long id;
        private String companyName;
        private List<QuestionDto.UpdateQuestionReq> questions;

        public UpdateReq(long id, String companyName, List<QuestionDto.UpdateQuestionReq> questions) {
            this.id = id;
            this.companyName = companyName;
            this.questions = questions;
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
        private LocalDateTime createDate;
        private LocalDateTime updateDate;
        private List<QuestionDto.ViewRes> questions;

        public ViewRes(Coverletter coverletter) {
            this.id = coverletter.getId();
            this.companyName = coverletter.getCompanyName();
            this.createDate = coverletter.getCreateDate();
            this.updateDate = coverletter.getUpdateDate();
            this.questions = coverletter.getQuestions().stream().map(QuestionDto.ViewRes::new).collect(Collectors.toList());
        }
    }
}
