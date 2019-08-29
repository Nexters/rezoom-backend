package com.nexters.rezoom.coverletter.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public enum ApplicationHalf {
    FIRST_HALF(0),  // 상반기
    SECOND_HALF(1), // 하반기
    ALWAYS(2),      // 수시
    ETC(3);         // 기타

    private static final Map<Integer, ApplicationHalf> lookup = new HashMap<>();

    static {
        for (ApplicationHalf applicationHalf : ApplicationHalf.values()) {
            lookup.put(applicationHalf.typeNo, applicationHalf);
        }
    }

    int typeNo;

    ApplicationHalf(int typeNo) {
        this.typeNo = typeNo;
    }

    public static ApplicationHalf getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, ApplicationHalf.ETC);
    }

    public static ApplicationHalf getValueByName(String name) {
        switch (name) {
            case "상반기":
                return ApplicationHalf.FIRST_HALF;
            case "하반기":
                return ApplicationHalf.SECOND_HALF;
            default:
                return ApplicationHalf.ETC;
        }
    }

    public int getTypeNo() {
        return this.typeNo;
    }
}
