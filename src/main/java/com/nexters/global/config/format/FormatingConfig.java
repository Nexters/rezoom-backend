package com.nexters.global.config.format;

import com.nexters.global.config.format.FormattingConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by momentjin@gmail.com on 2020-01-13
 * Github : http://github.com/momentjin
 */

@Configuration
public class FormatingConfig {


    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<LocalTime>() {
            @Override
            public LocalTime parse(String text, Locale locale) {
                return LocalTime.parse(text, DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_TIME_FORMAT));
            }

            @Override
            public String print(LocalTime object, Locale locale) {
                return DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_TIME_FORMAT).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_DATE_FORMAT));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_DATE_FORMAT).format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {

        return new Formatter<LocalDateTime>() {

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_DATE_TIME_FORMAT));
            }

            @Override
            public String print(LocalDateTime obj, Locale locale) {
                return DateTimeFormatter.ofPattern(FormattingConstants.LOCAL_DATE_TIME_FORMAT).format(obj);
            }
        };
    }
}
