package com.nexters.rezoom.converter.domain;

import java.io.File;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
class ConverterFactory {

    private ConverterFactory() {
    }

    static CoverletterConverter createConverterByExtension(String extension, File file) {
        if ("txt".equals(extension)) {
            return new TextFileConverter(file);
        }

        throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
    }


}
