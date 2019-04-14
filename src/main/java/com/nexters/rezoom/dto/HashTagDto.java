package com.nexters.rezoom.dto;

import com.nexters.rezoom.hashtag.domain.Hashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class HashTagDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {
        @NotNull
        @NotEmpty
        private String value;
        public Hashtag toEntity() {
            return new Hashtag(value);
        }
    }

    @Getter
    public static class ViewRes {
        private long id;
        private String value;

        public ViewRes(long id, String value) {
            this.id = id;
            this.value = value;
        }

        public ViewRes(Hashtag hashTag) {
            this.id = hashTag.getId();
            this.value = hashTag.getValue();
        }
    }

}
