package com.nexters.rezoom.coverletter.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public enum IsApplication {
    WAIT(0), // 대기
    NO(1), // 미지원
    YES(2), // 지원
    ETC(3);  // 기타

    private static final Map<Integer, IsApplication> lookup = new HashMap<>();

    static {
        for (IsApplication item : IsApplication.values()) {
            lookup.put(item.typeNo, item);
        }
    }

    int typeNo;

    IsApplication(int typeNo) {
        this.typeNo = typeNo;
    }

    public static IsApplication getValue(int typeNo) {
        return lookup.getOrDefault(typeNo, IsApplication.ETC);
    }

    public int getTypeNo() {
        return this.typeNo;
    }
}
