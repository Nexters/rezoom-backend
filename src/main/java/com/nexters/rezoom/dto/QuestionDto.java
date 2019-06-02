package com.nexters.rezoom.dto;

import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.question.domain.Question;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveQuestionReq {
        @NotNull
        @NotEmpty
        private String title;

        @NotNull
        @NotEmpty
        @Builder.Default
        private String contents;

        private Set<HashTagDto.SaveReq> hashtags = new HashSet<>();

        public Question toEntity() {
            Question question = new Question(title, contents);
            question.setHashtags(hashtags.stream().map(HashTagDto.SaveReq::toEntity).collect(Collectors.toSet()));
            return question;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateQuestionReq {
        @Positive
        private long id;

        @NotNull
        @NotEmpty
        private String title;

        @NotNull
        @NotEmpty
        private String contents;

        @Builder.Default
        private Set<HashTagDto.SaveReq> hashtags = new HashSet<>();

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
//        private Set<HashTagDto.ViewRes> hashtags;
        private Set<String> hashtags;

        public ViewRes(long id, String title, String contents, Set<String> hashtags) {
            this.id = id;
            this.title = title;
            this.contents = contents;
            this.hashtags = hashtags;
        }

        public ViewRes(Question question) {
            this.id = question.getId();
            this.title = question.getTitle();
            this.contents = question.getContents();
            this.hashtags = question.getHashtags().stream().map(Hashtag::getValue).collect(Collectors.toSet());
        }
    }
}
