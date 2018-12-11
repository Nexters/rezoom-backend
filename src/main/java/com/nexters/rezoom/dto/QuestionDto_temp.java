package com.nexters.rezoom.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

public class QuestionDto_temp {
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
    }
}