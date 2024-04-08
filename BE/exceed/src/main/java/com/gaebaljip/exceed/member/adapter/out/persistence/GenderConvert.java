package com.gaebaljip.exceed.member.adapter.out.persistence;

import java.util.Optional;

import javax.persistence.AttributeConverter;

import com.gaebaljip.exceed.member.domain.Gender;

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
