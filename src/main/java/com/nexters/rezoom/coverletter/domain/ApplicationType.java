package com.nexters.rezoom.coverletter.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public enum ApplicationType {
    INTERN(0), // 인턴
    JUNIOR(1), // 신입
    SENIOR(2), // 경력
    ETC(3);    // 기타

    private static final Map<Integer, ApplicationType> lookup = new HashMap<>();

    static {
        for (ApplicationType applicationType : ApplicationType.values()) {
            lookup.put(applicationType.typeNo, applicationType);
        }
    }

    private int typeNo;

    ApplicationType(int typeNo) {
        this.typeNo = typeNo;
    }

    public static ApplicationType getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, ApplicationType.ETC);
    }

    public int getTypeNo() {
        return typeNo;
    }
}
