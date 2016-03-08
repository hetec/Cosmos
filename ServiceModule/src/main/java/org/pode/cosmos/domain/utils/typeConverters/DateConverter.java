package org.pode.cosmos.domain.utils.typeConverters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by patrick on 08.03.16.
 */
@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return dbData == null ? null : dbData.toLocalDate();
    }
}
