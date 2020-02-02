package com.nexters.rezoom.core.domain.converter.domain;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.global.exception.ErrorType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * Created by momentjin@gmail.com on 2019-08-29
 * Github : http://github.com/momentjin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class ConverterFactory {

    private final static String TXT = "txt";

    static CoverletterConverter createConverterByExtension(String extension, File file) {
        if (TXT.equals(extension)) {
            return new TextFileConverter(file);
        }

        throw new BusinessException(ErrorType.UNSURPPOTED_FILE_EXTENTION);
    }


}
