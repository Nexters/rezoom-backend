package com.nexters.rezoom.dto;

import com.nexters.rezoom.hashtag.domain.HashTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class HashTagDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SaveReq {
        private String value;

        public SaveReq(String value) {
            this.value = value;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateReq {
        private String beforeValue;
        private String updatedValue;

        public UpdateReq(String beforeValue, String updatedValue) {
            this.beforeValue = beforeValue;
            this.updatedValue = updatedValue;
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

        public ViewRes(HashTag hashTag) {
            this.id = hashTag.getId();
            this.value = hashTag.getValue();
        }
    }

}
