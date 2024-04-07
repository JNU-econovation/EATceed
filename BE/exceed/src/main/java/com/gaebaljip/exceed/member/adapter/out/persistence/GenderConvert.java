package com.gaebaljip.exceed.member.adapter.out.persistence;

import com.gaebaljip.exceed.member.domain.Gender;
import javax.persistence.AttributeConverter;
import java.util.Optional;

public class GenderConvert implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender attribute) {
        return Optional.ofNullable(attribute).map(Gender::getValue).orElse(null);
    }

    @Override
    public Gender convertToEntityAttribute(Integer dbData) {
        return Optional.ofNullable(dbData).map(Gender::of).orElse(null);
    }
}
