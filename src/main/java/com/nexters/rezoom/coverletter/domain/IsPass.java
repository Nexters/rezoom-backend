package com.nexters.rezoom.coverletter.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public enum IsPass {
    WAIT(0), // 대기
    PASS(1), // 합격
    FAIL(2), // 불합격
    ETC(3);  // 기타

    private static final Map<Integer, IsPass> lookup = new HashMap<>();

    static {
        for (IsPass item : IsPass.values()) {
            lookup.put(item.typeNo, item);
        }
    }

    int typeNo;

    IsPass(int typeNo) {
        this.typeNo = typeNo;
    }

    @JsonCreator
    public static IsPass getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, IsPass.ETC);
    }

    public static IsPass getValueByName(String isPass) {
        switch (isPass) {
            case "대기":
                return IsPass.WAIT;
            case "합격":
                return IsPass.PASS;
            case "불합격":
                return IsPass.FAIL;
            default:
                return IsPass.ETC;
        }
    }

    @JsonValue
    public int getTypeNo() {
        return this.typeNo;
    }
}
