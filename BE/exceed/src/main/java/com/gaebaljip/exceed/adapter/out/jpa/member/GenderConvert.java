package com.gaebaljip.exceed.adapter.out.jpa.member;

import java.util.Optional;

import javax.persistence.AttributeConverter;

import com.gaebaljip.exceed.application.domain.member.Gender;

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
