package de.dschoen.opco.util;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class InstantAttributeConverter implements AttributeConverter<Instant, Timestamp> {
	
    @Override
    public Timestamp convertToDatabaseColumn(Instant instant) {
    	return (instant == null ? null : java.sql.Timestamp.from(instant));
    }

    @Override
    public Instant convertToEntityAttribute(Timestamp sqlTimestamp) {
    	return (sqlTimestamp == null ? null : sqlTimestamp.toInstant());
    }
}