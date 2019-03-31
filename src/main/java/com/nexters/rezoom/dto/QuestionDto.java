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

        public Question toEntity() {
            Question question = new Question(title, contents);
            question.setHashtags(hashtags.stream().map(HashTagDto.SaveReq::toEntity).collect(Collectors.toSet()));
            return question;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateQuestionReq {
        private long id;
        private String title;
        private String contents;
        private Set<HashTagDto.SaveReq> hashtags;

        public boolean isNew() {
            return this.id == 0;
        }

        public Question toEntity() {
            Question question = new Question(id, title, contents);
            question.setHashtags(hashtags.stream().map(HashTagDto.SaveReq::toEntity).collect(Collectors.toSet()));
            return question;
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
            this.hashtags = question.getHashtags().stream().map(HashTagDto.ViewRes::new).collect(Collectors.toSet());
        }
    }
}
