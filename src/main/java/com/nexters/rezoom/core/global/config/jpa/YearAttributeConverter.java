package com.nexters.rezoom.core.global.config.jpa;

import javax.persistence.AttributeConverter;
import java.time.Year;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/
public class YearAttributeConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year year) {
        if (year == null) return null;
        return (short) year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short year) {
        return Year.of(year);
    }
}
