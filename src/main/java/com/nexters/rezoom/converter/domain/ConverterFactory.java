package com.nexters.rezoom.converter.domain;

import java.io.File;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
public class ConverterFactory {

    private ConverterFactory() {
    }

    public static CoverletterConverter createConverterByExtension(String extension, File file) {
        switch (extension) {
            case "txt":
                return new TextFileConverter(file);

            default:
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
        }
    }


}
