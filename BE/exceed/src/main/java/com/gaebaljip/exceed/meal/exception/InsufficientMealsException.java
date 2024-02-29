package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class InsufficientMealsException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InsufficientMealsException() {
        super(MessageCode.INSUFFICIENT_MEALS.getValue());
        this.messageCode = MessageCode.INSUFFICIENT_MEALS;
    }
}
