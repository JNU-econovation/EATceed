package com.gaebaljip.exceed.nutritionist.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class MealNotFoundException extends NoSuchElementException {

    private final MessageCode messageCode;

    public MealNotFoundException() {
        super(MessageCode.INVALID_MEAL.getValue());
        this.messageCode = MessageCode.INVALID_MEAL;
    }
}
