package com.gaebaljip.exceed.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.gaebaljip.exceed.common.ValidationMessage;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (value.equalsIgnoreCase(enumValue.toString()))) {
                    result = true;
                    break;
                }
            }
        }
        context.buildConstraintViolationWithTemplate(value + ValidationMessage.ENUM_SUFFIX)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return result;
    }
}
