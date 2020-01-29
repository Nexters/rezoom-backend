package com.nexters.rezoom.coverletter.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public enum PassState {
    WAIT(0), // 대기
    PASS(1), // 합격
    FAIL(2), // 불합격
    ETC(3);  // 기타

    private static final Map<Integer, PassState> lookup = new HashMap<>();

    static {
        for (PassState item : PassState.values()) {
            lookup.put(item.typeNo, item);
        }
    }

    int typeNo;

    PassState(int typeNo) {
        this.typeNo = typeNo;
    }

    @JsonCreator
    public static PassState getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, PassState.ETC);
    }

    public static PassState getValueByName(String isPass) {
        switch (isPass) {
            case "대기":
                return PassState.WAIT;
            case "합격":
                return PassState.PASS;
            case "불합격":
                return PassState.FAIL;
            default:
                return PassState.ETC;
        }
    }

    @JsonValue
    public int getTypeNo() {
        return this.typeNo;
    }
}
