package com.nexters.rezoom.dto;

import com.nexters.rezoom.question.domain.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveQuestionReq {
        private String title;
        private String contents;
        private Set<HashTagDto.SaveReq> hashtags;

        public SaveQuestionReq(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }

        public SaveQuestionReq(String title, String contents, Set<HashTagDto.SaveReq> hashtags) {
            this.title = title;
            this.contents = contents;
            this.hashtags = hashtags;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateQuestionReq {
        private long id;
        private String title;
        private String contents;
        private Set<HashTagDto.SaveReq> hashtags;

        // id를 받지 않으면 (0이면) NewQuestion
        public UpdateQuestionReq(String title, String contents) {
            this.title = title;
            this.contents = contents;
        }

        public UpdateQuestionReq(long id, String title, String contents) {
            this.id = id;
            this.title = title;
            this.contents = contents;
        }

        public UpdateQuestionReq(long id, String title, String contents, Set<HashTagDto.SaveReq> hashtags) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.hashtags = hashtags;
        }

        public boolean isNew() {
            return this.id == 0;
        }
    }

    @Getter
    public static class ViewRes {
        private long id;
        private String title;
        private String contents;
        private Set<HashTagDto.ViewRes> hashtags;

        public ViewRes(long id, String title, String contents, Set<HashTagDto.ViewRes> hashtags) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.hashtags = hashtags;
        }

        public ViewRes(Question question) {
            this.id = question.getId();
            this.title = question.getTitle();
            this.contents = question.getContents();
            this.hashtags = question.getHashTags().stream().map(HashTagDto.ViewRes::new).collect(Collectors.toSet());
        }
    }
}
