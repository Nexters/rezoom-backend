package com.nexters.rezoom.core.domain.coverletter.api.dto;

import com.nexters.rezoom.core.domain.coverletter.domain.Hashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class HashtagDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {

        public SaveReq(String value) {
            this.value = value;
        }

        @NotEmpty
        private String value;
        public Hashtag toEntity() {
            return new Hashtag(value);
        }
    }

    @Getter
    public static class ViewRes {
        private String value;

        public ViewRes(String value) {
            this.value = value;
        }

        public ViewRes(Hashtag hashTag) {
            this.value = hashTag.getValue();
        }
    }

}